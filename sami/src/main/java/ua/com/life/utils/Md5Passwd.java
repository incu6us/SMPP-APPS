package ua.com.life.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Passwd {
	public static String createMd5(String password) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(password.trim().getBytes(), 0, password.length());
		byte[] bytes = digest.digest();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		String generatedPassword = sb.toString();
		return generatedPassword;
	}
}