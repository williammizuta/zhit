package br.com.caelum.zhit.model.internal;

import static br.com.caelum.zhit.matchers.ZhitMatchers.sameBranch;
import static org.hamcrest.Matchers.hasItem;
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
		
		assertThat(refs.remotes(), hasItem(sameBranch(remoteBranch)));
	}
}
