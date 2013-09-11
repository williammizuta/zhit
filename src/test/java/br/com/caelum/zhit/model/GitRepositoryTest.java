package br.com.caelum.zhit.model;

import static br.com.caelum.zhit.matchers.ZhitMatchers.sameBranch;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.zhit.model.internal.Sha1;

public class GitRepositoryTest {

	@Test
	public void should_find_head_object() {
		GitRepository repository = GitRepository.bare(new File("src/test/resources/sample-with-head"));
		GitCommit head = repository.head();
		assertEquals("first commit", head.message());
	}
	
	@Test
	public void should_list_all_local_branches() throws Exception {
		GitRepository repository = GitRepository.bare(new File("src/test/resources/sample-with-branches"));
		List<GitBranch> branches = repository.branches();
		
		GitBranch master = new GitBranch("master", new Sha1("ebc00cc02fe9aab1257b23465a78e0465f4144f3"));
		GitBranch comItau = new GitBranch("com-itau", new Sha1("d793219787c1dbd20e44300200a4750f11ac2a38"));
		
		Assert.assertThat(branches, hasItems(sameBranch(master), sameBranch(comItau)));
	}

}
