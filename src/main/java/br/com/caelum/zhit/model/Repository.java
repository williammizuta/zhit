package br.com.caelum.zhit.model;

import java.io.File;

public class Repository {

	private File root;
	private boolean bare;

	public static Repository local(File repositoryRoot) {
		return new Repository(repositoryRoot, false);
	}
	
	public static Repository bare(File repositoryRoot) {
		return new Repository(repositoryRoot, true);
	}
	
	private Repository(File repositoryRoot, boolean bare) {
		this.root = repositoryRoot;
		this.bare = bare;
	}

	public File getPath() {
		return root.getAbsoluteFile();
	}

	public boolean isBare() {
		return bare;
	}
	
}
