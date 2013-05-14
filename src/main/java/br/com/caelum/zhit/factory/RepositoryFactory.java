package br.com.caelum.zhit.factory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

import br.com.caelum.zhit.model.Repository;

public class RepositoryFactory {

	private File path;

	public RepositoryFactory(File path) {
		if (!path.exists()) {
			throw new IllegalArgumentException(path.getPath() + " doesn't exist");
		}
		this.path = path;
	}
	
	public Repository build(String name) {
		File repositoryRoot = new File(path, name);
		repositoryRoot.mkdir();
		copyDotGit(new File(repositoryRoot, ".git"));
		
		return Repository.local(repositoryRoot);
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

	private void copyGitSampleResource(File destDir, String resource) throws URISyntaxException,
			IOException {
		InputStream is = getClass().getResourceAsStream("/git-sample/" + resource);
		String content = new Scanner(is).useDelimiter("\\A").next();
		String[] split = resource.split("/");
		String fileName = split[split.length - 1];
		FileUtils.write(new File(destDir, fileName), content);
	}

	public Repository buildBare(String name) {
		File repositoryRoot = new File(path, name);
		repositoryRoot.mkdir();
		copyDotGit(repositoryRoot);
		rewriteConfig(repositoryRoot);

		return Repository.bare(repositoryRoot);
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
