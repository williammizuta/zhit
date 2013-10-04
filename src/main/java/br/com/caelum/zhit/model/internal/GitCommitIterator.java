package br.com.caelum.zhit.model.internal;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import br.com.caelum.zhit.infra.GitCommitInflater;
import br.com.caelum.zhit.model.GitCommit;
import br.com.caelum.zhit.model.GitRepository;
import br.com.caelum.zhit.parser.GitCommitParser;

public class GitCommitIterator implements Iterator<GitCommit> {

	private TreeSet<GitCommit> currentCommits = new TreeSet<>(comparator());
	private GitCommitInflater inflater;
	private GitCommitParser parser;
	private GitRepository repository;

	public GitCommitIterator(GitCommit commit) {
		inflater = new GitCommitInflater();
		repository = commit.repository();
		parser = new GitCommitParser(repository);
		currentCommits.add(commit);
	}

	@Override
	public boolean hasNext() {
		return !currentCommits.isEmpty();
	}

	@Override
	public GitCommit next() {
		GitCommit current = currentCommits.pollFirst();
		if (current.hasParents()) {
			List<Sha1> parents = current.parents();
			for (Sha1 parent : parents) {
				GitCommit commit = new GitObject<GitCommit>(parent, repository, inflater).extract(parser);
				currentCommits.add(commit);
			}
		}
		return current;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	private Comparator<GitCommit> comparator() {
		return new Comparator<GitCommit>() {
			@Override
			public int compare(GitCommit o1, GitCommit o2) {
				return -o1.createdAt().compareTo(o2.createdAt());
			}
		};
	}
	
}
