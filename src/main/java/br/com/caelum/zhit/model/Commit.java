package br.com.caelum.zhit.model;

public class Commit {

	private final String message;
	private final String author;

	public Commit(String author, String message) {
		this.author = author;
		this.message = message;
	}

	public String message() {
		return message;
	}
	
	public String author() {
		return author;
	}


}
