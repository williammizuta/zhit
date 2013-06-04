package br.com.caelum.zhit.model;

import java.io.File;

import br.com.caelum.zhit.infra.ZhitFileUtils;
import br.com.caelum.zhit.parser.GitCommitParser;

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
		String headHash = ZhitFileUtils.readFileToString(new File(dotGit, headBranch));
		
		GitObject<GitCommit> gitObject = new GitObject<GitCommit>(headHash, this);
		GitCommit commit = gitObject.extract(new GitCommitParser());
		return commit;
	}
	
	public File path() {
		return root.getAbsoluteFile();
	}

	public boolean isBare() {
		return bare;
	}

	File dotGit() {
		return dotGit;
	}

}
