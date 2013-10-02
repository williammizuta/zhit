package br.com.caelum.zhit.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.caelum.zhit.model.Author;
import br.com.caelum.zhit.model.GitCommit;
import br.com.caelum.zhit.model.GitRepository;
import br.com.caelum.zhit.model.internal.Sha1;


public class GitCommitParser implements GitObjectParser<GitCommit> {

	private final GitRepository gitRepository;

	public GitCommitParser(GitRepository gitRepository) {
		this.gitRepository = gitRepository;
	}

	@Override
	public GitCommit parse(String objectContent) {
		Scanner scanner = new Scanner(objectContent);
		Author author = null;
		String message = "";
		String tree = "";
		List<Sha1> parents = new ArrayList<>();
		while (scanner.hasNext()) {
			String  currentLine = scanner.nextLine();
			if (currentLine.startsWith("author")) {
				author = Author.fromString(currentLine);
			}
			if (currentLine.startsWith("tree")) {
				tree = currentLine.split("tree ")[1];
			}
			if (currentLine.startsWith("parent")) {
				parents.add(new Sha1(currentLine.split("\\s")[1].trim()));
			}
			if (currentLine.matches("\\s*")) {
				message = scanner.useDelimiter("\\z").next().trim();
			}
		}
		scanner.close();
		return new GitCommit(author, message, new Sha1(tree), parents, gitRepository);
	}

}
