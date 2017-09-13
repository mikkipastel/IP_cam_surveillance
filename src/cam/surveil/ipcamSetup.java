package cam.surveil;

import android.app.Activity;
//import android.app.backup.RestoreObserver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.RadioButton;
//import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class ipcamSetup extends Activity {
    /** Called when the activity is first created. */
	RadioButton image,video;
	Button camera,view,ipport;
	boolean image_var,video_var;
	String getBegin_date;
	
	SharedPreferences preferences;
	
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.mainsetup);
        
        image = (RadioButton)findViewById(R.id.image);
        video = (RadioButton)findViewById(R.id.video);
        camera = (Button)findViewById(R.id.camera);
        view = (Button)findViewById(R.id.view);
        ipport = (Button)findViewById(R.id.showipport);
        
        /*String username = preferences.getString("username", "n/a");
		String password = preferences.getString("password", "n/a");*/
        
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        	
        //choose media mode to capture or record 
        image.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				image_var = true;
				video_var = false;
				//Toast.makeText(IP_cam_surveillanceActivity.this, image.getText(), Toast.LENGTH_SHORT).show();
			}	
        });
        
        video.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				video_var = true;
				image_var = false;
				//Toast.makeText(IP_cam_surveillanceActivity.this, video.getText(), Toast.LENGTH_SHORT).show();
			}	
        });
        
        //button option work in program
        camera.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(image_var == true){
					Intent goCamera_image = new Intent(getApplicationContext(),image_capture.class);
					startActivity(goCamera_image);
				}
				if(video_var == true){
					Intent goCamera_video = new Intent(getApplicationContext(),video_record.class);
					startActivity(goCamera_video);
					/*Intent goCamera_video = new Intent(getApplicationContext(),streamvideo.class);
					startActivity(goCamera_video);*/
				}
			}
        	
        });
        
        view.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(image_var == true){
					Intent goPreview = new Intent(getApplicationContext(),preview.class);
					startActivity(goPreview);
				}
				if(video_var == true){
					Intent goVideoPreview = new Intent(getApplicationContext(),previewVideo.class);
					startActivity(goVideoPreview);
				}
			}
        	
        });
        
        ipport.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent goIPport = new Intent(getApplicationContext(),showIPport.class);
				startActivity(goIPport);
			}
        	
        });
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.preferences:
			Intent i = new Intent(ipcamSetup.this, preferences.class);
			startActivity(i);
			break;
		}
		return true;
	}

	
}