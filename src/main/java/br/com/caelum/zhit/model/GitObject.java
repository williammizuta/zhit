package br.com.caelum.zhit.model;

public class GitObject {

	private final String sha1;

	public GitObject(String sha1) {
		this.sha1 = sha1.trim();
	}
	
	String getSha1() {
		return sha1;
	}

}
