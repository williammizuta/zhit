package br.com.caelum.zhit.model;

import static java.io.File.separator;

import java.io.File;

import br.com.caelum.zhit.factory.GitObjectFactory;
import br.com.caelum.zhit.infra.GitObjectInflater;

public class GitObject<T> {

	private final String sha1;
	private final File dotGit;
	private final GitObjectInflater gitObjectInflater;
	private final GitObjectFactory<T> gitObjectFactory;

	public GitObject(String sha1, File dotGit, GitObjectInflater gitObjectInflater, GitObjectFactory<T> gitObjectFactory) {
		this.dotGit = dotGit;
		this.gitObjectInflater = gitObjectInflater;
		this.gitObjectFactory = gitObjectFactory;
		this.sha1 = sha1.trim();
	}
	
	public T extract() {
		return gitObjectFactory.build(gitObjectInflater.inflate(objectFile()));
	}
	
	protected File objectFile() {
		String dir = sha1.substring(0, 2);
		String fileName = sha1.substring(2);
		File file = new File(dotGit, "objects" + separator + dir + separator + fileName);
		return file;
	}

	public String sha1() {
		return sha1;
	}


}
