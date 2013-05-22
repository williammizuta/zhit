package br.com.caelum.zhit.infra;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class GitObjectInflaterTest {

	@Test
	public void should_inflate_git_object() throws Exception {
		File file = new File("src/test/resources/sample-with-objects/objects/25/0f67ef017fcb97b5371a302526872cfcadad21");
		String inflate = new GitObjectInflater().inflate(file);
		assertTrue(inflate.contains("author"));
		assertTrue(inflate.contains("tree"));
		assertTrue(inflate.contains("committer"));
	}
	
}
