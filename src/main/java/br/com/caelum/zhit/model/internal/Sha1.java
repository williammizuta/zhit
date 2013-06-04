package br.com.caelum.zhit.model.internal;

public class Sha1 {

	@Override
	public String toString() {
		return "Sha1 [hash=" + hash + "]";
	}

	private final String hash;

	public Sha1(String hash) {
		this.hash = hash;
	}
	
	public String hash() {
		return hash;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
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
		Sha1 other = (Sha1) obj;
		if (hash == null) {
			if (other.hash != null)
				return false;
		} else if (!hash.equals(other.hash))
			return false;
		return true;
	}
	
	

}
