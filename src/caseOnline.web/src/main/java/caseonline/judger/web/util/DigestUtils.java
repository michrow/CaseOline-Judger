package caseonline.judger.web.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class DigestUtils {
	private DigestUtils(){}
	
	/**
	 * MD5 加密
	 * @param password
	 * @return
	 */
	public static String md5Hex(String password){
		String md5 = "";
		if ( password == null || password.isEmpty() ) {
			return "";
		}
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			 byte byteData[] = md.digest();
	 
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			md5 = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5;
	}
	
	/**
	 * 生成GUID.
	 * @return GUID
	 */
	public static String getGuid() {
		return UUID.randomUUID().toString();
	}
}
