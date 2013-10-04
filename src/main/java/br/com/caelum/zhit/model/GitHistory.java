package br.com.caelum.zhit.model;

import java.util.Iterator;

import br.com.caelum.zhit.model.internal.GitCommitIterator;

public class GitHistory implements Iterable<GitCommit> {
	
	private GitCommit commit;

	public GitHistory(GitCommit commit) {
		this.commit = commit;
	}

	@Override
	public Iterator<GitCommit> iterator() {
		return new GitCommitIterator(commit);
	}

}
