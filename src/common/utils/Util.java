package common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Util {
	
	/**
	 * URLエンコードメソッド
	 * @return 
	 * @throws UnsupportedEncodingException 
	 */
	public static String urlEncode(String urlParam, String characterCode) {
		try {
			return URLEncoder.encode(urlParam, characterCode);
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		return urlParam;
	}	

}
