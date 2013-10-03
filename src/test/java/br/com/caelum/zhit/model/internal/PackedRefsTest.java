package br.com.caelum.zhit.model.internal;

import static br.com.caelum.zhit.matchers.ZhitMatchers.sameBranch;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

import br.com.caelum.zhit.model.GitBranch;

public class PackedRefsTest {

	@Test
	public void should_list_only_remote_branches() {
		File repository = new File("src/test/resources/sample-with-packedrefs");
		PackedRefs refs = new PackedRefs(repository);
		GitBranch remoteBranch = new GitBranch("origin/sets", new Sha1("67377001ff76aed4e327deb489b4c3c43ca51519"));
		
		assertThat(refs.remotes(), contains(sameBranch(remoteBranch)));
	}

	@Test
	public void should_list_only_local_branches() {
		File repository = new File("src/test/resources/sample-with-packedrefs");
		PackedRefs refs = new PackedRefs(repository);
		GitBranch localBranch = new GitBranch("com-itau@536", new Sha1("b9181a66550ca8d8ae7eff31ca276b5f5dcb584f"));

		assertThat(refs.locals(), contains(sameBranch(localBranch)));
	}

}
