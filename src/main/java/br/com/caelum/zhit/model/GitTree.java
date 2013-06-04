package br.com.caelum.zhit.model;

import java.util.List;

import br.com.caelum.zhit.model.internal.RawGitTreeEntry;

public class GitTree {


	private final List<RawGitTreeEntry> entries;

	public GitTree(List<RawGitTreeEntry> entries) {
		this.entries = entries;
	}

	public List<RawGitTreeEntry> entries() {
		return entries;
	}
	
}
