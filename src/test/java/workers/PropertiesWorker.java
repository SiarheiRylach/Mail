package workers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Sergey on 8/12/2017.
 */
public class PropertiesWorker {
    private  Properties properties;


    public PropertiesWorker() {
    }

    public void createProperties(String pathProperties) throws IOException {
        final FileInputStream fis = new FileInputStream(pathProperties);
        properties = new Properties();
        properties.load(fis);
        fis.close();
    }

    public String getProperty(String nameProperty){
       return  properties.getProperty(nameProperty);
    }

}
