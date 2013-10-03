package br.com.caelum.zhit.model.internal;

import static br.com.caelum.zhit.matchers.ZhitMatchers.sameBranch;
import static br.com.caelum.zhit.matchers.ZhitMatchers.sameSha1;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.zhit.model.GitBranch;

public class PackedRefsTest {

	private PackedRefs refs;

	@Before
	public void setup() {
		File repository = new File("src/test/resources/sample-with-packedrefs");
		refs = new PackedRefs(repository);
	}

	@Test
	public void should_list_only_remote_branches() {
		GitBranch remoteBranch = new GitBranch("origin/sets", new Sha1("67377001ff76aed4e327deb489b4c3c43ca51519"));
		assertThat(refs.remotes(), contains(sameBranch(remoteBranch)));
	}

	@Test
	public void should_list_only_local_branches() {
		GitBranch localBranch = new GitBranch("com-itau@536", new Sha1("b9181a66550ca8d8ae7eff31ca276b5f5dcb584f"));
		assertThat(refs.locals(), contains(sameBranch(localBranch)));
	}

	@Test
	public void should_return_branch_hash() {
		Sha1 sha1 = new Sha1("b9181a66550ca8d8ae7eff31ca276b5f5dcb584f");
		assertThat(refs.sha1("com-itau@536"), sameSha1(sha1));
	}

	@Test(expected=IllegalArgumentException.class)
	public void should_tell_if_the_branch_doesnt_exist_in_refshead() {
		refs.sha1("branch-that-doesnt-exist");
	}

}
