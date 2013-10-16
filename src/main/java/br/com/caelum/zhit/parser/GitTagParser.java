package br.com.caelum.zhit.parser;

import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import br.com.caelum.zhit.model.Author;
import br.com.caelum.zhit.model.GitTag;
import br.com.caelum.zhit.model.internal.EntryType;
import br.com.caelum.zhit.model.internal.Sha1;


public class GitTagParser implements GitObjectParser<GitTag> {

	@Override
	public GitTag parse(String content) {
		Scanner scanner = new Scanner(content);

		Sha1 commit = null;
		EntryType type = EntryType.COMMIT;
		Author tagger = null;
		DateTime createdAt = null;
		String tagName = "";
		String message = "";

		while (scanner.hasNext()) {
			String  currentLine = scanner.nextLine();
			if (currentLine.startsWith("tagger")) {
				tagger = Author.fromString(currentLine);
				createdAt = parseDate(currentLine);
			} else if (currentLine.startsWith("object")) {
				commit = new Sha1(currentLine.split("object ")[1]);
			} else if (currentLine.startsWith("tag")) {
				tagName = currentLine.split("tag ")[1];
			} else if (currentLine.startsWith("type")) {
				type = EntryType.valueOf(currentLine.split("type ")[1].toUpperCase());
			} else {
				message += currentLine.trim();
			}
		}
		scanner.close();

		if(message.isEmpty()) {
			return new GitTag(commit);
		}
		return new GitTag(commit, type, tagger, createdAt, tagName, message);
	}

	private DateTime parseDate(String currentLine) {
		String[] splitedLine = currentLine.split("\\s");
		String timestamp = splitedLine[splitedLine.length-2];

		String timezoneOffset = splitedLine[splitedLine.length-1];
		int hours = Integer.parseInt(timezoneOffset.substring(0 ,timezoneOffset.length()-2));
		int minutes = Integer.parseInt(timezoneOffset.substring(timezoneOffset.length()-2));

		return new DateTime(Long.parseLong(timestamp), DateTimeZone.forOffsetHoursMinutes(hours, minutes));
	}

}
