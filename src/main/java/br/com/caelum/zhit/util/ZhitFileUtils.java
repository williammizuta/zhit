package br.com.caelum.zhit.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class ZhitFileUtils {

	public static String readFileToString(File file) {
		try {
			return FileUtils.readFileToString(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
