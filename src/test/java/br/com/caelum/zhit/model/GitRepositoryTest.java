package br.com.caelum.zhit.model;

import static br.com.caelum.zhit.matchers.ZhitMatchers.sameBranch;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Collection;
import java.util.List;

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
	public void should_list_all_local_branches() {
		GitRepository repository = GitRepository.bare(new File("src/test/resources/sample-with-branches"));
		List<GitBranch> branches = repository.branches();
		
		GitBranch master = new GitBranch("master", new Sha1("ebc00cc02fe9aab1257b23465a78e0465f4144f3"));
		GitBranch comItau = new GitBranch("com-itau", new Sha1("d793219787c1dbd20e44300200a4750f11ac2a38"));
		GitBranch localbranch = new GitBranch("localbranch", new Sha1("250f67ef017fcb97b5371a302526872cfcadad21"));
		GitBranch oldLocalbranch = new GitBranch("localbranch", new Sha1("fda4ec629684b949f241aeeb9f5ee6da4289c7b7 "));
		
		assertThat(branches, hasItems(sameBranch(master), sameBranch(comItau), sameBranch(localbranch)));
		assertThat(branches, not(hasItems(sameBranch(oldLocalbranch))));
	}
	
	@Test
	public void should_list_all_remote_branches() {
		GitRepository repository = GitRepository.bare(new File("src/test/resources/sample-with-remote-branches"));
		
		Collection<GitBranch> branches = repository.remoteBranches();
		
		GitBranch remoteBranch = new GitBranch("origin/remote-test", new Sha1("d793219787c1dbd20e44300200a4750f11ac2a38"));
		GitBranch anotherRemoteBranch = new GitBranch("another-remote/another-remote-branch", new Sha1("af7424836884cdf85fec61ca3035529ffbb649b2"));
		
		assertThat(branches, hasItems(sameBranch(remoteBranch), sameBranch(anotherRemoteBranch)));
	}

}
