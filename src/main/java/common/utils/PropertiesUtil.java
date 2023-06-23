package common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import common.constants.Constants;

public class PropertiesUtil {
	
	/**
	 * プロパティファイルから値を取得して返却する
	 * @throws IOException 
	 */
	public static String getProperties(String param) {
		
        Properties properties = new Properties();
        
        //プロパティファイルのパスを指定する
        String propertiesPass = Constants.PROPERTIES_PATH;
        
        try {
            InputStream istream = new FileInputStream(propertiesPass);
            properties.load(istream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return properties.getProperty(param);
		
	}

}
