package br.com.caelum.zhit.model;

import java.io.File;

public class Repository {

	private File root;

	public Repository(File repositoryRoot) {
		this.root = repositoryRoot;
	}

	public File getPath() {
		return root.getAbsoluteFile();
	}
	
	
	
}
