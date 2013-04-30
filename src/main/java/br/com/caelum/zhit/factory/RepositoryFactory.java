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
		
		URI url;
		try {
			url = getClass().getResource("/git-sample").toURI();
			FileUtils.copyDirectory(new File(url), new File(repositoryRoot, ".git"));
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		
		return new Repository(repositoryRoot);
	}

}