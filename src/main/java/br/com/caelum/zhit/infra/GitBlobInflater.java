package br.com.caelum.zhit.infra;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.zip.InflaterInputStream;

import org.apache.commons.io.IOUtils;

public class GitBlobInflater implements GitObjectInflater {

	@Override
	public String inflate(File file) {
		try {
			return copyContents(file);
		} catch (IOException e) {
			throw new RuntimeException("Error reading object file", e);
		}
	}

	private String copyContents(File file) throws IOException, FileNotFoundException {
		try(InflaterInputStream inflaterInputStream = new InflaterInputStream(new FileInputStream(file))) {
			skipMetaData(inflaterInputStream);
			
			Writer writer = new StringWriter();
			IOUtils.copy(inflaterInputStream, writer);
			return writer.toString();
		}
	}

	private void skipMetaData(InflaterInputStream inflaterInputStream)
			throws IOException {
		byte read;
		do {
			read = (byte) inflaterInputStream.read();
		} while (read != 0);
	}

}
