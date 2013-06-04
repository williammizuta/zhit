package br.com.caelum.zhit.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.caelum.zhit.model.GitTree;
import br.com.caelum.zhit.model.internal.RawGitTreeEntry;

public class GitTreeParser implements GitObjectParser<GitTree> {

	@Override
	public GitTree parse(String objectContent) {
		try (Scanner scanner = new Scanner(objectContent)) {
			List<String> files = new ArrayList<>();
			List<RawGitTreeEntry> entries = new ArrayList<>();
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				entries.add(RawGitTreeEntry.parse(line));
				files.add(line);
			}
			scanner.close();
			return new GitTree(entries);
		}
	}

}
