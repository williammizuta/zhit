package br.com.caelum.zhit.model.internal;

import static br.com.caelum.zhit.matchers.ZhitMatchers.sameGitCommit;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.zhit.builder.AuthorBuilder;
import br.com.caelum.zhit.model.Author;
import br.com.caelum.zhit.model.GitCommit;
import br.com.caelum.zhit.model.GitRepository;

public class GitCommitIteratorTest {
	
	private Author author;

	@Before
	public void setup() {
		author = new AuthorBuilder()
			.withName("Francisco Sokol")
			.withEmail("chico.sokol@gmail.com").create();
	}

	@Test
	public void should_iterate_over_three_commits() {
		GitRepository repository = GitRepository.bare(new File("src/test/resources/sample-with-history"));
		GitCommit third = repository.head();
		
		GitCommit expectedThird = new GitCommit(author, "third commit", new Sha1("4b825dc642cb6eb9a060e54bf8d69288fbee4904"), 
				asList(new Sha1("b82da37b7455f932c3db398bece665aeac451ac8")), repository);
		GitCommit expectedSecond = new GitCommit(author, "second commit", new Sha1("d4db8957618b02a72fe78f192e528169bd197a21"), 
				asList(new Sha1("52a3dea7240eae5fa0fe435783bb287d5488f0be")), repository);
		GitCommit expectedFirst = new GitCommit(author, "first commit", new Sha1("acac085b7f5e5523fc9d943d1bcc942684d9a3b8"), 
				Collections.<Sha1>emptyList(), repository);
		
		GitCommitIterator gitCommitIterator = new GitCommitIterator(third);
		assertThat(gitCommitIterator.next(), sameGitCommit(expectedThird));
		
		assertTrue(gitCommitIterator.hasNext());
		GitCommit second = gitCommitIterator.next();
		assertThat(second, sameGitCommit(expectedSecond));
		
		assertTrue(gitCommitIterator.hasNext());
		GitCommit first = gitCommitIterator.next();
		assertThat(first, sameGitCommit(expectedFirst));
	}
	
	@Test
	public void should_iterate_over_merged_commits() {
		GitRepository repository = GitRepository.bare(new File("src/test/resources/sample-with-merge-history"));
		
		GitCommitIterator history = new GitCommitIterator(repository.head());
		
		GitCommit expectedMerge = new GitCommit(author, "Merge branch 'branch'", new Sha1("e7326e5f9580300126f4bdfe0ee62ec04f00bfca"), Arrays.asList(new Sha1("d4650132c23a130710ec018cedd587ebe473121f"), new Sha1("7bd7f06744c440fde45ec63cca1e9e1772420746")), repository);
		GitCommit expectedThird = new GitCommit(author, "third commit", new Sha1("4b825dc642cb6eb9a060e54bf8d69288fbee4904"), 
				asList(new Sha1("b82da37b7455f932c3db398bece665aeac451ac8")), repository);
		
		assertThat(history.next(), sameGitCommit(expectedMerge));
	}

}
