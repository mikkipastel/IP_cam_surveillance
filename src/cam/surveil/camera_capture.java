package cam.surveil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
//import android.widget.ViewFlipper;

public class camera_capture extends Activity{
	int timecap,time,temptime,timeall;
	Camera camera;
	camPreview preview;
	Context context;
	private static final String TAG = "CameraDemo";
	
	//private ViewFlipper flipper1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window win = getWindow();
		win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);		
        win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		
		setContentView(R.layout.main2);
		//flipper1 = ((ViewFlipper)this.findViewById(R.id.viewFlipper1));
        //flipper1.startFlipping();
		
		Intent i = getIntent();
		Bundle extra = i.getExtras();
		timecap = extra.getInt("timecapture");
		
		time = timecap*60*1000;
		Log.d("timecap", ""+timecap);
		Log.d("time", ""+time);
		
		temptime = 60;
		timeall = temptime*60*1000;
		
		//set capture
		MyCount counter = new MyCount(timeall,time);
		counter.start();
		camera = Camera.open();
	}
	
	public class MyCount extends CountDownTimer{
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}
		@Override
		public void onFinish() {
			Log.d("finish", "done");
			Intent finish = new Intent(camera_capture.this,pictureFrame.class);
	    	startActivity(finish);
		}
		@Override
		public void onTick(long millisUntilFinished) {
			//preview = 
			//camera = Camera.open();
			Parameters camParams = camera.getParameters(); //
			camParams.setPictureFormat(PixelFormat.JPEG);
			camera.takePicture(shutterCallback, rawCallback, jpegCallback);
			Log.d("capture", ""+ millisUntilFinished/(1000*60));
		}
	}
	
	// Called when shutter is opened
	ShutterCallback shutterCallback = new ShutterCallback() { // <6>
		public void onShutter() {
			Log.d(TAG, "onShutter'd");
		}
	};

	// Handles data for raw picture
	PictureCallback rawCallback = new PictureCallback() { // <7>
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d(TAG, "onPictureTaken - raw");
		}
	};

	// Handles data for jpeg picture
	PictureCallback jpegCallback = new PictureCallback() { // <8>
		public void onPictureTaken(byte[] data, Camera camera) {
			FileOutputStream outStream = null;
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			try {
				// Write to SD Card
				/*outStream = new FileOutputStream(String.format("sdcard/FORTH2_IPCAM/IP_camera_IMAGE/CAPTURE_%d.jpg",
						System.currentTimeMillis())); // <9>*/
				outStream = new FileOutputStream(String.format("sdcard/FORTH2_IPCAM/IP_camera_IMAGE/CAPTURE_%s.jpg",
						timeStamp));
				outStream.write(data);
				outStream.close();
				//camera.release();
				//camera = null;
				Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);
			} catch (FileNotFoundException e) { // <10>
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			}
			Log.d(TAG, "onPictureTaken - jpeg");
		}
	};

	
	public void createNotification(View view) {
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.btn_notification_ic_example,
				"A new notification", System.currentTimeMillis());
		// Hide the notification after its selected
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		Intent intent = new Intent(this, NotificationReceiver.class);
		PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 0);
		notification.setLatestEventInfo(this, "AndroidCamm","Look at me", activity);
		notificationManager.notify(0, notification);

	}

}
