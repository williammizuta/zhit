package br.com.caelum.zhit.infra;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Arrays;
import java.util.zip.InflaterInputStream;

public class GitTreeInflater implements GitObjectInflater {

	@Override
	public String inflate(File file) {
		try(InflaterInputStream inflaterInputStream = new InflaterInputStream(new FileInputStream(file))) {
			Integer read = inflaterInputStream.read();
			while (read != 0) {
				read = inflaterInputStream.read();
			}

			StringBuilder content = new StringBuilder();
			byte[] buffer = new byte[6];
			byte[] nameBuffer = new byte[32];

			while (inflaterInputStream.available() != 0) {
				inflaterInputStream.read(buffer);
				CharBuffer octalMode = convertToAscii(buffer, 6);
				content.append(octalMode);
				content.append(" ");

				inflaterInputStream.read();
				int bytesRead;
				do {
					 bytesRead = inflaterInputStream.read(nameBuffer);
					 System.out.println(bytesRead);
					 System.out.println(Arrays.toString(nameBuffer));
					 CharBuffer toAscii = convertToAscii(nameBuffer, bytesRead);
					 System.out.println(toAscii);
					content.append(toAscii);
				} while (bytesRead != 32);
			}
			return content.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private CharBuffer convertToAscii(byte[] bytes, int length) throws CharacterCodingException {
		Charset ascii = Charset.forName("UTF-8");
		CharsetDecoder decoder = ascii.newDecoder();
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, 0, length);
		CharBuffer decode = decoder.decode(byteBuffer);
		return decode;
	}

}
