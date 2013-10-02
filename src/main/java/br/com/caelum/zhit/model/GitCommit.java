package br.com.caelum.zhit.model;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.zhit.infra.GitTreeInflater;
import br.com.caelum.zhit.model.internal.GitObject;
import br.com.caelum.zhit.model.internal.Sha1;
import br.com.caelum.zhit.parser.GitTreeParser;

public class GitCommit {

	private final String message;
	private final GitObject<GitTree> tree;
	private final Author author;
	private final List<Sha1> parents;

	public GitCommit(Author author, String message, Sha1 tree, List<Sha1> parents, GitRepository gitRepository) {
		this.author = author;
		this.message = message;
		this.parents = parents;
		this.tree = new GitObject<GitTree>(tree, gitRepository, new GitTreeInflater());
	}

	public String message() {
		return message;
	}

	public GitTree tree() {
		return tree.extract(new GitTreeParser());
	}

	public Author author() {
		return author;
	}

	@Deprecated
	public GitObject<GitTree> treeObject() {
		return tree;
	}

	public List<Sha1> parents() {
		return parents;
	}

}
