package br.com.caelum.zhit.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AuthorTest {

	@Test
	public void should_build_author() {
		String authorLine = "author Francisco Sokol <chico.sokol@gmail.com> 1369140112 -0300";
		Author author = Author.fromString(authorLine);
		assertEquals("Francisco Sokol", author.name());
		assertEquals("chico.sokol@gmail.com", author.email());
	}

}
