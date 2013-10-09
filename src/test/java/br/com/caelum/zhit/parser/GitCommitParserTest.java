package br.com.caelum.zhit.parser;

import static br.com.caelum.zhit.matchers.ZhitMatchers.sameGitCommit;
import static br.com.caelum.zhit.matchers.ZhitMatchers.sameSha1;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import br.com.caelum.zhit.builder.AuthorBuilder;
import br.com.caelum.zhit.model.Author;
import br.com.caelum.zhit.model.GitCommit;
import br.com.caelum.zhit.model.internal.Sha1;

public class GitCommitParserTest {

	@Test
	public void should_build_commit() {
		String objectContent = "tree df2b8fc99e1c1d4dbc0a854d9f72157f1d6ea078\n" +
				"author Francisco Sokol <chico.sokol@gmail.com> 1369140112 -0300\n" +
				"committer Francisco Sokol <chico.sokol@gmail.com> 1369140112 -0300\n" +
				"\n" +
				"first commit\n";
		GitCommit commit = new GitCommitParser(null).parse(objectContent);

		Author author = new AuthorBuilder().withName("Francisco Sokol").withEmail("chico.sokol@gmail.com").create();
		Sha1 tree = new Sha1("df2b8fc99e1c1d4dbc0a854d9f72157f1d6ea078");

		assertThat(commit, sameGitCommit(new GitCommit(author, "first commit", 
				tree, Collections.<Sha1>emptyList(), null, new DateTime(1369140112, DateTimeZone.forOffsetHoursMinutes(-3, 0)))));
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void should_get_commit_parents() {
		String objectContent = "tree df2b8fc99e1c1d4dbc0a854d9f72157f1d6ea078\n" +
				"author Francisco Sokol <chico.sokol@gmail.com> 1369140112 -0300\n" +
				"parent 8999463ab4ada363f106a62754807a8ac61c2814\n" +
				"parent ebf0f932c65de56ae11736646ddafc23529a6399\n" +
				"committer Francisco Sokol <chico.sokol@gmail.com> 1369140112 -0300\n" +
				"\n" +
				"first commit\n";
		GitCommit commit = new GitCommitParser(null).parse(objectContent);

		List<Sha1> parents = commit.parents();

		Sha1 firstParent = new Sha1("8999463ab4ada363f106a62754807a8ac61c2814");
		Sha1 secondParent = new Sha1("ebf0f932c65de56ae11736646ddafc23529a6399");
		assertThat(parents, containsInAnyOrder(sameSha1(firstParent), sameSha1(secondParent)));
	}

}

