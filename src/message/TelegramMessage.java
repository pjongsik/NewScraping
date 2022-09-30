package message;

import java.net.URLEncoder;

import scrap.Scraping;

public class TelegramMessage {

	public static boolean send(String message) {
	
		boolean result = true;
        try
        {
        	final String personal_key = "bot999783271:AAH-RWs1aqO1q0HmUrCKpJsrYLXgYM-uJrY";
        	final String chat_id = "1124104280"; 
        	
            // 주소 인코딩
            message = URLEncoder.encode(message, "UTF-8");
            
            String url = "https://api.telegram.org/%s/sendMessage?chat_id=%s&text=%s";
            url = String.format(url, personal_key, chat_id, message);
            Scraping.Scrap(url);

        }
        catch (Exception ex)
        {
            result = false;
            System.out.println(ex.getMessage());
        }

        return result;
	}
}
