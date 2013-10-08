package br.com.caelum.zhit.model;

import br.com.caelum.zhit.model.internal.Sha1;

public class GitBranch {

	private final String name;
	private final Sha1 sha1;

	public GitBranch(String name, Sha1 sha1) {
		this.name = name;
		this.sha1 = sha1;
	}

	public String name() {
		return this.name;
	}

	public Sha1 sha1() {
		return this.sha1;
	}

	@Override
	public String toString() {
		return "GitBranch [name=" + name + ", sha1=" + sha1 + "]";
	}

}
