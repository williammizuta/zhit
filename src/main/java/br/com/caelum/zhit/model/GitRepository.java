package br.com.caelum.zhit.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.zhit.infra.GitBlobInflater;
import br.com.caelum.zhit.infra.GitCommitInflater;
import br.com.caelum.zhit.infra.GitTreeInflater;
import br.com.caelum.zhit.infra.ZhitFileUtils;
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

	private String extractHeadHash(String headBranch) {
		File packedRefsFile = new File(dotGit, "packed-refs");
		if (packedRefsFile.exists()) {
			String packedRefs = ZhitFileUtils.readFileToString(packedRefsFile);
			String[] lines = packedRefs.split("\n");
			for (String line : lines) {
				if (line.contains(headBranch)) {
					return line.split("\\s")[0];
				}
			}
		}
		File headBranchFile = new File(dotGit, headBranch);
		if (headBranchFile.exists()) {
			return ZhitFileUtils.readFileToString(headBranchFile);
		}
		throw new IllegalArgumentException("could not find " + headBranch);
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
		List<GitBranch> branches = new ArrayList<>();
		if (packedRefsFile.exists()) {
			String packedRefs = ZhitFileUtils.readFileToString(packedRefsFile);
			String[] lines = packedRefs.split("\n");
			for (String line : lines) {
				String[] fields = line.split("\\s");
				if (fields.length > 1) { 
					String sha1 = fields[0];
					String name = fields[1];
					if (name.startsWith("refs/heads/")) {
						String simpleName = name.substring(name.lastIndexOf("/") + 1);
						branches.add(new GitBranch(simpleName, new Sha1(sha1)));
					}
				}
			}
		}
		return branches;
	}

}
