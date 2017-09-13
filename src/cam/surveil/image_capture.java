package cam.surveil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class image_capture extends Activity{

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;
	private Uri fileUri;
	SurfaceView mSurfaceView,mSurfaceHolder;
	Bitmap mImageBitmap;
	ImageView mImageView; 
	File storageDir;
    String mCurrentPhotoPath;
    
	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";
    
	private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window win = getWindow();
		win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);		
        win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    
	    //setContentView(R.layout.camera_image);
        setContentView(R.layout.camera_capture);
	    
	    mSurfaceView = (SurfaceView)findViewById(R.id.surfaceView1);

	    // create Intent to take a picture and return control to the calling application
	    Intent imagecap = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    
	    //fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
	    imagecap.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
	    
	    // start the image capture Intent
	    startActivityForResult(imagecap, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	    
	    mAlbumStorageDirFactory = new BaseAlbumDirFactory();
	    dispatchTakePictureIntent();
	    setPic();
		galleryAddPic();
	    
	    finish();
	    setContentView(R.layout.mainsetup); 
	}

	/* Photo album for this application */
	private String getAlbumName() {
		return getString(R.string.album_name); //IP_camera
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
	
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
		File albumF = getAlbumDir();
		File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
		return imageF;
	}

	private File setUpPhotoFile() throws IOException {
		
		File f = createImageFile();
		mCurrentPhotoPath = f.getAbsolutePath();
		
		return f;
	}
	
	private void setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = mImageView.getWidth();
		int targetH = mImageView.getHeight();

		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;
		
		/* Figure out which way needs to be reduced less */
		int scaleFactor = 1;
		if ((targetW > 0) || (targetH > 0)) {
			scaleFactor = Math.min(photoW/targetW, photoH/targetH);	
		}

		/* Set bitmap options to scale the image decode target */
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
		Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		
		/* Associate the Bitmap to the ImageView */
		mImageView.setImageBitmap(bitmap);
		mImageView.setVisibility(View.VISIBLE);
	}
	
	private void galleryAddPic() {
	    Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		File f = new File(mCurrentPhotoPath);
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    this.sendBroadcast(mediaScanIntent);
	}
	
	private void dispatchTakePictureIntent() {

		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			File f = null;
			
			try {
				f = setUpPhotoFile();
				mCurrentPhotoPath = f.getAbsolutePath();
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
			} catch (IOException e) {
				e.printStackTrace();
				f = null;
				mCurrentPhotoPath = null;
			}

		startActivityForResult(takePictureIntent, 0);
	}
}
