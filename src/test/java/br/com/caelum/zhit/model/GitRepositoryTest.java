package br.com.caelum.zhit.model;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class GitRepositoryTest {

	@Test
	public void should_find_head_object() {
		GitRepository repository = GitRepository.bare(new File("src/test/resources/sample-with-head"));
		GitCommit head = repository.head();
		assertEquals("first commit", head.message());
	}

}
