package cam.surveil;

import android.app.Activity;

public class calculateDuration extends Activity {
	String date1,date2,time1,time2;
	int min1,min2,hour1,hour2,day1,day2,month1,month2,year1,year2;
	int min,hour,day,month;
	
	public calculateDuration(){
	
		if(day2 == day1){
			if(hour2 > hour1){
				//min1 > min2 || min1 < min2
				hour = (hour2-(hour1 + 1)) + ((min2 + (60 - min1)) % 60);
				min = (min2 + (60 - min1)) - 60;
			}
			if(hour2 < hour1){
				hour = (24 - hour1) + hour2;
				min = (min2 + (60 - min1)) - 60;
			}
			if(hour1 == hour2){
				min = min2 - min1;
			}
		}
		if(day2 != day1){
			if(year1 == year2){
				if(month2 != month1 && month2 > month1) 
					month = month2 - month1;
				day = day2 - day1;
				if(hour2 > hour1){
					//min1 > min2 || min1 < min2
					hour = (hour2-(hour1 + 1)) + ((min2 + (60 - min1)) % 60);
					min = (min2 + (60 - min1)) - 60;
				}
				if(hour2 < hour1){
					hour = (24 - hour1) + hour2;
					min = (min2 + (60 - min1)) - 60;
				}
				if(hour1 == hour2){
					min = min2 - min1;
				}
			 
			}
		}
	}

}
