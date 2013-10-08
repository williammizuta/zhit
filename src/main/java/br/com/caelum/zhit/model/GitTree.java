package br.com.caelum.zhit.model;

import static br.com.caelum.zhit.model.internal.EntryType.BLOB;
import static br.com.caelum.zhit.model.internal.EntryType.TREE;
import static com.google.common.collect.Collections2.filter;

import java.util.Collection;
import java.util.List;

import br.com.caelum.zhit.model.internal.EntryType;
import br.com.caelum.zhit.model.internal.RawGitTreeEntry;

import com.google.common.base.Predicate;

public class GitTree {

	private final List<RawGitTreeEntry> entries;

	public GitTree(List<RawGitTreeEntry> entries) {
		this.entries = entries;
	}

	public List<RawGitTreeEntry> entries() {
		return entries;
	}

	public Collection<RawGitTreeEntry> files() {
		return filter(entries, type(BLOB));
	}

	public Collection<RawGitTreeEntry> dirs() {
		return filter(entries, type(TREE));
	}

	private Predicate<RawGitTreeEntry> type(final EntryType type) {
		return new Predicate<RawGitTreeEntry>() {
			public boolean apply(RawGitTreeEntry entry) {
				return entry.type().equals(type);
			}
		};
	}

}
