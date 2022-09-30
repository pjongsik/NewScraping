package process;

import java.util.Arrays;
import java.util.List;

import Log.Log;
import message.TelegramMessage;
import scrap.Scraping;

public class WebChecker {
	
	public static void allWebProcess() {
		
		 // 1. api
        String apiUrl = "http://api.iquest.co.kr/link/link_data_provider.do?mode=x2&actno=%s&cbit=%s&serial=%s";
        apiUrl = String.format(apiUrl, "456-34-23652", "41", "7FDRQ4-1C2FCN-GF1TKW");
        boolean result = checkWebPage(apiUrl, Arrays.asList( "<ptratsq>", "456-34-23652"), null);
        Log.write(String.format("1. API Check : %s", result));

        // 2. 홈피
        result = checkWebPage("https://www.iquest.co.kr", Arrays.asList( "아이퀘스트", "얼마에요" ), null);
        Log.write(String.format("2. 홈페이지 iquest.co.kr : %s", result));

        // 3. 관리자
        result = checkWebPage("http://manager.iquest.co.kr", Arrays.asList( "매직빌 관리자 페이지", "아이디" ), null);
        Log.write(String.format("3. 관리자 manager.iquest.co.kr : %s", result));

        // 4. 우리 
        result = checkWebPage("http://woori.iquest.co.kr", Arrays.asList( "/main.do" ), null);
        Log.write(String.format("4-1. 우리 woori.iquest.co.kr : %s", result));

        result = checkWebPage("http://woori.iquest.co.kr/main.do", Arrays.asList( "우리자금관리서비스", "얼마에요 우리ERP" ), null);
        Log.write(String.format("4-2. 우리 woori.iquest.co.kr/main.do : %s", result));

        // 5. 부산 
        //result = checkWebPage("http://bicerp.iquest.co.kr", Arrays.asList( "/main.do" ), null);
        //Log.write(String.format("5-1. 부산 bicerp.iquest.co.kr : %s", result));

        //result = checkWebPage("http://bicerp.iquest.co.kr/main.do", Arrays.asList( "BNK부산은행 BIC+ERP", "BIC+ ERP" ), null);
        //Log.write(String.format("5-2. 부산 bicerp.iquest.co.kr/main.do : %s", result));

        // 6. 신한 
        result = checkWebPage("http://insidebankerp.iquest.co.kr", Arrays.asList( "/main.do" ), null);
        Log.write(String.format("6-1. 신한 insidebankerp.iquest.co.kr : %s", result));

        result = checkWebPage("http://insidebankerp.iquest.co.kr/main.do", Arrays.asList( "신한 InsideBank ERP", "insidebankerp" ), null);
        Log.write(String.format("6-2. 신한 insidebankerp.iquest.co.kr/main.do : %s", result));

        // 7. 이지세무
        result = checkWebPage("https://www.easysemu.co.kr", Arrays.asList( "Easy세무", "로그인" ), null);
        Log.write(String.format("7. 이지세무 easysemu.co.kr : %s", result));

        // 8. 싸인빌
       // result = checkWebPage("https://econbot.iquest.co.kr/wm/main.do", Arrays.asList( "싸인빌", "전자계약서" ), null);
       // Log.write(String.format("8. 싸인빌 econbot.iquest.co.kr : %s", result));

        // 9. 얼마서버
        result = checkWebPage("https://ulma.iquest.co.kr", Arrays.asList( "얼마", "아이디" ), null);
        Log.write(String.format("9. 얼마서버 ulma.iquest.co.kr : %s", result));
	}
	
	private static boolean checkWebPage(String checkUrl, List<String> checkTextList, String errorMessage) {
		String result = Scraping.Scrap(checkUrl);
		
		boolean resultValue = true;
		for (String checkText : checkTextList) {
			if (result.contains(checkText) == false) {
				resultValue= false;
			}
		}
		
		if (resultValue == false) {
			if (errorMessage == null || "".equals(errorMessage)) {
				TelegramMessage.send(String.format("%s : %s", checkUrl, errorMessage));
			}
			else {
				TelegramMessage.send(String.format("%s : 응답 오류 발생!! 확인해주세요!!", checkUrl));
			}
		}
		
		return resultValue;
	}

}
