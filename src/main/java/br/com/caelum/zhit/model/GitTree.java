package br.com.caelum.zhit.model;

import java.util.List;

public class GitTree {

	private List<String> trees;
	private List<String> blobs;
	
	public GitTree(List<String> trees, List<String> blobs) {
		this.trees = trees;
		this.blobs = blobs;
	}

	public List<String> blobs() {
		return blobs;
	}
	
	public List<String> trees() {
		return trees;
	}

}
