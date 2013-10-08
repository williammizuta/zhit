package br.com.caelum.zhit.model.internal;

public class Sha1 {

	private final String hash;

	public Sha1(String hash) {
		this.hash = hash;
	}

	public String hash() {
		return hash;
	}

	@Override
	public String toString() {
		return "Sha1 [hash=" + hash + "]";
	}

}
