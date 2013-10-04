package br.com.caelum.zhit.model.internal;

import static java.io.File.separator;

import java.io.File;

import br.com.caelum.zhit.infra.GitObjectInflater;
import br.com.caelum.zhit.model.GitRepository;
import br.com.caelum.zhit.parser.GitObjectParser;

public class GitObject<T> {

	private final Sha1 sha1;
	private final GitRepository gitRepository;
	private GitObjectInflater inflater;

	public GitObject(Sha1 sha1, GitRepository gitRepository, GitObjectInflater inflater) {
		this.gitRepository = gitRepository;
		this.sha1 = sha1;
		this.inflater = inflater;
	}
	
	public T extract(GitObjectParser<T> parser) {
		File dotGit = gitRepository.dotGit();
		String sha1 = this.sha1.hash().trim();
		String dir = sha1.substring(0, 2);
		String fileName = sha1.substring(2);
		File objectFile = new File(dotGit, "objects" + separator + dir + separator + fileName);
		return parser.parse(inflater.inflate(objectFile));
	}

	public Sha1 sha1() {
		return sha1;
	}
	
}
