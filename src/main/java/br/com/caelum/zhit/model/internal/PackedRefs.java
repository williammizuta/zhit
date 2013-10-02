package br.com.caelum.zhit.model.internal;

import static br.com.caelum.zhit.infra.ZhitFileUtils.readFileToString;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.caelum.zhit.model.GitBranch;

public class PackedRefs {

	private File packedRefs;

	public PackedRefs(File file) {
		this.packedRefs = new File(file, "packed-refs");
	}

	public Collection<GitBranch> remotes() {
		List<String> packedRefsLines = packedRefsLines();
		List<GitBranch> remotes = new ArrayList<>();
		for(String line : packedRefsLines) {
			String[] splittedLine = line.split(" ");
			if (splittedLine.length > 1 && splittedLine[1].startsWith("refs/remotes")) {
				remotes.add(new GitBranch(splittedLine[1].replace("refs/remotes/", ""), new Sha1(splittedLine[0])));
			}
		}
		return remotes;
	}

	public List<String> packedRefsLines() {
		if (packedRefs.exists()) {
			String content = readFileToString(packedRefs);
			return asList(content.split("\n"));
		}
		return emptyList();
	}
}
