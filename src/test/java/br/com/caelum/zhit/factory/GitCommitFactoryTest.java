package br.com.caelum.zhit.factory;

import static br.com.caelum.zhit.matchers.ZhitMatchers.sameGitCommit;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import br.com.caelum.zhit.builder.AuthorBuilder;
import br.com.caelum.zhit.model.Author;
import br.com.caelum.zhit.model.GitCommit;

public class GitCommitFactoryTest {

	@Test
	public void should_build_commit() {
		String objectContent = "tree df2b8fc99e1c1d4dbc0a854d9f72157f1d6ea078\n" +
				"author Francisco Sokol <chico.sokol@gmail.com> 1369140112 -0300\n" +
				"committer Francisco Sokol <chico.sokol@gmail.com> 1369140112 -0300\n" +
				"\n" +
				"first commit\n";
		GitCommit commit = new GitCommitFactory().build(objectContent);

		Author author = new AuthorBuilder().withName("Francisco Sokol").withEmail("chico.sokol@gmail.com").create();
		assertThat(commit, sameGitCommit(new GitCommit(author, "first commit", "df2b8fc99e1c1d4dbc0a854d9f72157f1d6ea078")));
	}

}

