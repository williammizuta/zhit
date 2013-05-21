package br.com.caelum.zhit.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.commons.io.FileUtils;

public class ZhitFileUtils {

	public static String readFileToString(File file) {
		try {
			return FileUtils.readFileToString(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * based on http://thiscouldbebetter.wordpress.com/2011/08/26/compressing-and-uncompressing-data-in-java-using-zlib/
	 */
	public static byte[] compress(byte[] bytesToCompress) {
		Deflater deflater = new Deflater();
		deflater.setInput(bytesToCompress);
		deflater.finish();

		byte[] bytesCompressed = new byte[Short.MAX_VALUE];

		int numberOfBytesAfterCompression = deflater.deflate(bytesCompressed);

		byte[] returnValues = new byte[numberOfBytesAfterCompression];

		System.arraycopy(bytesCompressed, 0, returnValues, 0,
				numberOfBytesAfterCompression);

		return returnValues;
	}

	public byte[] compress(String stringToCompress) {
		byte[] returnValues = null;

		try {
			returnValues = compress(stringToCompress.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		}

		return returnValues;
	}

	public static byte[] decompress(byte[] bytesToDecompress) {
		Inflater inflater = new Inflater();

		int numberOfBytesToDecompress = bytesToDecompress.length;

		inflater.setInput(bytesToDecompress, 0, numberOfBytesToDecompress);

		int compressionFactorMaxLikely = 3;

		int bufferSizeInBytes = numberOfBytesToDecompress
				* compressionFactorMaxLikely;

		byte[] bytesDecompressed = new byte[bufferSizeInBytes];

		byte[] returnValues = null;

		try {
			int numberOfBytesAfterDecompression = inflater
					.inflate(bytesDecompressed);

			returnValues = new byte[numberOfBytesAfterDecompression];

			System.arraycopy(bytesDecompressed, 0, returnValues, 0,
					numberOfBytesAfterDecompression);
		} catch (DataFormatException dfe) {
			dfe.printStackTrace();
		}

		inflater.end();

		return returnValues;
	}

	public static String decompressToString(byte[] bytesToDecompress) {
		byte[] bytesDecompressed = decompress(bytesToDecompress);

		String returnValue = null;

		try {
			returnValue = new String(bytesDecompressed, 0,
					bytesDecompressed.length, "UTF-8");
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		}

		return returnValue;
	}

	public static byte[] readFileToByteArray(File file) {
		try {
			return FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


}
