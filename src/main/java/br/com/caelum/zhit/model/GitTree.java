package br.com.caelum.zhit.model;

import static br.com.caelum.zhit.model.ZhitPredicates.ofType;
import static br.com.caelum.zhit.model.internal.EntryType.BLOB;
import static br.com.caelum.zhit.model.internal.EntryType.TREE;
import static com.google.common.collect.Collections2.filter;

import java.util.Collection;
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

	public Collection<RawGitTreeEntry> files() {
		return filter(entries, ofType(BLOB));
	}

	public Collection<RawGitTreeEntry> dirs() {
		return filter(entries, ofType(TREE));
	}

}
