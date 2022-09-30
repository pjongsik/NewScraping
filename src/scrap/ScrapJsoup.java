package scrap;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class ScrapJsoup {

    private static final String URL = "https://www.mir4draco.com/price";
    
    /**
     * 크롤링
     * @return
     */
    public static String parseHTMLData() {
       
    	WebClient webClient = null;
    	
        try {
//        	webClient = new WebClient();
//        	HtmlPage myPage = webClient.getPage(URL);
//        
//        	webClient.waitForBackgroundJavaScript(2000);
//        	webClient.getOptions().setThrowExceptionOnScriptError(false);
//
//        	
//        	// Wait
//        	synchronized (myPage) {
//        	    try {
//        	    	myPage.wait(2000);
//        	    } catch (InterruptedException e) {
//        	        e.printStackTrace();
//        	    }
//        	}
        	Document doc = Jsoup.connect(URL)
					            .header("Accept-Encoding", "gzip, deflate")
					            .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
					            .maxBodySize(0)
					            .timeout(600000)
					            .get();
        	
            // timeout을 설정하지 않으면 ReadTimeoutException이 발생할 수 있다.
            //Document doc = Jsoup.connect(URL).timeout(50000).get(); 
        //	Document doc = Jsoup.parse(myPage.asXml());
          
            // class 명이 amount wemix
        //    Elements elements = doc.select(".amount wemix");
        //    for(Element element : elements) {
        //    	System.out.println( element.toString() );
        //    }
            return doc.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
            return "error : " + e.getMessage();
        } finally {
        	//webClient.close();
		}
    }

    /**
     * phantomjsdriver
     * @return
     */
    public static String parseHTMLData2() {
    	
    	 DesiredCapabilities caps = new DesiredCapabilities();
         caps.setJavascriptEnabled(true);  
         caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "F:\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");

    	WebDriver ghostDriver = new PhantomJSDriver(caps);
        try {
        	
        	ghostDriver.get(URL);
            String text = ghostDriver.getPageSource();
            
            return text;
        } finally {
            ghostDriver.quit();
        }
        
    }
    
    public static String parseHTMLData3() {
    

   	 DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);  
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "C:\\Program Files\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");

        
   // 	File file = new File("C:\\Program Files\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
   // 	System.setProperty("phantomjs.binary.path", file.getAbsolutePath());
    	
    	WebDriver driver = new PhantomJSDriver(caps);
    	//driver.get(URL);
    	driver.manage().window().maximize();
        driver.navigate().to(URL);
        
        try {
        	driver.wait(2000);
        	WebElement el = driver.findElement(By.className("wemix"));
			String text = el.getText();
			
			System.out.println(" text : " + text);
					
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
//        WebElement el =driver.findElement(By.className("amount wemix"));
//        if (el != null ) {
//	        String elText= el.getText();
//	        System.out.println("elText : " + elText);
//        }
    	String text = driver.getPageSource();
    	System.out.println(text);
    	
   // 	WebElement element = driver.findElement(By.className("wemix amount"));
   // 	String wemixAmount = element.getText();
  //  	System.out.println(" wemix amount : " + wemixAmount);
    	driver.quit();
    	
    	return "";
   }
    
    

   
}

