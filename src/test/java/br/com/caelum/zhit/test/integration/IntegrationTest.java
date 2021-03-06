package br.com.caelum.zhit.test.integration;

import static br.com.caelum.zhit.matchers.ZhitMatchers.sameRawGitTreeEntry;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.caelum.zhit.matchers.ZhitMatchers;
import br.com.caelum.zhit.model.GitBlob;
import br.com.caelum.zhit.model.GitCommit;
import br.com.caelum.zhit.model.GitHistory;
import br.com.caelum.zhit.model.GitRepository;
import br.com.caelum.zhit.model.GitTree;
import br.com.caelum.zhit.model.internal.EntryType;
import br.com.caelum.zhit.model.internal.RawGitTreeEntry;
import br.com.caelum.zhit.model.internal.Sha1;

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

		Collection<RawGitTreeEntry> files = gitTree.files();
		Collection<RawGitTreeEntry> dirs = gitTree.dirs();

		assertThat(files, hasSize(8));
		assertThat(dirs, hasSize(14));

		RawGitTreeEntry expected = new RawGitTreeEntry("100644", EntryType.BLOB, new Sha1("2beae51a0e14b3167fd7e81119972caef95779f4"), ".gitignore");
		assertThat(files, Matchers.hasItem(ZhitMatchers.sameRawGitTreeEntry(expected)));
	}

	@Test
	public void shouldCheckContentsOfASubtree() throws Exception {
		GitCommit head = repository.head();
		GitTree headTree = head.tree();
		RawGitTreeEntry dir = headTree.dirs().iterator().next();
		GitTree tree = repository.parseTree(dir.sha1());

		assertThat(tree.dirs(), hasSize(7));
		assertThat(tree.files(), hasSize(0));

		RawGitTreeEntry expected = new RawGitTreeEntry("040000", EntryType.TREE, new Sha1("1e409c824686f490dcbd7838adb675915bbd72b3"), "core-example");
		assertThat(tree.dirs(), hasItem(sameRawGitTreeEntry(expected)));
	}

	@Test
	public void shouldParseBlobContents() throws Exception {
		GitTree tree = repository.head().tree();
		RawGitTreeEntry gitignore = tree.files().iterator().next();
		GitBlob blob = repository.parseBlob(gitignore.sha1());

		assertThat(blob.content(), allOf(containsString("cobertura.ser"),
				containsString("out"), containsString("test.properties"),
				containsString("*.swp")));
	}

	@Test
	public void should_iterate_over_history() throws Exception {
		GitHistory history = repository.history();

		assertNotNull(history);
		for (GitCommit commit : history) {
			assertNotNull(commit);
		}

	}

}
