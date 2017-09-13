package cam.surveil;

//import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
//import android.preference.CheckBoxPreference;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class preferences extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	SharedPreferences preferences;
	
	public static final String KEY_LIST_PREFERENCE = "list_preference";
    public static final String KEY_CHECKBOX_PREFERENCE = "checkbox_preference";
    
    static final int DIALOG_START_CAPTURE = 0;
    static final int DIALOG_NOTSTART_CAPTURE = 1;
    
    int timecap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		addPreferencesFromResource(R.xml.preferences);

		 PreferenceManager.setDefaultValues(preferences.this, R.xml.preferences, false);

         for(int i=0;i<getPreferenceScreen().getPreferenceCount();i++){
        	 initSummary(getPreferenceScreen().getPreference(i));
         }
	}	

    private void initSummary(Preference p) {
		// TODO Auto-generated method stub
    	if (p instanceof PreferenceCategory){
            PreferenceCategory pCat = (PreferenceCategory)p;
            for(int i=0;i<pCat.getPreferenceCount();i++){
                initSummary(pCat.getPreference(i));
            }
        }else{
            updatePrefSummary(p);
        }

	}

	private void updatePrefSummary(Preference p) {
		// TODO Auto-generated method stub
		if (p instanceof ListPreference) {
            ListPreference listPref = (ListPreference) p; 
            p.setSummary(listPref.getEntry()); 
        }
	}

    @Override 
    protected void onResume(){
        super.onResume();
        // Set up a listener whenever a key changes             
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
	
	@Override
    protected void onPause() {
        super.onPause();

        // Unregister the listener whenever a key changes            
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);    
    }

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated method stub
		
		Preference pref = findPreference(key);
		
	    if (pref instanceof CheckBoxPreference){
	    	CheckBoxPreference capture = (CheckBoxPreference) pref;
	    	boolean image,video;
	    	
	    	if(capture.getKey() == "capture image") {
	    		
	    		image = capture.isChecked();
	    		image = capture.getEditor().putBoolean("image", image) != null;
	    		Log.d("image", ""+image);
	    		if(image == true){
	    			pref.setSummary(KEY_CHECKBOX_PREFERENCE);
	    			Log.d("image", ""+image);
	    		}
	    		
	    		//capture.setChecked(c.getInt(c.getColumnIndexOrThrow(People.Phones.ISPRIMARY)) != 0);
	    	}
	    	if(capture.getKey() == "recode video"){
	    		
	    		video = capture.isChecked();
	    		Log.d("video", ""+video);
	    		
	    	}
	    	//bind value to list or setting
	    	// .isChecked() == false
	    	//pref.setEnabled(capture.isChecked());
	    	//Log.d("capture", ""+capture.isChecked());
	    	//((CheckBoxPreference) pref).setSummaryOn(timecap);
	    	//Log.d("capture", ""+capture.getSummaryOn());
	    }

	    if (pref instanceof ListPreference) {
	        ListPreference listPref = (ListPreference) pref;
	        pref.setSummary(listPref.getEntry());
	        //Log.d("timecap", "" + pref); //Capture image every 10 min
	        //Log.d("timecap", ""+listPref); //Capture image every 10 min
	        Log.d("timecap", ""+listPref.getEntry()); //10 min
	        Log.d("timecap", ""+listPref.getValue()); //10
	        
	        timecap =Integer.parseInt(listPref.getValue());
	        
	        Intent goCapture = new Intent(preferences.this,camera_capture.class);
	        goCapture.putExtra("timecapture", timecap);
	        startActivity(goCapture);
	        
	    }
	    
	    if (pref instanceof TimePreference){
	    	String begintimeinput =null;
	    	String endtimeinput =null;
	    	int beginhour,beginmin,endhour,endmin;
	    	
	    	TimePreference time = (TimePreference) pref;
			//pref.setSummary(TimePreference.getHour(time));
			//pref.setSummary(TimePreference.getMinute(time));
			//pref.setSummary(time.getTitle());
			if(time.getKey() == "begintime"){
				begintimeinput = (String) pref.getSummary();
				
				pref.setSummary(begintimeinput);
				Log.d("Time", ""+begintimeinput);
			}
			if(time.getKey() == "endtime"){
				endhour = TimePreference.getHour(endtimeinput);
				endmin =  TimePreference.getMinute(endtimeinput);
				Log.d("endtime", ""+endtimeinput);
				Log.d("endhour", ""+endhour);
				Log.d("endmin", ""+endmin);
			}
			
			//Log.d("hourBegin", ""+TimePreference.getHour(time));
			//Log.d("minBegin", ""+TimePreference.getMinute(time));
			//Log.d("Time", ""+time.getKey());
			
	    }
	    
	    if (pref instanceof DatePreference){
	    	TimePreference date = (TimePreference) pref;
	    	if(date.getKey() == "begindate"){
	    		
	    	}
	    	if(date.getKey() == "enddate"){
	    		
	    	}
	    }

	}

}
