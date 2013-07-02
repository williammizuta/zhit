package br.com.caelum.zhit.infra;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.InflaterInputStream;

public class GitTreeInflater implements GitObjectInflater {

	private int bytesRead = 0;
	private InflaterInputStream inflaterInputStream;

	@Override
	public String inflate(File file) {
		try(InflaterInputStream inflaterInputStream = new InflaterInputStream(new FileInputStream(file))) {
			this.inflaterInputStream = inflaterInputStream;
			int treeSize = treeSize(inflaterInputStream);
			byte[] treeBytes = new byte[treeSize];
			int bytesToRead = inflaterInputStream.read(treeBytes);
			StringBuilder builder = new StringBuilder();
			while (bytesRead  < bytesToRead) {
				CharBuffer octalMode = readOctalMode(treeBytes);
				CharBuffer fileName = readFileName(treeBytes);
				String hashCode = readHashCode(treeBytes);
				builder
					.append(octalMode)
					.append(fileName)
					.append(" ")
					.append(hashCode)
					.append("\n");
			}
			
			return builder.toString();
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String readHashCode(byte[] treeBytes) {
		byte[] hashCodeBuffer = Arrays.copyOfRange(treeBytes, bytesRead, bytesRead + 20);
		String bytesToHex = bytesToHex(hashCodeBuffer);
		bytesRead += 20;
		return bytesToHex;
	}

	private CharBuffer readFileName(byte[] treeBytes)
			throws CharacterCodingException {
		int size = 0;
		for (; treeBytes[bytesRead + size] != 0; size++) {}
		byte[] fileNameArray = Arrays.copyOfRange(treeBytes, bytesRead, bytesRead + size);
		bytesRead += size + 1;
		return convertToUTF8(fileNameArray);
	}

	private CharBuffer readOctalMode(byte[] treeBytes) throws IOException {
		byte[] octalBuffer = Arrays.copyOfRange(treeBytes, bytesRead, bytesRead + 6);
		bytesRead += 6;
		CharBuffer octal = convertToUTF8(octalBuffer);
		inflaterInputStream.read();
		return octal;
	}

	private int treeSize(InflaterInputStream inflaterInputStream)
			throws IOException {
		ArrayList<Byte> bytes = new ArrayList<Byte>();
		for (int i = 0; i < 5; i++) {
			inflaterInputStream.read();
		}
		byte read = -1;
		do {
			read = (byte) inflaterInputStream.read();
			bytes.add(read);
		} while (read != 0);
		byte[] byteArray = new byte[bytes.size()-1];
		for (int i = 0; i < byteArray.length; i++) {
			byteArray[i] = bytes.get(i);
		}
		String s = new String(byteArray);
		return Integer.parseInt(s);
	}

	private CharBuffer convertToUTF8(byte[] bytes) throws CharacterCodingException {
		Charset utf8 = Charset.forName("UTF-8");
		CharsetDecoder decoder = utf8.newDecoder();
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, 0, bytes.length);
		CharBuffer decode = decoder.decode(byteBuffer);
		return decode;
	}
	
	private String bytesToHex(byte[] bytes) {
		char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	    char[] hexChars = new char[bytes.length * 2];
	    int v;
	    for ( int j = 0; j < bytes.length; j++ ) {
	        v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}

}
