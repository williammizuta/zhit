package br.com.caelum.zhit.test.integration;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.caelum.zhit.infra.GitTreeInflater;
import br.com.caelum.zhit.matchers.ZhitMatchers;
import br.com.caelum.zhit.model.GitBlob;
import br.com.caelum.zhit.model.GitCommit;
import br.com.caelum.zhit.model.GitRepository;
import br.com.caelum.zhit.model.GitTree;
import br.com.caelum.zhit.model.internal.EntryType;
import br.com.caelum.zhit.model.internal.GitObject;
import br.com.caelum.zhit.model.internal.RawGitTreeEntry;
import br.com.caelum.zhit.model.internal.Sha1;
import br.com.caelum.zhit.parser.GitObjectParser;
import br.com.caelum.zhit.parser.GitTreeParser;

public class IntegrationTest {
	
	private static File resources;
	private GitRepository repository;

	@BeforeClass
	public static void setupClass() throws InterruptedException, IOException {
		resources = new File("src/test/resources");
		Process exec = Runtime.getRuntime().exec("tar xzf caelum-stella.git.tar.gz", new String[]{}, resources);
		exec.waitFor();
	}

	@Before
	public void setup() {
		repository = GitRepository.bare(new File(resources, "caelum-stella.git"));
	}
	
	@AfterClass
	public static void tearDown() throws IOException {
		FileUtils.deleteDirectory(new File(resources, "caelum-stella.git"));
	}
	
	@Test
	public void shouldCheckCurrentFiles() throws Exception {
		GitRepository repository = GitRepository.bare(new File(resources, "caelum-stella.git"));
		GitCommit head = repository.head();
		GitTree gitTree = head.tree();
		
		List<RawGitTreeEntry> files = gitTree.files();
		List<RawGitTreeEntry> dirs = gitTree.dirs();
		
		assertThat(files, hasSize(8));
		assertThat(dirs, hasSize(14));
		
		RawGitTreeEntry expected = new RawGitTreeEntry("100644", EntryType.BLOB, new Sha1("2beae51a0e14b3167fd7e81119972caef95779f4"), ".gitignore");
		
		assertThat(files, Matchers.hasItem(ZhitMatchers.sameRawGitTreeEntry(expected)));
	}
	
	@Test
	public void shouldCheckContentsOfASubtree() throws Exception {
		GitCommit head = repository.head();
		GitTree headTree = head.tree();
		
		RawGitTreeEntry dir = headTree.dirs().get(0);
		
		GitTree tree = repository.parseTree(dir.sha1());
		
		assertThat(tree.dirs(), hasSize(7));
		assertThat(tree.files(), hasSize(0));
		
		RawGitTreeEntry expected = new RawGitTreeEntry("040000", EntryType.TREE, 
				new Sha1("1e409c824686f490dcbd7838adb675915bbd72b3"), "core-example");
		assertThat(tree.dirs(), Matchers.hasItem(ZhitMatchers.sameRawGitTreeEntry(expected)));
	}
	
	@Test
	public void shouldParseBlobContents() throws Exception {
		GitTree tree = repository.head().tree();
		RawGitTreeEntry gitignore = tree.files().get(0);
		
		GitBlob blob = repository.parseBlob(gitignore.sha1());
		
		assertThat(blob.content(), allOf(containsString("cobertura.ser"), 
				containsString("out"), containsString("test.properties"), 
				containsString("*.swp")));
	}
	
	@Test
	public void testName() throws Exception {
		repository.head().parents();
		
	}
	
	
	

}
