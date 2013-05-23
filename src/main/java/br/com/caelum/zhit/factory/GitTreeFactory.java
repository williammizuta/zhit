package br.com.caelum.zhit.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.caelum.zhit.model.GitTree;


public class GitTreeFactory implements GitObjectFactory<GitTree> {

	@Override
	public GitTree build(String objectContent) {
		Scanner scanner = new Scanner(objectContent);
		List<String> files = new ArrayList<>();
		List<String> trees = new ArrayList<>();
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String substring = line.substring(7);
			if (substring.startsWith("tree")) {
				trees.add(line);
			} else if(substring.startsWith("blob")) {
				files.add(line);
			} else {
				scanner.close();
				throw new IllegalArgumentException("Invalid object type: " + line);
			}
		}
		scanner.close();
		return new GitTree(trees, files);
	}

}