package br.com.caelum.zhit.model.internal;

import static br.com.caelum.zhit.infra.ZhitFileUtils.readFileToString;
import static br.com.caelum.zhit.model.ZhitFunctions.extractBranch;
import static br.com.caelum.zhit.model.ZhitPredicates.grep;
import static br.com.caelum.zhit.model.ZhitPredicates.linesWithBranches;
import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.caelum.zhit.model.GitBranch;

public class PackedRefs {

	private final List<String> packedRefsLines;

	public PackedRefs(File file) {
		File packedRefs = new File(file, "packed-refs");
		if (packedRefs.exists()) {
			String content = readFileToString(packedRefs);
			packedRefsLines = asList(content.split("\n"));
		} else {
			packedRefsLines = emptyList();
		}
	}

	public Collection<GitBranch> locals() {
		Collection<String> linesWithBranches = filter(packedRefsLines, linesWithBranches("refs/heads/"));
		return transform(linesWithBranches, extractBranch("/"));
	}

	public Collection<GitBranch> remotes() {
		Collection<String> linesWithBranches = filter(packedRefsLines, linesWithBranches("refs/remotes/"));
		return transform(linesWithBranches, extractBranch("remotes/"));
	}

	public Sha1 sha1(String branch) {
		ArrayList<String> headLines = new ArrayList<String>(filter(packedRefsLines, grep(branch)));
		if (headLines.isEmpty()) {
			throw new IllegalArgumentException("could not find " + branch);
		}

		return new Sha1(headLines.get(0).split("\\s")[0]);
	}

}
