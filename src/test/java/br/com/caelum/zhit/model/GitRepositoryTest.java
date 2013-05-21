package br.com.caelum.zhit.model;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class GitRepositoryTest {

	@Test
	public void should_find_head_object() {
		GitRepository repository = GitRepository.local(new File("src/test/resources/sample-repository"));
		CommitObject head = repository.head();
		String expected = "250f67ef017fcb97b5371a302526872cfcadad21";
		assertEquals(expected, head.getSha1());
	}

}
