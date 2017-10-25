package ifrs.gp.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5 {
	
	public static String convertToMD5(String value){
		try{
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(value.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			String hashtext = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while(hashtext.length() < 32 ){
			  hashtext = "0"+hashtext;
			}
			return hashtext;
		}
		catch (Exception e) {
			e.printStackTrace();
			return value;
		}
	}

}
