package br.com.caelum.zhit.model;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.zhit.model.internal.EntryType;
import br.com.caelum.zhit.model.internal.RawGitTreeEntry;

public class GitTree {

	private final List<RawGitTreeEntry> entries;

	public GitTree(List<RawGitTreeEntry> entries) {
		this.entries = entries;
	}

	public List<RawGitTreeEntry> entries() {
		return entries;
	}
	
	public List<RawGitTreeEntry> files() {
		List<RawGitTreeEntry> filter = filter(EntryType.BLOB);
		return filter;
	}
	
	public List<RawGitTreeEntry> dirs() {
		List<RawGitTreeEntry> dirs = filter(EntryType.TREE);
		return dirs;
	}

	private List<RawGitTreeEntry> filter(EntryType type) {
		ArrayList<RawGitTreeEntry> dirs = new ArrayList<>();
		for (RawGitTreeEntry entry : entries) {
			if (entry.type().equals(type)) {
				dirs.add(entry);
			}
		}
		return dirs;
	}
	
}
