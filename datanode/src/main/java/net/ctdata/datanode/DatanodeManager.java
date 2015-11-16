package net.ctdata.datanode;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by aditi on 14/11/15.
 * See for Log4j Initialisation: http://howtodoinjava.com/2013/04/08/how-to-configure-log4j-using-properties-file/,
 * http://www.petrikainulainen.net/programming/gradle/getting-started-with-gradle-dependency-management/
 */
public class DatanodeManager {

    static Properties properties = new Properties();
    static Logger logger = Logger.getLogger(DatanodeManager.class);

    public static void main(String[] args)
    {
        try{
            InputStream input;
            input = DatanodeManager.class.getClassLoader().getResourceAsStream("././resources/log4j.properties");

            if(properties!=null)
                properties.load(input);
            else
                System.out.println("Its null");

            //PropertiesConfigurator is used to configure logger from properties file
            PropertyConfigurator.configure(properties);

            //Log in console in and log file
            logger.debug("Log4j appender configuration is successful !!");

        }catch(FileNotFoundException ex){
            System.err.println("FATAL ERROR: log4j configuration file not found!!");
        }
        catch(IOException ex){
            System.err.println("FATAL ERROR: log4j file I/O exception!!");
        }

    }
}
