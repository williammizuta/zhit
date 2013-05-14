package br.com.caelum.zhit.factory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
		File hooks = new File(destDir, "hooks");
		hooks.mkdir();
		File info = new File(destDir, "info");
		info.mkdir();
		
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

	private void copyGitSampleResource(File destDir, String resource) throws URISyntaxException,
			IOException {
		URI url = getClass().getResource("/git-sample/" + resource).toURI();
		FileUtils.copyFileToDirectory(new File(url), destDir);
	}

	public Repository buildBare(String name) {
		File repositoryRoot = new File(path, name);
		repositoryRoot.mkdir();
		copyDotGit(repositoryRoot);

		return Repository.bare(repositoryRoot);
	}

}
