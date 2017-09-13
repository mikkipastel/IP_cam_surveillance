package cam.surveil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class preview extends Activity{
	ImageView i;
	
	@Override  
	public void onCreate(Bundle savedInstanceState) {  
	    super.onCreate(savedInstanceState);  
	    setContentView(R.layout.view);  
	    
	    ImageView i = (ImageView)findViewById(R.id.imageView1);
	  
	    Gallery g = (Gallery) findViewById(R.id.gallery1);  
	    g.setAdapter(new ImageAdapter(this, ReadSDCard()));  
	  
	    g.setOnItemClickListener(new OnItemClickListener() {  
	        public void onItemClick(AdapterView<?> parent,View v, int position, long id) {  
	        }  
	    });  
	}  
	  
	private List<String> ReadSDCard()  
	{  
	 List<String> tFileList = new ArrayList<String>();  
	  
	 //It have to be matched with the directory in SDCard  
	 File f = new File("../sdcard/FORTH2_IPCAM/IP_camera_IMAGE");  
	 // ../mnt/flash/DCIM/FORTH2_IPCAM/IP_camera_IMAGE 
	 //../sdcard/FORTH2_IPCAM/IP_camera_IMAGE : mobile
	 File[] files=f.listFiles();  
	  
	 for(int i=0; i<files.length; i++)  
	 {  
	  File file = files[i];  
	  /*It's assumed that all file in the path 
	    are in supported type*/  
	  tFileList.add(file.getPath());  
	 }  
	  
	 return tFileList;  
	}  
	  
	public class ImageAdapter extends BaseAdapter {  
	    int mGalleryItemBackground;  
	    private Context mContext;  
	    private List<String> FileList;  
	  
	    public ImageAdapter(Context c, List<String> fList) {  
	        mContext = c;  
	        FileList = fList;  
	        TypedArray a = obtainStyledAttributes(R.styleable.HelloGallery);  
	        mGalleryItemBackground = a.getResourceId(R.styleable.HelloGallery_android_galleryItemBackground,0);  
	        a.recycle();  
	    }  
	  
	    public int getCount() {  
	        return FileList.size();  
	    }  
	  
	    public Object getItem(int position) {  
	        return position;  
	    }   
	  
	    public long getItemId(int position) {  
	        return position;  
	    }  
	  
	    public View getView(int position, View convertView,ViewGroup parent) {  
	        i = new ImageView(mContext);  
	  
	        Bitmap bm = BitmapFactory.decodeFile( FileList.get(position).toString());  
	        i.setImageBitmap(bm);  
	      
	        i.setLayoutParams(new Gallery.LayoutParams(300, 200));  
	        i.setScaleType(ImageView.ScaleType.FIT_XY);  
	        i.setBackgroundResource(mGalleryItemBackground);  
	      
	        return i;  
	    }  
	}  
	  
	public TypedArray obtainStyledAttributes(int theme) {  
	    // TODO Auto-generated method stub  
	    return null;  
	}  
}
