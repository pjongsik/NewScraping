package scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Scheduler {

	
	public static String getNowHHmm() throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
		
		String dateString = sdf.format(new Date());
		
		System.out.println(" HHmm : " + dateString);
		
		return dateString;
	}
	
	/**
	 * 실행시간 확인  10분 간격 확인
	 * @param time HHmm : 24시 00분 표기 예) 14시 30분 => 1430
	 * @return
	 * @throws Exception 
	 */
	public static boolean isRunTime(String time) throws Exception {
		
		if (time.length() != 4)
			return false;
		
		System.out.println("time : " + time);
		
		boolean returnValue = false;
		
		int hour = Integer.parseInt(time.substring(0,2));
		int min = Integer.parseInt(time.substring(2,4));
		
		System.out.println("hour : "+ String.valueOf(hour));
		System.out.println("min : "+ String.valueOf(min));
		
		String hhmm = getNowHHmm();
		
		int hh = Integer.parseInt(hhmm.substring(0,2));
		int mm = Integer.parseInt(hhmm.substring(2,4));
		
		System.out.println("hh : "+ String.valueOf(hh));
		System.out.println("mm : "+ String.valueOf(mm));
		
		
		if (hh == hour && mm >= min && mm <= min + 10) {
			returnValue = true; 
		}
		
		return returnValue;
	}
	
}
