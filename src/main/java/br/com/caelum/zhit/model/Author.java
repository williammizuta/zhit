package br.com.caelum.zhit.model;

public class Author {

	private String name;
	private String email;

	private Author(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	public static Author fromString(String authorLine) {
		String[] split = authorLine.split(" <");
		String name = split[0].substring(7);
		String email = split[1].substring(0, split[1].indexOf(">"));
		return new Author(name, email);
	}
	
	public String email() {
		return email;
	}
	
	public String name() {
		return name;
	}


}