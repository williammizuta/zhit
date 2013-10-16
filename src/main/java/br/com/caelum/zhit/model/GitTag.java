package br.com.caelum.zhit.model;

import org.joda.time.DateTime;

import br.com.caelum.zhit.model.internal.EntryType;
import br.com.caelum.zhit.model.internal.Sha1;

public class GitTag {

	private final Sha1 commit;
	private EntryType type;
	private Author tagger;
	private DateTime createdAt;
	private String tagName;
	private String message;

	public GitTag(Sha1 commit) {
		this.commit = commit;
	}

	public GitTag(Sha1 commit, EntryType type, Author tagger, DateTime createdAt, String tagName, String message) {
		this.commit = commit;
		this.type = type;
		this.tagger = tagger;
		this.createdAt = createdAt;
		this.tagName = tagName;
		this.message = message;
	}

	public Sha1 commit() {
		return commit;
	}

	public EntryType type() {
		return type;
	}

	public Author tagger() {
		return tagger;
	}

	public DateTime createdAt() {
		return createdAt;
	}

	public String tagName() {
		return tagName;
	}

	public String message() {
		return message;
	}

	@Override
	public String toString() {
		return "GitTag [commit=" + commit + ", type=" + type + ", tagger="
				+ tagger + ", createdAt=" + createdAt + ", tagName=" + tagName
				+ ", message=" + message + "]";
	}

}
