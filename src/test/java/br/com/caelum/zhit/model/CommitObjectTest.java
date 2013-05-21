package br.com.caelum.zhit.model;

import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;

public class CommitObjectTest {

	@Test
	public void should_get_object_contents_from_head_commit_object() {
		File dotGit = new File("src/test/resources/sample-repository/.git/");
		String commitSha1 = "250f67ef017fcb97b5371a302526872cfcadad21";
		CommitObject commitObject = new CommitObject(commitSha1, dotGit);
		commitObject.extractCommit();
		fail();
		//TODO: asserts
	}

}
