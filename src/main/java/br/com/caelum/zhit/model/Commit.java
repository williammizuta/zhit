package br.com.caelum.zhit.model;

public class Commit {

	private final String message;
	private final String author;
	private final String tree;

	public Commit(String author, String message, String tree) {
		this.author = author;
		this.message = message;
		this.tree = tree;
	}

	public String message() {
		return message;
	}
	
	public String author() {
		return author;
	}

	public String tree() {
		return tree;
	}


}
