package br.com.caelum.zhit.matchers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import br.com.caelum.zhit.model.Author;
import br.com.caelum.zhit.model.GitCommit;
import br.com.caelum.zhit.model.GitRepository;
import br.com.caelum.zhit.model.GitTree;
import br.com.caelum.zhit.model.internal.RawGitTreeEntry;
import br.com.caelum.zhit.model.internal.Sha1;

public class ZhitMatchers {

	@Factory
	public static Matcher<Author> sameAuthor(final Author author) {
		return new TypeSafeMatcher<Author>() {
			public void describeTo(Description description) {
				description.appendText(author.toString());
			}
			protected boolean matchesSafely(Author testingAuthor) {
				boolean sameName = author.name().equals(testingAuthor.name());
				boolean sameEmail = author.email().equals(testingAuthor.email());

				return sameName && sameEmail;
			}
		};
	}

	@Factory
	public static Matcher<GitCommit> sameGitCommit(final GitCommit commit) {
		return new TypeSafeMatcher<GitCommit>() {
			public void describeTo(Description description) {
				description.appendText(commit.toString());
			}
			protected boolean matchesSafely(GitCommit testingCommit) {
				boolean sameTree = sameSha1(commit.tree()).matches(testingCommit.tree());
				boolean sameAuthor = sameAuthor(commit.author()).matches(testingCommit.author());
				boolean sameMessage = commit.message().equals(testingCommit.message());

				return sameTree && sameAuthor && sameMessage;
			}
		};
	}
	
	@Factory
	public static Matcher<GitTree> sameGitTree(final GitTree tree) {
		return new TypeSafeMatcher<GitTree>() {
			public void describeTo(Description description) {
				description.appendText(tree.toString());
			}
			protected boolean matchesSafely(GitTree testingTree) {
				boolean matches = true;
				List<RawGitTreeEntry> entries = testingTree.entries();
				for (int i = 0; i < entries.size(); i++) {
					RawGitTreeEntry entry = entries.get(i);
					RawGitTreeEntry testingEntry = testingTree.entries().get(i);
					matches = matches && sameRawGitTreeEntry(entry).matches(testingEntry);
				}
				return matches;
			}
		};
	}
	
	@Factory
	public static Matcher<Sha1> sameSha1(final Sha1 sha1) {
		return new TypeSafeMatcher<Sha1>() {
			public void describeTo(Description description) {
				description.appendText(sha1.toString());
			}
			protected boolean matchesSafely(Sha1 testingSha1) {
				return sha1.hash().equals(testingSha1.hash());
			}
		};
	}
	
	@Factory
	public static Matcher<RawGitTreeEntry> sameRawGitTreeEntry(final RawGitTreeEntry entry) {
		return new TypeSafeMatcher<RawGitTreeEntry>() {
			public void describeTo(Description description) {
				description.appendText(entry.toString());
			}
			protected boolean matchesSafely(RawGitTreeEntry testingEntry) {
				boolean sameSha1 = testingEntry.sha1().equals(entry.sha1());
				boolean samePermissions = testingEntry.permissions().equals(entry.permissions());
				boolean sameType = testingEntry.type().equals(entry.type());
				return sameSha1 && samePermissions && sameType;
			}
		};
	}

	@Factory
	public static Matcher<String> isAValidGitCommit() {
		return new TypeSafeMatcher<String>() {
			public void describeTo(Description description) {
				description.appendText("a valid commit content with tree, author and committer");
			}
			protected boolean matchesSafely(String commit) {
				return commit.contains("author") && commit.contains("tree") && commit.contains("committer");
			}
		};
	}

	@Factory
	public static Matcher<GitRepository> isAValidNonBareRepository() {
		return new TypeSafeMatcher<GitRepository>() {
			public void describeTo(Description description) {
				description.appendText("a valid non bare repository");
			}
			protected boolean matchesSafely(GitRepository repository) {
				try {
					File repositoryRoot = repository.path();
					boolean existsRepository = repositoryRoot.exists();

					File dotGit = new File(repositoryRoot, ".git");
					boolean existsDotGit = dotGit.exists();
					boolean existsHead = new File(dotGit, "HEAD").exists();

					String config = FileUtils.readFileToString(new File(dotGit, "config"));
					boolean notBare = !repository.isBare() && config.contains("bare = false");

					return existsRepository && notBare && existsDotGit && existsHead;
				} catch (IOException e) {
					return false;
				}
			}
		};
	}

	@Factory
	public static Matcher<GitRepository> isAValidBareRepository() {
		return new TypeSafeMatcher<GitRepository>() {
			public void describeTo(Description description) {
				description.appendText("a valid bare repository");
			}
			protected boolean matchesSafely(GitRepository repository) {
				try {
					File repositoryRoot = repository.path();
					boolean existsRepository = repositoryRoot.exists();

					String config = FileUtils.readFileToString(new File(repositoryRoot, "config"));
					boolean bare = repository.isBare() && config.contains("bare = true");
					boolean existsHead = new File(repository.path(), "HEAD").exists();

					return existsRepository && bare && existsHead;
				} catch (IOException e) {
					return false;
				}
			}
		};
	}

}
