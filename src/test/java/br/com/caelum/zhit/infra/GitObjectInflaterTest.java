package br.com.caelum.zhit.infra;

import static br.com.caelum.zhit.matchers.ZhitMatchers.isAValidGitCommit;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

public class GitObjectInflaterTest {

	@Test
	public void should_inflate_a_commit_git_object() throws Exception {
		File commitObject = new File("src/test/resources/sample-with-objects/objects/25/0f67ef017fcb97b5371a302526872cfcadad21");
		String commitInformation = new GitObjectInflater().inflate(commitObject);
		assertThat(commitInformation, isAValidGitCommit());
	}

}
