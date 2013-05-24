package br.com.caelum.zhit.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import br.com.caelum.zhit.model.Author;
import br.com.caelum.zhit.model.GitCommit;

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
				boolean sameTree = commit.tree().equals(testingCommit.tree());
				boolean sameAuthor = sameAuthor(commit.author()).matches(testingCommit.author());
				boolean sameMessage = commit.message().equals(testingCommit.message());

				return sameTree && sameAuthor && sameMessage;
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

}
