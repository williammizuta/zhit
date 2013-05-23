package br.com.caelum.zhit.factory;

import java.util.Scanner;

import br.com.caelum.zhit.model.Author;
import br.com.caelum.zhit.model.Commit;


public class CommitFactory implements GitObjectFactory<Commit> {
	
	@Override
	public Commit build(String objectContent) {
		Scanner scanner = new Scanner(objectContent);
		Author author = null;
		String message = "";
		String tree = "";
		while(scanner.hasNext()) {
			String  currentLine = scanner.nextLine();
			if (currentLine.startsWith("author")) {
				author = Author.fromString(currentLine);
			}
			if (currentLine.startsWith("tree")) {
				tree = currentLine.split("tree ")[1];
			}
			if (currentLine.matches("\\s*")) {
				message = scanner.useDelimiter("\\z").next().trim();
			}
		}
		scanner.close();
		return new Commit(author, message, tree);
	}
	
	

}
