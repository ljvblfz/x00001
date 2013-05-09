package com.founder.sipbus.common.util;

// Util.java

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Util {
	// 把文件读入byte数组
	static public byte[] readFile(String filename) throws IOException {
		File file = new File(filename);
		//System.out.println(file.getAbsolutePath());
		long len = file.length();
		byte data[] = new byte[(int) len];
		FileInputStream fin = new FileInputStream(file);
		int r = fin.read(data);
		if (r != len)
			throw new IOException("Only read " + r + " of " + len + " for "
					+ file);
		fin.close();
		return data;
	}

	static public byte[] readFile(JarFile jarFile, String filename)
			throws Exception {
		JarEntry jarEntry = jarFile.getJarEntry(filename);
		return InputStreamToByte(jarFile.getInputStream(jarEntry));
	}

	// 把byte数组写出到文件
	static public void writeFile(String filename, byte data[])
			throws IOException {
		FileOutputStream fout = new FileOutputStream(filename);
		fout.write(data);
		fout.close();
	}

	public static byte[] getBytes(InputStream is) throws Exception {
		byte[] data = null;

		Collection chunks = new ArrayList();
		byte[] buffer = new byte[1024 * 1000];
		int read = -1;
		int size = 0;

		while ((read = is.read(buffer)) != -1) {
			if (read > 0) {
				byte[] chunk = new byte[read];
				System.arraycopy(buffer, 0, chunk, 0, read);
				chunks.add(chunk);
				size += chunk.length;
			}
		}

		if (size > 0) {
			ByteArrayOutputStream bos = null;
			try {
				bos = new ByteArrayOutputStream(size);
				for (Iterator itr = chunks.iterator(); itr.hasNext();) {
					byte[] chunk = (byte[]) itr.next();
					bos.write(chunk);
				}
				data = bos.toByteArray();
			} finally {
				if (bos != null) {
					bos.close();
				}
			}
		}
		return data;
	}

	public static byte[] InputStreamToByte(InputStream is) throws IOException {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();
		return imgdata;
	}

}
