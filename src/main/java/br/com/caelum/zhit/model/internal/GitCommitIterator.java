package br.com.caelum.zhit.model.internal;

import java.util.Iterator;

import br.com.caelum.zhit.infra.GitCommitInflater;
import br.com.caelum.zhit.model.GitCommit;
import br.com.caelum.zhit.model.GitRepository;
import br.com.caelum.zhit.parser.GitCommitParser;

public class GitCommitIterator implements Iterator<GitCommit> {

	private GitCommit currentCommit;
	private GitCommitInflater inflater;
	private GitCommitParser parser;
	private GitRepository repository;

	public GitCommitIterator(GitCommit commit) {
		this.currentCommit = commit;
		inflater = new GitCommitInflater();
		repository = commit.repository();
		parser = new GitCommitParser(repository);
	}

	@Override
	public boolean hasNext() {
		return currentCommit != null;
	}

	@Override
	public GitCommit next() {
		GitCommit current = currentCommit;
		if (currentCommit.hasParents()) {
			Sha1 sha1 = currentCommit.parents().get(0);
			currentCommit = new GitObject<GitCommit>(sha1, repository, inflater)
					.extract(parser);
		} else {
			currentCommit = null;
		}
		return current;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
