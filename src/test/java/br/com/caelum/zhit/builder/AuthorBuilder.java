package br.com.caelum.zhit.builder;

import java.lang.reflect.Field;

import br.com.caelum.zhit.model.Author;

public class AuthorBuilder {

	private final Author author;

	public AuthorBuilder() {
		author = Author.fromString("author Zhit <zhit@zhit.com> 1369140112 -0300");
	}

	public AuthorBuilder withName(String name) {
		changeField("name", name);
		return this;
	}

	public AuthorBuilder withEmail(String email) {
		changeField("email", email);
		return this;
	}

	public Author create() {
		return author;
	}

	private void changeField(String fieldName, String name) {
		try {
			Field field = author.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(author, name);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
