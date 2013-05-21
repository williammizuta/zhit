package br.com.caelum.zhit.model;

import static java.io.File.separator;

import java.io.File;

import br.com.caelum.zhit.factory.CommitFactory;
import br.com.caelum.zhit.util.ZhitFileUtils;

public class CommitObject {

	private final String sha1;
	private final File dotGit;

	public CommitObject(String sha1, File dotGit) {
		this.dotGit = dotGit;
		this.sha1 = sha1.trim();
	}
	
	String getSha1() {
		return sha1;
	}

	public Commit extractCommit() {
		byte[] objectFileBytes = ZhitFileUtils.readFileToByteArray(objectFile());
		String objectContents = ZhitFileUtils.decompressToString(objectFileBytes);
		return new CommitFactory().build(objectContents);
	}

	private File objectFile() {
		String dir = sha1.substring(0, 2);
		String fileName = sha1.substring(2);
		File file = new File(dotGit, "objects" + separator + dir + separator + fileName);
		return file;
	}

}
