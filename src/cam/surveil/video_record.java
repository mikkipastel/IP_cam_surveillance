package cam.surveil;

//import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
//import android.hardware.Camera;
//import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
//import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceView;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.VideoView;

public class video_record extends Activity{

	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	private Uri fileUri;
	SurfaceView mSurfaceView,mSurfaceHolder;
	//private VideoView mVideoView;
	//private Uri mVideoUri;
	
	private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
	//private static final int ACTION_TAKE_VIDEO = 3;
	
	//private static final String MP4_FILE_PREFIX = "VIDEO_";
	//private static final String MP4_FILE_SUFFIX = ".mp4";
	String mCurrentVideoPath;
	
	private static final String TAG = "VideoDemo";
	//private static final String MEDIA_TYPE_VIDEO = "sdcard/FORTH2_IPCAM/IP_camera_VIDEO/";

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.camera_video);

	    //create new Intent
	    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

	    //fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);  // create a file to save the video
	    createVideoFile();
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // set the image file name

	    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high

	    // start the Video Capture Intent
	    startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
	    //handleCameraVideo(intent); //
	    
	    mSurfaceView = (SurfaceView)findViewById(R.id.surfaceView2);
	    
	    mAlbumStorageDirFactory = new BaseAlbumDirFactory();  
	    
	    finish();
	    setContentView(R.layout.main);
	    
	}

	public static boolean isIntentAvailable(Context context, String action) {
	    final PackageManager packageManager = context.getPackageManager();
	    final Intent intent = new Intent(action);
	    List<ResolveInfo> list =
	        packageManager.queryIntentActivities(intent,
	            PackageManager.MATCH_DEFAULT_ONLY);
	    
	    return list.size() > 0;
	}
	
	//	
	public static FileOutputStream createVideoFile(){
		// Create an video file name
		FileOutputStream outStream = null;
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		try {
			// Write to SD Card
			outStream = new FileOutputStream(String.format("sdcard/FORTH2_IPCAM/IP_camera_VIDEO/VIDEO_%s.mp4",
					timeStamp));
			outStream.write(MediaStore.EXTRA_OUTPUT.getBytes());
			outStream.close();
			Log.d(TAG, "onVideoTaken - wrote bytes: " + MediaStore.EXTRA_OUTPUT.getBytes());
		} catch (FileNotFoundException e) { 
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		Log.d(TAG, "onVideoTaken - mp4");
		return outStream;
	}
	
	/* Photo album for this application */
	/*private String getAlbumName() {
		return getString(R.string.album_name_video); //IP_camera_video
	}

	private File getAlbumDir() {
		File storageDir = null;

		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			
			storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

			if (storageDir != null) {
				if (! storageDir.mkdirs()) {
					if (! storageDir.exists()){
						Log.d("IP_CAMERA", "failed to create directory");
						return null;
					}
				}
			}
			
		} else {
			Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
		}
		
		return storageDir;
	}
	
	private File createVideoFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String videoFileName = MP4_FILE_PREFIX + timeStamp + "_";
		File albumF = getAlbumDir();
		File videoF = File.createTempFile(videoFileName, MP4_FILE_SUFFIX, albumF);
		return videoF;
	}

	private File setUpVideoFile() throws IOException {
		
		File f = createVideoFile();
		mCurrentVideoPath = f.getAbsolutePath();
		
		return f;
	}

	private void galleryAddVideo() {
	    Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		File f = new File(mCurrentVideoPath);
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    this.sendBroadcast(mediaScanIntent);
	}
	
	private void handleCameraVideo(Intent intent) {
	    mVideoUri = intent.getData();
	    mVideoView.setVideoURI(mVideoUri); //
	}
	
	private void dispatchTakeVideoIntent() {
		Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		
		File f = null;
		try {
			f = setUpVideoFile();
			MediaRecorder recorder = new MediaRecorder();
		    recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
		    recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		    recorder.setVideoEncoder(MediaRecorder.OutputFormat.MPEG_4);
			takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			f = null;
		}
		
		startActivityForResult(takeVideoIntent, ACTION_TAKE_VIDEO);
	}*/
	
	//stream
	 
}
