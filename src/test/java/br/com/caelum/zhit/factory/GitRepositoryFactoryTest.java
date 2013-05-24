package br.com.caelum.zhit.factory;

import static br.com.caelum.zhit.matchers.ZhitMatchers.isAValidBareRepository;
import static br.com.caelum.zhit.matchers.ZhitMatchers.isAValidNonBareRepository;
import static org.junit.Assert.assertThat;

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
	public void should_create_a_non_bare_repository() {
		GitRepository repository = factory.build(projectName);
		assertThat(repository, isAValidNonBareRepository());
	}

	@Test
	public void should_create_a_bare_repository() {
		GitRepository repository = factory.buildBare(projectName);
		assertThat(repository, isAValidBareRepository());
	}

}
