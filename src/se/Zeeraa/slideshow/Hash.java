package se.Zeeraa.slideshow;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class Hash {
	public static String dirMD5(String dir) {
		String md5 = "";
		File folder = new File(dir);
		File[] files = folder.listFiles();

		for (int i = 0; i < files.length; i++) {
			md5 = md5 + getMd5OfFile(files[i].toString());
		}
		md5 = GetMD5HashOfString(md5);
		return md5;
	}

	public static String getMd5OfFile(String filePath) {
		String returnVal = "";
		try {
			InputStream input = new FileInputStream(filePath);
			byte[] buffer = new byte[1024];
			MessageDigest md5Hash = MessageDigest.getInstance("MD5");
			int numRead = 0;
			while (numRead != -1) {
				numRead = input.read(buffer);
				if (numRead > 0) {
					md5Hash.update(buffer, 0, numRead);
				}
			}
			input.close();

			byte[] md5Bytes = md5Hash.digest();
			for (int i = 0; i < md5Bytes.length; i++) {
				returnVal += Integer.toString((md5Bytes[i] & 0xff) + 0x100, 16).substring(1);
			}
		} catch (Throwable t) {
			// t.printStackTrace();
		}
		return returnVal.toUpperCase();
	}

	public static String GetMD5HashOfString(String str) {
		MessageDigest md5;
		StringBuffer hexString = new StringBuffer();
		try {
			md5 = MessageDigest.getInstance("md5");
			md5.reset();
			md5.update(str.getBytes());
			byte messageDigest[] = md5.digest();
			for (int i = 0; i < messageDigest.length; i++) {
				hexString.append(Integer.toHexString((0xF0 & messageDigest[i]) >> 4));
				hexString.append(Integer.toHexString(0x0F & messageDigest[i]));
			}
		} catch (Throwable t) {
		}
		return hexString.toString();
	}
}
