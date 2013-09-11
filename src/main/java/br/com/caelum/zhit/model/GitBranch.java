package br.com.caelum.zhit.model;

import br.com.caelum.zhit.model.internal.Sha1;

public class GitBranch {

	private String name;
	private Sha1 sha1;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sha1 == null) ? 0 : sha1.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GitBranch other = (GitBranch) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sha1 == null) {
			if (other.sha1 != null)
				return false;
		} else if (!sha1.equals(other.sha1))
			return false;
		return true;
	}

}
