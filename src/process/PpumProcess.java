package process;

import java.util.ArrayList;
import java.util.List;

import scrap.Scraping;
import scrap.model.Ppum;

public class PpumProcess {
	
	 static List<Ppum> _list = new ArrayList<Ppum>();

	 public static List<Ppum> pageScraping(int endPage, boolean isOversee, boolean searchAfterLastTime)
     {
         // 국내
         String url = "https://www.ppomppu.co.kr/zboard/zboard.php?id=ppomppu";

         // 해외
         if (isOversee)
             url = "https://www.ppomppu.co.kr/zboard/zboard.php?id=ppomppu4";

         int page = 1;
        
         _list = null;
         _list = new ArrayList<Ppum>();

         while (page <= endPage)
         {
             if (page > 1)
                 url = String.format("%s&page=%s", url, page);

             // 한글셋
             Scraping.setCharset("EUC-KR");
             String text = Scraping.Scrap(url);
             //System.out.println(text);
             _list.addAll(getPpumData(text));

             page++;
         }

         // 마지막 조회시간확인
         //String lasttime = GetLastSearchTime(isOversee);
         //SetLastSearchTime(isOversee);

        
        // if (string.IsNullOrEmpty(lasttime) == false && searchAfterLastTime)
        //     _list = _list.Where(x => x.시간.CompareTo(lasttime) > 0).ToList();

         return _list;
     }
	
	public static List<Ppum> getPpumData(String text) {

         //System.out.println(text);

		  final String hot_icon2 = "/images/menu/hot_icon2.jpg";    // hot
          final String pop_icon2 = "/images/menu/pop_icon2.jpg";    // 인기
          final String end_icon = "/DQ_Revolution_BBS_New1/end_icon.PNG";   // 종결 
          final String evnet_href = "href=\"/zboard/view.php"; // 이벤트 뷰페이지 바로가기

          // 게시글 블록
          final String startText = "<div class=\"baseList-box\">";
          final String endText = "<td class='baseList-space baseList-views' colspan=2>";

          // 쇼핑몰
          final String startFaceText = "<em class=\"baseList-head subject_preface\">";
          final String endFaceText = "</em>";
          
          // 등록시간
          final String startTime = "<td class='baseList-space' colspan=2  title=\"";
          final String endTime = "\" ><time class=";


          // 상세페이지 url
          final String startUrl = "' href=";
          final String endUrl = "\"  ><span>";
          
          //
          String temp = text;
          String urlTemp = "";

          String bigo = "";

          boolean hot = false;
          boolean pop = false;
          boolean closed = false;

          int count = 0;
          List<Ppum> titleList = new ArrayList<Ppum>();

          while (temp.indexOf(startText) > -1 && temp.indexOf(endText) > -1)
          {

              hot = pop = closed = false;
              bigo = "";

              int startTextIndex = temp.indexOf(startText);
              int endTextIndex =  temp.indexOf(endText);

              String textBlock = temp.substring(startTextIndex, endTextIndex);

              if (textBlock.indexOf(evnet_href) > -1) {
                  // 코드블럭에 이벤트 페이지 바로가기가 있는경우 리스트 종료
                  break;
              }
              
              //System.out.println("textBlock : " + textBlock);

              temp = temp.substring(endTextIndex + endText.length());


              if (textBlock.indexOf(end_icon) > 0)
                  closed = true;

              // 인기. 핫 확인
              if (textBlock.indexOf(hot_icon2) > 0)
                  hot = true;
              else if (textBlock.indexOf(pop_icon2) > 0)
                  pop = true;

              // url
              if (textBlock.indexOf(startUrl) > -1) {
                  urlTemp = textBlock.substring(textBlock.indexOf(startUrl));
              } else {
                  continue;
              }

              // 바로가기 URL만 남김
              urlTemp = urlTemp.substring(startUrl.length() + 1);
              urlTemp = urlTemp.substring(0, urlTemp.indexOf(endUrl));

              // 비고
              bigo = String.format("%s%s", closed ? "<종결>" : "", hot ? "[핫]" : pop ? "[인기]" : "");

              
              // 제목
              String title;
              if (textBlock.indexOf(startFaceText) > -1) {
                  title = textBlock.substring(textBlock.indexOf(startFaceText), textBlock.indexOf("</span>"));
                  title = title.replaceAll(startFaceText, "");
                  title = title.replaceAll(endFaceText, "");
              } else {

                  if (textBlock.indexOf("<span>") > -1) {
                      title = textBlock.substring(textBlock.indexOf("<span>"), textBlock.indexOf("</span>"));
                      title = title.replaceAll("<span>", "");
                  } else {
                      // 종결 --> 해당글은 게시중단요청에 의해 블라인드 처리된 글입니다.
                      continue;
                  }
              }

              String time = textBlock.substring(textBlock.indexOf(startTime) + startTime.length(), textBlock.indexOf(endTime));

              titleList.add(new Ppum(bigo
                                          , title //temp.substring(0, temp.indexOf(endText))
                                          , time
                                          , String.format("%s%s", "https://www.ppomppu.co.kr/zboard/", urlTemp)
                                          , hot, pop, closed));

//              System.out.println("===== start ==============================================");
//              System.out.println("bigo : " + bigo);
//              System.out.println("title : " + title);
//              System.out.println("url : " + String.format("%s%s", "https://www.ppomppu.co.kr/zboard/", urlTemp));
//              System.out.println("time : " + time);
//              System.out.println("hot : " + hot);
//              System.out.println("pop : " + pop);
//              System.out.println("closed : " + closed);
//
//              System.out.println("===== end   ==============================================");
          }

          return titleList;
		
	}
}
