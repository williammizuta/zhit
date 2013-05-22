package br.com.caelum.zhit.model;

import static java.io.File.separator;

import java.io.File;
import java.util.zip.InflaterInputStream;

import br.com.caelum.zhit.factory.CommitFactory;
import br.com.caelum.zhit.infra.GitObjectInflater;

public class CommitObject {

	private final String sha1;
	private final File dotGit;
	private final GitObjectInflater gitObjectInflater;

	public CommitObject(String sha1, File dotGit, GitObjectInflater gitObjectInflater) {
		this.dotGit = dotGit;
		this.gitObjectInflater = gitObjectInflater;
		this.sha1 = sha1.trim();
	}
	
	String getSha1() {
		return sha1;
	}

	public Commit extractCommit() {
		InflaterInputStream inflaterInputStream;
		return new CommitFactory().build(gitObjectInflater.inflate(objectFile()));
		
	}

	private File objectFile() {
		String dir = sha1.substring(0, 2);
		String fileName = sha1.substring(2);
		File file = new File(dotGit, "objects" + separator + dir + separator + fileName);
		return file;
	}

}
