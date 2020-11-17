package getDataConfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class GetDataConfig {
    public static String getPath(){
        return System.getProperty("user.dir") + "\\config.properties";
    }

    public static String getValue(String key) throws FileNotFoundException {
        Properties pps = new Properties();
        try{
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(getPath()));
            pps.load(inputStreamReader);
            String value = pps.getProperty(key);
            return value;
        }
        catch(IOException exception){
            exception.printStackTrace();
            return null;
        }
    }

}
