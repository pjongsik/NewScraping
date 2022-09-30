package Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	public static void write(String logText) {
	
		try{
        	String logFilePath = System.getProperty("user.dir");
        	//System.out.println("----logFilePath : "  + logFilePath);
        	
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	String dateString = sdf.format(new Date());
        	
        	logFilePath += "//log";
        	
        	File folder = new File(logFilePath);
        	File file = new File(logFilePath + "//log_" + dateString + ".txt");
        	
        	
        	//System.out.println("----logFilePath : "  + file.toPath().toString());
        	
        	if (folder.exists() == false) {
        		folder.mkdir();
        	}
        	
        	if (file.exists()==false) {
        		file.createNewFile();
        	}
        	
        	SimpleDateFormat logSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        	logText = String.format("[%s] %s\r\n", logSdf.format(new Date()), logText);
        	
        	//System.out.println("----logText : "  + logText);
        	
        	Files.write(file.toPath(), logText.getBytes(), StandardOpenOption.APPEND);

		} catch(IOException ex) {
            ex.printStackTrace();
		}
    }
}
