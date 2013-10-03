package br.com.caelum.zhit.model;

import br.com.caelum.zhit.model.internal.Sha1;

import com.google.common.base.Function;

public class ZhitFunctions {

	public static Function<String, GitBranch> extractLocalBranch() {
		return new Function<String, GitBranch>() {
			@Override
			public GitBranch apply(String line) {
				String[] fields = line.split("\\s");
				String sha1 = fields[0];
				String name = fields[1];
				String simpleName = name.substring(name.lastIndexOf("/") + 1);
				return new GitBranch(simpleName, new Sha1(sha1));
			}
		};
	}

	public static Function<String, GitBranch> extractRemoteBranch() {
		return new Function<String, GitBranch>() {
			@Override
			public GitBranch apply(String line) {
				String[] fields = line.split("\\s");
				String sha1 = fields[0];
				String name = fields[1];
				String simpleName = name.substring(name.lastIndexOf("remotes/") + 8);
				return new GitBranch(simpleName, new Sha1(sha1));
			}
		};
	}

}
