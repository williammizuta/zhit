package br.com.caelum.zhit.model;

import br.com.caelum.zhit.model.internal.EntryType;
import br.com.caelum.zhit.model.internal.RawGitTreeEntry;

import com.google.common.base.Predicate;

public class ZhitPredicates {

	public static Predicate<String> grep(final String pattern) {
		return new Predicate<String>() {
			@Override
			public boolean apply(String line) {
				return line.contains(pattern);
			}
		};
	}

	public static Predicate<RawGitTreeEntry> ofType(final EntryType type) {
		return new Predicate<RawGitTreeEntry>() {
			public boolean apply(RawGitTreeEntry entry) {
				return entry.type().equals(type);
			}
		};
	}

	public static Predicate<String> linesWithBranches(final String pattern) {
		return new Predicate<String>() {
			@Override
			public boolean apply(String line) {
				String[] fields = line.split("\\s");
				return fields.length > 1 && fields[1].startsWith(pattern);
			}
		};
	}

}
