package br.com.caelum.zhit.model;

import static java.io.File.separator;

import java.io.File;

import br.com.caelum.zhit.factory.GitObjectFactory;
import br.com.caelum.zhit.infra.GitObjectInflater;

public class GitObject<T> {

	private final String sha1;
	private final File dotGit;
	private final GitObjectInflater inflater;
	private final GitObjectFactory<T> factory;

	public GitObject(String sha1, File dotGit, GitObjectInflater inflater, GitObjectFactory<T> factory) {
		this.sha1 = sha1.trim();
		this.dotGit = dotGit;
		this.inflater = inflater;
		this.factory = factory;
	}
	
	public T extract() {
		return factory.build(inflater.inflate(objectFile()));
	}

	public String sha1() {
		return sha1;
	}
	
	private File objectFile() {
		String dir = sha1.substring(0, 2);
		String fileName = sha1.substring(2);
		File file = new File(dotGit, "objects" + separator + dir + separator + fileName);
		return file;
	}

}
