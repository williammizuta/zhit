package br.com.caelum.zhit.model;

import java.io.File;

import br.com.caelum.zhit.util.ZhitFileUtils;

public class GitRepository {

	private File root;
	private boolean bare;

	public static GitRepository local(File repositoryRoot) {
		return new GitRepository(repositoryRoot, false);
	}
	
	public static GitRepository bare(File repositoryRoot) {
		return new GitRepository(repositoryRoot, true);
	}
	
	private GitRepository(File repositoryRoot, boolean bare) {
		this.root = repositoryRoot;
		this.bare = bare;
	}

	public File getPath() {
		return root.getAbsoluteFile();
	}

	public boolean isBare() {
		return bare;
	}

	public GitObject head() {
		File head = new File(dotGit(), "HEAD");
		String headContent = ZhitFileUtils.readFileToString(head);
		String headBranch = headContent.split(":")[1].trim();
		String headHash = ZhitFileUtils.readFileToString(new File(dotGit(), headBranch));
		return new GitObject(headHash);
	}

	private File dotGit() {
		return isBare() ? root : new File(root, ".git");
	}
	
}
