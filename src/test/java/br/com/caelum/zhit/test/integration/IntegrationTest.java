package br.com.caelum.zhit.test.integration;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.caelum.zhit.model.GitCommit;
import br.com.caelum.zhit.model.GitRepository;
import br.com.caelum.zhit.model.GitTree;
import br.com.caelum.zhit.model.internal.RawGitTreeEntry;
import br.com.caelum.zhit.parser.GitTreeParser;

public class IntegrationTest {
	
	private static File resources;

	@BeforeClass
	public static void setupClass() throws InterruptedException, IOException {
		resources = new File("src/test/resources");
		Process exec = Runtime.getRuntime().exec("tar xzf caelum-stella.git.tar.gz", new String[]{}, resources);
		exec.waitFor();
		
	}
	
	@AfterClass
	public static void tearDown() throws IOException {
		FileUtils.deleteDirectory(new File(resources, "caelum-stella.git"));
	}
	
	@Test
	public void shouldCheckCurrentFiles() throws Exception {
		GitRepository repository = GitRepository.bare(new File(resources, "caelum-stella.git"));
		GitCommit head = repository.head();
		GitTree gitTree = head.tree().extract(new GitTreeParser());
		List<RawGitTreeEntry> files = gitTree.files();
		System.out.println(files);
		
	}
	
	
	

}
