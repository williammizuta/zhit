package br.com.caelum.zhit.model;

public class GitCommit {

	private final String message;
	private final String tree;
	private final Author author;

	public GitCommit(Author author, String message, String tree) {
		this.author = author;
		this.message = message;
		this.tree = tree;
	}

	public String message() {
		return message;
	}

	public String tree() {
		return tree;
	}

	public Author author() {
		return author;
	}

}
