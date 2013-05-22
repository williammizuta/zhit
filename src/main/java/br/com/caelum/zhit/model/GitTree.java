package br.com.caelum.zhit.model;

import java.util.List;

public class GitTree {

	private List<String> trees;
	private List<String> files;
	
	public GitTree(List<String> trees, List<String> files) {
		this.trees = trees;
		this.files = files;
	}

	public List<String> files() {
		return files;
	}
	
	public List<String> trees() {
		return trees;
	}

}
