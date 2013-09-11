package br.com.caelum.zhit.model;

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

	public static Predicate<String> linesWithBranches() {
		return new Predicate<String>() {
			@Override
			public boolean apply(String line) {
				String[] fields = line.split("\\s"); 
				return fields.length > 1 && fields[1].startsWith("refs/heads/");
			}
		};
	}

}
