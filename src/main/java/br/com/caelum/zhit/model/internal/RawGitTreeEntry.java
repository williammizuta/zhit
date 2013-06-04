package br.com.caelum.zhit.model.internal;

public class RawGitTreeEntry {
	
	private final String permissions;
	private final Sha1 sha1;
	private final String type;

	public RawGitTreeEntry(String permissions, String type, Sha1 sha1) {
		this.permissions = permissions;
		this.type = type;
		this.sha1 = sha1;
	}

	public static RawGitTreeEntry parse(String line) {
		String[] fields = line.split("\\s+");
		if (fields.length < 3) {
			throw new IllegalArgumentException();
		}
		String permissions = fields[0];
		String type = fields[1];
		Sha1 sha1 = new Sha1(fields[2]);
		return new RawGitTreeEntry(permissions, type, sha1);
	}

	public String permissions() {
		return permissions;
	}

	public String type() {
		return type;
	}

	public Sha1 sha1() {
		return sha1;
	}

	@Override
	public String toString() {
		return "RawGitTreeEntry [permissions=" + permissions + ", sha1=" + sha1
				+ ", type=" + type + "]";
	}

}
