package br.com.caelum.zhit.factory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.caelum.zhit.model.Repository;

public class RepositoryFactoryTest {

	private static File repositoriesPath;
	private String projectName;
	private RepositoryFactory factory;

	@BeforeClass
	public static void setUp() {
		repositoriesPath = new File("src/test/resources/repositories/");
		repositoriesPath.mkdirs();
	}
	
	@Before
	public void before() {
		projectName = "zhit";
		factory = new RepositoryFactory(repositoriesPath);
	}
	
	@AfterClass
	public static void tearDown() throws IOException {
		FileUtils.deleteDirectory(repositoriesPath);
	}

	@Test(expected=IllegalArgumentException.class)
	public void should_check_if_path_does_not_exits() {
		String pathname = "/home/zhit";
		String projectName = "zhit";
		RepositoryFactory factory = new RepositoryFactory(new File(pathname));
		factory.build(projectName);
	}
	
	@Test
	public void should_create_repository_root_directory() {
		Repository repository = factory.build(projectName);
		Assert.assertTrue(repository.getPath().exists());
		assertFalse(repository.isBare());
	}
	
	@Test
	public void should_create_git_internals_files() {
		Repository repository = factory.build(projectName);
		assertTrue(new File(repository.getPath(), ".git").exists());
		assertFalse(repository.isBare());
	}
	
	@Test
	public void should_create_a_bare_repository() {
		Repository repository = factory.buildBare(projectName);
		assertTrue(repository.isBare());
		assertTrue(new File(repository.getPath(), "HEAD").exists());
	}
	
	@Test
	public void should_create_a_bare_repository_and_rewrite_config_file() throws IOException {
		Repository repository = factory.buildBare(projectName);
		String config = FileUtils.readFileToString(new File(repository.getPath(), "config"));
		assertTrue(config.contains("bare = true"));
	}
	
}
