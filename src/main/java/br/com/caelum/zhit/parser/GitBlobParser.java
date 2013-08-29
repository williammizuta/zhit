package br.com.caelum.zhit.parser;

import br.com.caelum.zhit.model.GitBlob;

public class GitBlobParser implements GitObjectParser<GitBlob> {

	@Override
	public GitBlob parse(String objectContent) {
		return new GitBlob(objectContent);
	}

}
