package br.com.caelum.zhit.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;

import br.com.caelum.zhit.model.Author;
import br.com.caelum.zhit.model.GitCommit;
import br.com.caelum.zhit.model.GitRepository;
import br.com.caelum.zhit.model.internal.Sha1;

public class GitCommitBuilder {

	private final GitRepository repository;
	private Author author;
	private String message;
	private Sha1 tree;
	private final List<Sha1> parents = new ArrayList<>();
	private DateTime createdAt;

	public GitCommitBuilder(GitRepository repository) {
		this.repository = repository;
		this.author = new AuthorBuilder().create();
		this.message = "";
		this.tree = new Sha1("");
		this.createdAt = new DateTime();
	}

	public GitCommitBuilder from(Author author) {
		this.author = author;
		return this;
	}

	public GitCommitBuilder withMessage(String message) {
		this.message = message;
		return this;
	}

	public GitCommitBuilder withTree(Sha1 tree) {
		this.tree = tree;
		return this;
	}

	public GitCommitBuilder parents(Sha1... sha1s) {
		Collections.addAll(parents, sha1s);
		return this;
	}

	public GitCommitBuilder at(DateTime time) {
		this.createdAt = time;
		return this;
	}

	public GitCommit create() {
		return new GitCommit(author, message, tree, parents, repository, createdAt);
	}

}
