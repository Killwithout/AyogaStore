package com.cjk.common.encode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * md5加密
 * @author cjk
 *
 */
public class Md5PwdImpl implements Md5Pwd{
	//加密
	public String encode(String password){
		String algorithm = "MD5";
		//加盐  就是将用户输入进来的密码加点东西进去后再加密  
		//password = "xinbaba123456";
		MessageDigest instance = null;
		try {
		    instance = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//加密
		byte[] digest = instance.digest(password.getBytes());
		//十六进制加密
		char[] encodeHex = Hex.encodeHex(digest);
		return new String(encodeHex);
	}
}
