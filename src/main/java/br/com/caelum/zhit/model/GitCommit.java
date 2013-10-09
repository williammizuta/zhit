package br.com.caelum.zhit.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import br.com.caelum.zhit.infra.GitTreeInflater;
import br.com.caelum.zhit.model.internal.GitObject;
import br.com.caelum.zhit.model.internal.RawGitTreeEntry;
import br.com.caelum.zhit.model.internal.Sha1;
import br.com.caelum.zhit.parser.GitTreeParser;

public class GitCommit {

	private final String message;
	private final GitObject<GitTree> tree;
	private final Author author;
	private final List<Sha1> parents;
	private final GitRepository repository;
	private final DateTime createdAt;

	public GitCommit(Author author, String message, Sha1 tree, List<Sha1> parents, GitRepository gitRepository, DateTime createdAt) {
		this.author = author;
		this.message = message;
		this.parents = parents;
		this.repository = gitRepository;
		this.createdAt = createdAt;
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

	public GitRepository repository() {
		return repository;
	}

	public List<Sha1> parents() {
		return parents;
	}

	public DateTime createdAt() {
		return createdAt;
	}

	@Override
	public String toString() {
		return "GitCommit [message=" + message + ", tree=" + tree + ", author="
				+ author + ", parents=" + parents + ", createdAt=" + createdAt + "]";
	}

	public List<String> listFiles() {
		GitTree tree = tree();
		List<String> files = filesFromTree(tree, "");
		return files;
	}

	private List<String> filesFromTree(GitTree tree, String path) {
		List<String> files = new ArrayList<>();
		for (RawGitTreeEntry file : tree.files()) {
			files.add(path + file.filename());
		}
		for (RawGitTreeEntry dir : tree.dirs()) {
			files.addAll(filesFromTree(repository.parseTree(dir.sha1()), path + dir.filename() + File.separator));
		}
		return files;
	}

}
