package common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import common.constants.Constants;

public class PropertiesUtil {
	
	/**
	 * Propertiesファイルを扱う
	 * @return 
	 * @throws UnsupportedEncodingException 
	 */
	public static String getProperties(String param) {
		
        Properties properties = new Properties();
        
        //プロパティファイルのパスを指定する
        String propertiesPass = Constants.PROPERTIES_PASS;
        
        try {
            InputStream istream = new FileInputStream(propertiesPass);
            properties.load(istream);
            System.out.println("param" + properties.getProperty(param));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return properties.getProperty(param);
		
	}

}
