package br.com.caelum.zhit.model;

import static br.com.caelum.zhit.infra.ZhitFileUtils.readFileToString;
import static br.com.caelum.zhit.model.ZhitFunctions.extractBranch;
import static br.com.caelum.zhit.model.ZhitPredicates.grep;
import static br.com.caelum.zhit.model.ZhitPredicates.linesWithBranches;
import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import br.com.caelum.zhit.infra.GitBlobInflater;
import br.com.caelum.zhit.infra.GitCommitInflater;
import br.com.caelum.zhit.infra.GitTreeInflater;
import br.com.caelum.zhit.infra.ZhitFileUtils;
import br.com.caelum.zhit.matchers.ZhitMatchers;
import br.com.caelum.zhit.model.internal.GitObject;
import br.com.caelum.zhit.model.internal.Sha1;
import br.com.caelum.zhit.parser.GitBlobParser;
import br.com.caelum.zhit.parser.GitCommitParser;
import br.com.caelum.zhit.parser.GitTreeParser;

public class GitRepository {

	private final File root;
	private final File dotGit;
	private final boolean bare;

	public static GitRepository local(File repositoryRoot) {
		return new GitRepository(repositoryRoot, false);
	}
	
	public static GitRepository bare(File repositoryRoot) {
		return new GitRepository(repositoryRoot, true);
	}
	
	private GitRepository(File repositoryRoot, boolean bare) {
		this.bare = bare;
		this.root = repositoryRoot;
		this.dotGit = bare ? root : new File(root, ".git");
	}

	public GitCommit head() {
		File head = new File(dotGit, "HEAD");
		String headContent = ZhitFileUtils.readFileToString(head);
		String headBranch = headContent.split(":")[1].trim();
		String headHash = extractHeadHash(headBranch);
		
		GitObject<GitCommit> gitObject = new GitObject<GitCommit>(new Sha1(headHash), this, new GitCommitInflater());
		GitCommit commit = gitObject.extract(new GitCommitParser(this));
		return commit;
	}

	private String extractHeadHash(final String headBranch) {
		File headBranchFile = new File(dotGit, headBranch);
		if (headBranchFile.exists()) {
			return ZhitFileUtils.readFileToString(headBranchFile);
		}
		
		File packedRefsFile = new File(dotGit, "packed-refs");
		List<String> packedRefsLines = packedRefsLines(packedRefsFile);
		ArrayList<String> headLines = new ArrayList<String>(filter(packedRefsLines, grep(headBranch)));
		if (headLines.isEmpty())
			throw new IllegalArgumentException("could not find " + headBranch);
		
		return headLines.get(0).split("\\s")[0];
		
	}

	private List<String> packedRefsLines(File packedRefsFile) {
		if (packedRefsFile.exists()) {
			String packedRefs = readFileToString(packedRefsFile);
			return asList(packedRefs.split("\n"));
		}
		return emptyList();
	}
	
	public File path() {
		return root.getAbsoluteFile();
	}

	public boolean isBare() {
		return bare;
	}

	public File dotGit() {
		return dotGit;
	}

	public GitTree parseTree(Sha1 sha1) {
		return new GitObject<GitTree>(sha1, this,
				new GitTreeInflater()).extract(new GitTreeParser());
	}

	public GitBlob parseBlob(Sha1 sha1) {
		return new GitObject<GitBlob>(sha1, this, new GitBlobInflater()).extract(new GitBlobParser());
	}

	public List<GitBranch> branches() {
		File packedRefsFile = new File(dotGit, "packed-refs");
		List<String> packedRefsLines = packedRefsLines(packedRefsFile);
		
		Collection<String> linesWithBranches = filter(packedRefsLines, linesWithBranches());
		Function<String, GitBranch> function = extractBranch(); 
		
		return new ArrayList<GitBranch>(transform(linesWithBranches, function));  
	}

}
