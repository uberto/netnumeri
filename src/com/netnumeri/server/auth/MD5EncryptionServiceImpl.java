package com.netnumeri.server.auth;

import java.security.MessageDigest;

public class MD5EncryptionServiceImpl implements OneWayEncryptionService {
	private MessageDigest md;

	public MD5EncryptionServiceImpl() {
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}


	public String hexMD5(String toEncrypt) {
		md.reset();
		md.update(toEncrypt.getBytes());
		byte messageDigest[] = md.digest();

		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < messageDigest.length; i++) {
			int b = 0xFF & messageDigest[i];

			if (b < 16) {
				hexString.append("0" + Integer.toHexString(b));
			} else {
				hexString.append(Integer.toHexString(b));
			}

//			System.err.println("hex " + b + "   " + hexString.toString());
		}
		String foo = hexString.toString();
		//System.out.println("sessionid " + toEncrypt + " md5 version is " + hexString.toString());
		return foo;
	}

}
