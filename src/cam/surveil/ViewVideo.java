package cam.surveil;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class ViewVideo extends Activity {
      private String filename;
      @Override
      public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            Window win = getWindow();
    		win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);		
            win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
    				WindowManager.LayoutParams.FLAG_FULLSCREEN); 
            
            System.gc();
            Intent i = getIntent();
            Bundle extras = i.getExtras();
            filename = extras.getString("videofilename");
            VideoView vv = new VideoView(getApplicationContext());
            setContentView(vv);
            vv.setVideoPath(filename);
            vv.setMediaController(new MediaController(this));
            vv.requestFocus();
            vv.start();
            
      }
}
