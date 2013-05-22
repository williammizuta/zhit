package br.com.caelum.zhit.infra;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.InflaterInputStream;

import org.apache.commons.io.IOUtils;

public class GitObjectInflater {

	public String inflate(File file) {
		try {
			InflaterInputStream inflaterInputStream = new InflaterInputStream(new FileInputStream(file));
			Integer read = inflaterInputStream.read();
			while (read != 0) {
				read = inflaterInputStream.read();
			}
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			IOUtils.copyLarge(inflaterInputStream, os);
			return new String(os.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
