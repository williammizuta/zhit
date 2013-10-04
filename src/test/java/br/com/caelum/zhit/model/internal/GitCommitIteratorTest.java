package br.com.caelum.zhit.model.internal;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import br.com.caelum.zhit.model.GitCommit;
import br.com.caelum.zhit.model.GitRepository;

public class GitCommitIteratorTest {

	@Test
	public void should_iterate_over_three_commits() {
		GitRepository repository = GitRepository.bare(new File("src/test/resources/sample-with-history"));
		GitCommit third = repository.head();
		
		assertEquals("third commit", third.message());
		
		GitCommitIterator gitCommitIterator = new GitCommitIterator(third);
		
		assertTrue(gitCommitIterator.hasNext());
		GitCommit second = gitCommitIterator.next();
		assertEquals("second commit", second.message());
		
		assertTrue(gitCommitIterator.hasNext());
		GitCommit first = gitCommitIterator.next();
		assertEquals("first commit", first.message());
	}

}
