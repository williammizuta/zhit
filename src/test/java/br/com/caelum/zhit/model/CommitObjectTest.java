package br.com.caelum.zhit.model;

import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.zip.InflaterInputStream;

import org.apache.commons.io.IOUtils;
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
	
	@Test
	public void escovandoBytes() throws Exception {
		File dotGit = new File("src/test/resources/sample-repository/.git/objects/25/0f67ef017fcb97b5371a302526872cfcadad21");
		InflaterInputStream inflaterInputStream = new InflaterInputStream(new FileInputStream(dotGit));
		Integer read = inflaterInputStream.read();
		while(read != 0) {
			read = inflaterInputStream.read();
			System.out.println((char)read.byteValue());
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		IOUtils.copyLarge(inflaterInputStream, os);
		System.out.println(new String(os.toByteArray()));
	}
	
}
