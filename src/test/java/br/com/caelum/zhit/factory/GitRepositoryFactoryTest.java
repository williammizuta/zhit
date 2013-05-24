package br.com.caelum.zhit.factory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.caelum.zhit.model.GitRepository;

public class GitRepositoryFactoryTest {

	private static File repositoriesPath;
	private String projectName;
	private GitRepositoryFactory factory;

	@BeforeClass
	public static void setUp() {
		repositoriesPath = new File("src/test/resources/repositories/");
		repositoriesPath.mkdirs();
	}

	@Before
	public void before() {
		projectName = "zhit";
		factory = new GitRepositoryFactory(repositoriesPath);
	}

	@AfterClass
	public static void tearDown() throws IOException {
		FileUtils.deleteDirectory(repositoriesPath);
	}

	@Test(expected=IllegalArgumentException.class)
	public void should_check_if_path_does_not_exits() {
		String pathname = "/home/zhit";
		String projectName = "zhit";
		GitRepositoryFactory factory = new GitRepositoryFactory(new File(pathname));
		factory.build(projectName);
	}

	@Test
	public void should_create_repository_root_directory() {
		GitRepository repository = factory.build(projectName);
		assertTrue(repository.path().exists());
		assertFalse(repository.isBare());
	}

	@Test
	public void should_create_git_internals_files() {
		GitRepository repository = factory.build(projectName);
		assertTrue(new File(repository.path(), ".git").exists());
		assertFalse(repository.isBare());
	}

	@Test
	public void should_create_a_bare_repository() {
		GitRepository repository = factory.buildBare(projectName);
		assertTrue(repository.isBare());
		assertTrue(new File(repository.path(), "HEAD").exists());
	}

	@Test
	public void should_create_a_bare_repository_and_rewrite_config_file() throws IOException {
		GitRepository repository = factory.buildBare(projectName);
		String config = FileUtils.readFileToString(new File(repository.path(), "config"));
		assertTrue(config.contains("bare = true"));
	}

}
