package br.com.caelum.zhit.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.caelum.zhit.model.GitTree;
import br.com.caelum.zhit.model.internal.RawGitTreeEntry;

public class GitTreeParserTest {

	@Test
	public void test() {
		GitTreeParser treeFactory = new GitTreeParser();
		GitTree tree = treeFactory.parse("040000 tree 8add0d07efc6ba027407c82740a001cfcbc7b772    dir\n" +
				"100644 blob e69de29bb2d1d6434b8b29ae775ad8c2e48c5391    file");
		
		
		RawGitTreeEntry entry = tree.entries().get(0);
		assertEquals("040000", entry.permissions());
		assertEquals("tree", entry.type());
		assertEquals("8add0d07efc6ba027407c82740a001cfcbc7b772", entry.sha1().hash());
		
		entry = tree.entries().get(1);
		assertEquals("100644", entry.permissions());
		assertEquals("blob", entry.type());
		assertEquals("e69de29bb2d1d6434b8b29ae775ad8c2e48c5391", entry.sha1().hash());
		
	}

}
