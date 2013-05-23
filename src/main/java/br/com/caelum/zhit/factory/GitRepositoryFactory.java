package br.com.caelum.zhit.factory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

import br.com.caelum.zhit.model.GitRepository;

public class GitRepositoryFactory {

	private File path;

	public GitRepositoryFactory(File path) {
		if (!path.exists()) {
			throw new IllegalArgumentException(path.getPath() + " doesn't exist");
		}
		this.path = path;
	}
	
	public GitRepository build(String name) {
		File repositoryRoot = new File(path, name);
		repositoryRoot.mkdir();
		copyDotGit(new File(repositoryRoot, ".git"));
		
		return GitRepository.local(repositoryRoot);
	}

	private void copyDotGit(File destDir) {
		destDir.mkdir();
		mkdir(destDir, "refs");
		File info = mkdir(destDir, "info");
		File hooks = mkdir(destDir, "hooks");
		File objects = mkdir(destDir, "objects");
		mkdir(objects, "info");
		mkdir(objects, "pack");
		
		try {
			copyGitSampleResource(destDir, "HEAD");
			copyGitSampleResource(destDir, "config");
			copyGitSampleResource(destDir, "description");
            copyGitSampleResource(hooks, "hooks/applypatch-msg.sample");
            copyGitSampleResource(hooks, "hooks/commit-msg.sample");
            copyGitSampleResource(hooks, "hooks/post-update.sample");
            copyGitSampleResource(hooks, "hooks/pre-applypatch.sample");
            copyGitSampleResource(hooks, "hooks/pre-commit.sample");
            copyGitSampleResource(hooks, "hooks/pre-rebase.sample");
            copyGitSampleResource(hooks, "hooks/prepare-commit-msg.sample");
            copyGitSampleResource(hooks, "hooks/update.sample");
            copyGitSampleResource(info, "info/exclude");
		} catch (URISyntaxException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	private File mkdir(File parent, String dest) {
		File info = new File(parent, dest);
		info.mkdir();
		return info;
	}

	private void copyGitSampleResource(File destDir, String resource) throws URISyntaxException, IOException {
		String[] split = resource.split("/");
		String fileName = split[split.length - 1];

		InputStream is = getClass().getResourceAsStream("/git-sample/" + resource);
		Scanner scanner = new Scanner(is);
		String content = scanner.useDelimiter("\\A").next();
		FileUtils.write(new File(destDir, fileName), content);
		scanner.close();
	}

	public GitRepository buildBare(String name) {
		File repositoryRoot = new File(path, name);
		repositoryRoot.mkdir();
		copyDotGit(repositoryRoot);
		rewriteConfig(repositoryRoot);

		return GitRepository.bare(repositoryRoot);
	}

	private void rewriteConfig(File repositoryRoot) {
		try {
			File file = new File(repositoryRoot, "config");
			String content = FileUtils.readFileToString(file);
			String newContent = content.replace("bare = false", "bare = true");
			FileUtils.write(file, newContent);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
