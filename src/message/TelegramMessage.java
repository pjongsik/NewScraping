package message;

import java.net.URLEncoder;

import scrap.Scraping;

public class TelegramMessage {

	public static boolean send(String message) {
	
		boolean result = true;
        try
        {
        	final String personal_key = "bot6550822913:AAENPN8vuQI5htsZrZtDyDS3MDdstLjRZjk";
        	final String chat_id = "6187003248"; 
        	
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
