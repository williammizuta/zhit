package br.com.caelum.zhit.model;

import static java.io.File.separator;

import java.io.File;

import br.com.caelum.zhit.infra.GitObjectInflater;
import br.com.caelum.zhit.parser.GitObjectParser;

public class GitObject<T> {

	private final String sha1;
	private final GitRepository gitRepository;

	public GitObject(String sha1, GitRepository gitRepository) {
		this.gitRepository = gitRepository;
		this.sha1 = sha1.trim();
	}
	
	public T extract(GitObjectParser<T> factory) {
		GitObjectInflater inflater = new GitObjectInflater();
		File dotGit = gitRepository.dotGit();
		String dir = sha1.substring(0, 2);
		String fileName = sha1.substring(2);
		File objectFile = new File(dotGit, "objects" + separator + dir + separator + fileName);
		return factory.parse(inflater.inflate(objectFile));
	}

	public String sha1() {
		return sha1;
	}
	
}
