package br.com.caelum.zhit.model;

import static br.com.caelum.zhit.matchers.ZhitMatchers.sameAuthor;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import br.com.caelum.zhit.builder.AuthorBuilder;

public class AuthorTest {

	@Test
	public void should_build_author() {
		String authorLine = "author Francisco Sokol <chico.sokol@gmail.com> 1369140112 -0300";
		Author author = Author.fromString(authorLine);

		Author chico = new AuthorBuilder().withName("Francisco Sokol").withEmail("chico.sokol@gmail.com").create();
		assertThat(author, sameAuthor(chico));
	}

}
