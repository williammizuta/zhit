package br.com.caelum.zhit.model;

import br.com.caelum.zhit.model.internal.Sha1;

public class GitCommit {

	private final String message;
	private final Sha1 tree;
	private final Author author;

	public GitCommit(Author author, String message, Sha1 tree) {
		this.author = author;
		this.message = message;
		this.tree = tree;
	}

	public String message() {
		return message;
	}

	public Sha1 tree() {
		return tree;
	}

	public Author author() {
		return author;
	}

}
