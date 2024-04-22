
package process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Log.Log;
import scrap.Scraping;
import scrap.model.News;



import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*
 * 경제
https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=101#&date=%2000:00:00&page=2

금융
https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid2=259&sid1=101&date=20220223&page=1

증권
https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid2=258&sid1=101&date=20220223&page=1

산업/재계
https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid2=261&sid1=101&date=20220223&page=2

중기/벤처
https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid2=771&sid1=101&date=20220223&page=2

부동산
https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid2=260&sid1=101&date=20220223&page=2

글로벌 경제
https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid2=262&sid1=101&date=20220223&page=2

생활경제
https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid2=310&sid1=101&date=20220223&page=2

경제 일반
https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid2=263&sid1=101&date=20220223&page=2

IT / 과학
https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=105#&date=%2000:00:00&page=2

 */

public class NaverNew {
	
	public static List<News> Scraping(String mode, String mainId, String sub1Id, String sub2Id, String date, int pageCount) throws Exception {
		
		List<News> news = new ArrayList<News>();

        int page = 1;
        
        		
        // page start
        String start_filter = "<div id=\"main_content\">";
   
        // next
        String filter1 = "</dt>";
        String filter1_img = "<dt class=\"photo\">";	// 이미지 있는경우/없는경우도 있음
        
        // url
        String filter2 = "<a href=\"";
        String filter2_1 = "\"";

        // title
        String filter3 = "\">";
        String filter3_1 = "</a>";

        // from
        String filter5 = "class=\"writing\">";
        String filter5_1 = "</span>";

        // time
        String filter6 = "class=\"date is_new\">";
        String filter66 = "class=\"date is_outdated\">";
        
        String filter6_1 = "</span>";
		
        //
        String imageText = "";
        	
        while (page <= pageCount)
        {
            System.out.println("page : " + page);
            Log.write("page : " + page);

            // 소붐
            String url = String.format("https://news.naver.com/main/list.naver?mode=%s&mid=%s&sid2=%s&sid1=%s&date=%s&page=%d",mode, mainId, sub2Id, sub1Id, date, page);
            
            // 대분류
            //url = String.format("https://news.naver.com/main/main.naver?mode=%s&mid=%s&sid1=%s#&date=%2000:00:00&page=%d", page);
            
            Scraping.setCharset("EUC-KR");
            String text = Scraping.Scrap(url);
            text = text.substring(text.indexOf(start_filter) + start_filter.length());

            while (text.indexOf(filter1) > -1)
            {
            	
            	// 이미지 유무 확인용
            	imageText = text.substring(0, text.indexOf(filter1) + filter1.length());
            	if (imageText.indexOf(filter1_img) > -1) {
            		text = text.substring(text.indexOf(filter1) + filter1.length());
            	}
            	
            	text = text.substring(text.indexOf(filter2) + filter2.length());
            	
            	//
                String clickUrl = text.substring(0, text.indexOf(filter2_1));

                text = text.substring(text.indexOf(filter3) + filter3.length());
                String title = text.substring(0, text.indexOf(filter3_1));
                title = trimText(title);

                // 이미지 시작인경우 다음 dt에서 title 확인
//                if (title.startsWith("<img")) {
//                	 text = text.substring(text.indexOf(filter1) + filter1.length());
//                	 text = text.substring(text.indexOf(filter3) + filter3.length());
//                	 title = text.substring(0, text.indexOf(filter3_1));
//                	 title = trimTitle(title);
//                }
//                
              
                // 출처, 시간이 없으면 pass~
                if (text.indexOf(filter5) < 0 && text.indexOf(filter6) < 0  && text.indexOf(filter66) < 0)
                    continue;

                text = text.substring(text.indexOf(filter5) + filter5.length());

                String from = text.substring(0, text.indexOf(filter5_1));

                if (text.indexOf(filter66) > -1 ) {
                	text = text.substring(text.indexOf(filter66) + filter66.length());
                } else {
                	text = text.substring(text.indexOf(filter6) + filter6.length());
                }

                String time = text.substring(0, text.indexOf(filter6_1));
                time = trimText(time);

                news.add(new News(title, clickUrl, from, time));

                System.out.println("title : " + title);
                Log.write("title : " + title);
                System.out.println("url : " + clickUrl + " - from : " +from+ ", time : " + time);
                Log.write("url : " + clickUrl + " - from : " +from+ ", time : " + time);
            }

            page++;
        }
        
		return news;
		
	}

	private static String trimText(String title) {
		
		if (StringUtils.isEmpty(title))
			return title;
		
		title = title.trim();
		title = title.replace("&#034;", "\"");
        title = title.replace("&#039;", "'");
        title = title.replace("&lt;", "<");
        title = title.replace("&gt;", ">");
        title = title.replace("&#8729;", "∙");
        title = title.replace("&#8231;", "·");
        title = title.replace("&#8943;", "⋯");
        title = title.replace("&#160;", "...");


		return title;
	}
}
