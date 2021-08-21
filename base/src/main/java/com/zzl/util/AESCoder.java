package com.zzl.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

public class AESCoder {

	public static final String encodeRules = "MIIBIjANBgkqhkiG";

	public static final String qrCodeEncodeRules = "jfMg^&(4BkTh!@*M";
	/**
	 * 加密
	 */
	public static String Encrypt(String sSrc, String sKey) throws Exception {
		if (sKey == null) {
			return null;
		}
		// 判断Key是否为16位
		if (sKey.length() != 16) {
			return null;
		}
		byte[] raw = sKey.getBytes("utf-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
		return new String(Base64.getEncoder().encode(encrypted));// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
	}

	/**
	 * 解密
	 */
	public static String Decrypt(String sSrc, String sKey) throws Exception {
		if(StringUtils.isBlank(sSrc)) {
			return null;
		}
		// 判断Key是否正确
		if (sKey == null) {
			return null;
		}
		// 判断Key是否为16位
		if (sKey.length() != 16) {
			return null;
		}
		byte[] raw = sKey.getBytes("utf-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] encrypted1 = Base64.getDecoder().decode(sSrc);// 先用base64解密
		byte[] original = cipher.doFinal(encrypted1);
		String originalString = new String(original, "utf-8");
		return originalString;
	}

}
