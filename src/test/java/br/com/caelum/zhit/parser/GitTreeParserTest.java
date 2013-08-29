package br.com.caelum.zhit.parser;

import static br.com.caelum.zhit.matchers.ZhitMatchers.sameRawGitTreeEntry;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import br.com.caelum.zhit.model.GitTree;
import br.com.caelum.zhit.model.internal.EntryType;
import br.com.caelum.zhit.model.internal.RawGitTreeEntry;
import br.com.caelum.zhit.model.internal.Sha1;

public class GitTreeParserTest {

	@Test
	public void test() {
		GitTreeParser treeFactory = new GitTreeParser();
		GitTree tree = treeFactory.parse("040000 tree 8add0d07efc6ba027407c82740a001cfcbc7b772    dir\n" +
				"100644 blob e69de29bb2d1d6434b8b29ae775ad8c2e48c5391    file");
		
		
		RawGitTreeEntry entry = tree.entries().get(0);
		assertThat(entry, sameRawGitTreeEntry(new RawGitTreeEntry("040000", EntryType.TREE, new Sha1("8add0d07efc6ba027407c82740a001cfcbc7b772"), "dir")));
		
		entry = tree.entries().get(1);
		assertThat(entry, sameRawGitTreeEntry(new RawGitTreeEntry("100644", EntryType.BLOB, new Sha1("e69de29bb2d1d6434b8b29ae775ad8c2e48c5391"), "file")));
		
	}

}
