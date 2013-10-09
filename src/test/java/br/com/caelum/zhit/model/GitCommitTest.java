package br.com.caelum.zhit.model;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;

public class GitCommitTest {

	@Test
	public void should_list_all_files_from_commit() {
		GitRepository repository = GitRepository.bare(new File("src/test/resources/sample-with-many-files"));
		GitCommit commit = repository.head();
		List<String> files = commit.listFiles();
		
		assertThat(files, Matchers.containsInAnyOrder("hello.txt","src/main/br/com/caelum/main.java",
			"src/test/br/com/caelum/main.java","src/test/br/com/caelum/package/file.txt","world.exe"));
	}
}
