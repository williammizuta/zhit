package br.com.caelum.zhit.factory;

import java.util.Scanner;

import br.com.caelum.zhit.model.Commit;


public class CommitFactory {

	public Commit build(String objectContent) {
		Scanner scanner = new Scanner(objectContent);
		String author = "";
		String message = "";
		while(scanner.hasNext()) {
			String  currentLine = scanner.nextLine();
			if (currentLine.startsWith("author")) {
				author = currentLine.split("author ")[1];
			}
			if (currentLine.matches("\\s*")) {
				message = scanner.useDelimiter("\\z").next().trim();
			}
		}
		return new Commit(author, message);
	}
	
	

}
