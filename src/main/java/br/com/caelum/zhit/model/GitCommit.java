package br.com.caelum.zhit.model;

import br.com.caelum.zhit.model.internal.GitObject;
import br.com.caelum.zhit.model.internal.Sha1;

public class GitCommit {

	private final String message;
	private final GitObject<GitTree> tree;
	private final Author author;

	public GitCommit(Author author, String message, Sha1 tree, GitRepository gitRepository) {
		this.author = author;
		this.message = message;
		this.tree = new GitObject<GitTree>(tree, gitRepository);
	}

	public String message() {
		return message;
	}

	public GitObject<GitTree> tree() {
		return tree;
	}

	public Author author() {
		return author;
	}

}
