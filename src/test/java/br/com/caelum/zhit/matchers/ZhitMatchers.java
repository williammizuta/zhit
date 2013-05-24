package br.com.caelum.zhit.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import br.com.caelum.zhit.model.GitCommit;

public class ZhitMatchers {

	@Factory
	public static Matcher<GitCommit> sameGitCommit(final GitCommit gold) {
		return new TypeSafeMatcher<GitCommit>() {
			public void describeTo(Description description) {
				description.appendText(gold.toString());
			}
			protected boolean matchesSafely(GitCommit commit) {
				boolean sameTree = gold.tree().equals(commit.tree());
				boolean sameAuthorName = gold.author().name().equals(commit.author().name());
				boolean sameAuthorEmail = gold.author().email().equals(commit.author().email());
				boolean sameMessage = gold.message().equals(commit.message());

				return sameTree && sameAuthorName && sameAuthorEmail && sameMessage;
			}
		};
	}

}
