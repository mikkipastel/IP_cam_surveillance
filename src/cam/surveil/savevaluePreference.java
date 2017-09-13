package cam.surveil;

import android.app.Activity;
import android.content.Context;
//import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

public class savevaluePreference extends Activity{


	public static final String PREFS_begindate = "PREFS_begindate";
    public static final String PREFS_begintime = "PREFS_begintime";
    public static final String PREFS_enddate = "PREFS_enddate";
    public static final String PREFS_endtime ="PREFS_endtime";
    
    public static final String KEY_begindate = "KEY_begindate";
    public static final String KEY_begintime = "KEY_begintime";
    public static final String KEY_enddate = "KEY_enddate";
    public static final String KEY_endtime = "KEY_endtime";
    
    private SharedPreferences prefsbegindate;          
    private SharedPreferences prefsbegintime;       
    private SharedPreferences prefsenddate;      
    private SharedPreferences prefsendtime;  
    @Override
    public void onCreate(Bundle icicle) {
    
    	 boolean valid = savevaluePreference.this.validate();
         if (valid) {
             prefsbegindate = getSharedPreferences(savevaluePreference.PREFS_begindate,Context.MODE_WORLD_READABLE+ Context.MODE_WORLD_WRITEABLE) ;                             
             prefsbegintime = getSharedPreferences(savevaluePreference.PREFS_begintime,Context.MODE_WORLD_READABLE+ Context.MODE_WORLD_WRITEABLE) ;                   
             prefsenddate = getSharedPreferences(savevaluePreference.PREFS_enddate,Context.MODE_WORLD_READABLE+ Context.MODE_WORLD_WRITEABLE) ;                  
             prefsendtime = getSharedPreferences(savevaluePreference.PREFS_endtime, Context.MODE_WORLD_READABLE+ Context.MODE_WORLD_WRITEABLE) ;
             
             Editor prefsbegindate = savevaluePreference.this.prefsbegindate.edit();
             Editor prefsbegintime = savevaluePreference.this.prefsbegintime.edit();
             Editor prefsenddate = savevaluePreference.this.prefsenddate.edit();
             Editor prefsendtime = savevaluePreference.this.prefsendtime.edit();
             
             /*prefsbegindate.putString(savevaluePreference.KEY_begindate,
            		  		savevaluePreference.this.inputPrivate.getText().toString()); 
             prefsbegintime.putString(savevaluePreference.KEY_begintime,
            				 savevaluePreference.this.inputWorldRead.getText().toString());
             prefsenddate.putString(savevaluePreference.KEY_enddate,
            				 savevaluePreference.this.inputWorldWrite.getText().toString());
             prefsendtime.putString(savevaluePreference.KEY_endtime,
            				 savevaluePreference.this.inputWorldReadWrite.getText().toString());*/
            		 
             prefsbegindate.commit(); 
             prefsbegintime.commit();
             prefsenddate.commit();
             prefsendtime.commit();
            
            /*Intent intent = new Intent(savevaluePreference.this, SharedPrefTestOutput.class);
            SharedPrefTestInput.this.startActivity(intent);*/
         }
    }
	private boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

}
