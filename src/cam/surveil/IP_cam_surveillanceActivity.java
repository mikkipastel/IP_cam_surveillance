package cam.surveil;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;


public class IP_cam_surveillanceActivity extends Activity {
	
	  @Override   
	  public void onCreate(Bundle icicle) {   
	    super.onCreate(icicle);   
	    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	    
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window win = getWindow();
		win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);		
        win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); 
	    
	    setContentView(R.layout.main);   
	
	    //Setup Button Listener
	    Button pictureFrameButton = (Button) findViewById(R.id.pictureFrameButton);
	    pictureFrameButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				startLayer01();
			}
		});
	    Button CameraButton = (Button)findViewById(R.id.CameraButton);
	    CameraButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				startLayer02();
			}
		});
	  }
	    public void startLayer01(){
	    	Intent layer01 = new Intent(IP_cam_surveillanceActivity.this,pictureFrame.class);
	    	startActivity(layer01);
	    }
	    public void startLayer02(){
	        Intent layer02 = new Intent(IP_cam_surveillanceActivity.this,ipcamSetup.class);
	    	startActivity(layer02);
	    }
	  }   
