package br.com.caelum.zhit.parser;

import static br.com.caelum.zhit.matchers.ZhitMatchers.sameGitTag;
import static br.com.caelum.zhit.model.internal.EntryType.COMMIT;
import static org.joda.time.DateTimeZone.forOffsetHoursMinutes;
import static org.junit.Assert.assertThat;

import org.joda.time.DateTime;
import org.junit.Test;

import br.com.caelum.zhit.builder.AuthorBuilder;
import br.com.caelum.zhit.model.Author;
import br.com.caelum.zhit.model.GitTag;
import br.com.caelum.zhit.model.internal.Sha1;

public class GitTagParserTest {

	@Test
	public void should_parse_a_tag_object() {
		String tagContent = "object 520dcc1f3a3441ae7f9ac8c01ee15804046fa790\n" +
							"type commit\n" +
							"tag zhit-1.0\n" +
							"tagger William Seiti Mizuta <william.mizuta@gmail.com> 1272340064 -0300\n\n" +
							"[maven-scm] copy for tag zhit-1.0";

		GitTag tag = new GitTagParser().parse(tagContent);
		Author author = new AuthorBuilder().withName("William Seiti Mizuta")
				.withEmail("william.mizuta@gmail.com").create();

		GitTag expectedTag = new GitTag(new Sha1("520dcc1f3a3441ae7f9ac8c01ee15804046fa790"),
				COMMIT, author, new DateTime(1272340064, forOffsetHoursMinutes(-3, 0)),
				"zhit-1.0", "[maven-scm] copy for tag zhit-1.0");

		assertThat(tag, sameGitTag(expectedTag));
	}

}
