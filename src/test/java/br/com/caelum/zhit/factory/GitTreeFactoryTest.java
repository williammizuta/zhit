package br.com.caelum.zhit.factory;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.caelum.zhit.model.GitTree;

public class GitTreeFactoryTest {

	@Test
	public void test() {
		GitTreeFactory treeFactory = new GitTreeFactory();
		GitTree tree = treeFactory.build("040000 tree 8add0d07efc6ba027407c82740a001cfcbc7b772    dir\n" +
				"100644 blob e69de29bb2d1d6434b8b29ae775ad8c2e48c5391    file");
		
		assertEquals("100644 blob e69de29bb2d1d6434b8b29ae775ad8c2e48c5391    file", tree.blobs().get(0));
		assertEquals("040000 tree 8add0d07efc6ba027407c82740a001cfcbc7b772    dir", tree.trees().get(0));
		
	}

}
