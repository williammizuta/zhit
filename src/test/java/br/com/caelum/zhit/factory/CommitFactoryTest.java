package br.com.caelum.zhit.factory;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.caelum.zhit.model.Commit;

public class CommitFactoryTest {

	@Test
	public void should_build_commit() {
		String objectContent = "tree df2b8fc99e1c1d4dbc0a854d9f72157f1d6ea078\n" +
				"author Francisco Sokol <chico.sokol@gmail.com> 1369140112 -0300\n" +
			"committer Francisco Sokol <chico.sokol@gmail.com> 1369140112 -0300\n" +
			"\n" +
			"first commit\n";
		Commit commit = new CommitFactory().build(objectContent);
		assertEquals("Francisco Sokol <chico.sokol@gmail.com> 1369140112 -0300", commit.author());
		assertEquals("first commit", commit.message());
		assertEquals("df2b8fc99e1c1d4dbc0a854d9f72157f1d6ea078", commit.tree());
	}

}

