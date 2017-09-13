package cam.surveil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

//import javax.xml.datatype.Duration;

import android.app.Activity;
import android.content.Context;
//import android.content.pm.ActivityInfo;
//import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class streamvideo extends Activity{

	StreamingLoop cameraLoop;
    StreamingLoop httpLoop;

    NativeAgent nativeAgt;
    CameraView myCamView;
    StreamingServer strServer;
    
    TextView myMessage;
    Button btnStart;

    boolean inServer = false;
    boolean inStreaming = false;
    int targetWid = 320;
    int targetHei = 240;

    final String checkingFile = "../sdcard/FORTH2_IPCAM/raw/myvideo.mp4";
    final String resourceDirectory = "../sdcard/FORTH2_IPCAM/raw";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window win = getWindow();
		win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);		
        win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		
	    setContentView(R.layout.testcamstream);
	
	}
	
    /*@Override
    public void onDestroy(){
    	super.onDestroy();
    }*/

    @Override
    public void onStart(){
    	super.onStart();
        setup(); //
    }

    @Override
    public void onResume(){
    	super.onResume();
    }
    
    @Override
    public void onPause(){    	
    	super.onPause();
        finish();
        System.exit(0);
    }
	
    private void setup() {
        clearResource();
        buildResource(); 
        
        NativeAgent.LoadLibraries(); 
        nativeAgt = new NativeAgent();
        cameraLoop = new StreamingLoop("cam.project");
        httpLoop = new StreamingLoop("cam.http");
        
        myCamView = (CameraView)findViewById(R.id.surface_overlay);
        SurfaceView sv = (SurfaceView)findViewById(R.id.surface_camera);
    	myCamView.SetupCamera(sv);//

        btnStart = (Button)findViewById(R.id.btn_start);
        btnStart.setOnClickListener(startAction);
        btnStart.setEnabled(true);
        
        myMessage = (TextView)findViewById(R.id.label_msg);

        View  v = (View)findViewById(R.id.layout_setup);
        v.setVisibility(View.VISIBLE);
	}
    
    private OnClickListener startAction = new OnClickListener() {
        @Override
        public void onClick(View v) {
            doAction();
        }
    };

	private void clearResource() {
        String[] str ={"rm", "-r", resourceDirectory};

        try { 
            Process ps = Runtime.getRuntime().exec(str);
            try {
                ps.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    private void buildResource() {
        String[] str ={"mkdir", resourceDirectory};

        try { 
            Process ps = Runtime.getRuntime().exec(str);
            try {
                ps.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
        
            copyResourceFile(R.raw.index, resourceDirectory + "/index.html"  );
            copyResourceFile(R.raw.style, resourceDirectory + "/style.css"  );
            copyResourceFile(R.raw.player, resourceDirectory + "/player.js"  );
            copyResourceFile(R.raw.player_object, resourceDirectory + "/player_object.swf"  );
            copyResourceFile(R.raw.player_controler, resourceDirectory + "/player_controler.swf"  );
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    private void startServer() {
        inServer = true;
        btnStart.setText( getString(R.string.action_stop) );
        btnStart.setEnabled(true);  
        NetInfoAdapter.Update(this);
        myMessage.setText( getString(R.string.msg_prepare_ok) + "http://" + NetInfoAdapter.getInfo("IP")  + ":8080" );

        try {
            strServer = new StreamingServer(8080, resourceDirectory); 
            strServer.setOnRequestListen(streamingRequest);
        } catch( IOException e ) {
            e.printStackTrace();
            Toast.makeText(streamvideo.this, "Can't start http server..", Toast.LENGTH_SHORT);
        }
    }
    
	    private void stopServer() {
	       inServer = false;
	       btnStart.setText( getString(R.string.action_start) );
	       btnStart.setEnabled(true);
	       myMessage.setText( getString(R.string.msg_idle));
	       if ( strServer != null) {
	            strServer.stop();
	            strServer = null;
	       }
	    }
	    
	    private boolean startStreaming() { ///
	        if ( inStreaming == true)
	            return false;
	        
	        cameraLoop.InitLoop();
	        httpLoop.InitLoop();
	        //nativeAgt.NativeStartStreamingMedia(cameraLoop.getReceiverFileDescriptor(),httpLoop.getSenderFileDescriptor());

	        myCamView.PrepareMedia(targetWid, targetHei);
	        boolean ret = myCamView.StartStreaming(cameraLoop.getSenderFileDescriptor());
	        if ( ret == false) {
	            return false;
	        } 
	        
	        new Handler(Looper.getMainLooper()).post(new Runnable() { 
	            public void run() { 
	            	//streaming
	                Toast.makeText(streamvideo.this, getString(R.string.msg_streaming), Toast.LENGTH_SHORT).show();
	                btnStart.setEnabled(false);
	            } 
	        });

	        inStreaming = true;
	        return true;
	    }

	    private void stopStreaming() {
	        if ( inStreaming == false)
	            return;
	        inStreaming = false;

	        myCamView.StopMedia(); 
	        httpLoop.ReleaseLoop();
	        cameraLoop.ReleaseLoop();
	        
	        nativeAgt.NativeStopStreamingMedia();
	        new Handler(Looper.getMainLooper()).post(new Runnable() { 
	            public void run() { 
	                btnStart.setEnabled(true);
	            } 
	        });
	    }

	    private void doAction() {
	         if ( inServer == false) {
	            myCamView.PrepareMedia(targetWid, targetHei);
	            boolean ret = myCamView.StartRecording(checkingFile);
	            if ( ret ) {
	            	//Checking camera ..
	            	myMessage.setText( getString(R.string.msg_prepare_waiting));
	            	new Handler().postDelayed(new Runnable() { 
	                    public void run() { 
	                        myCamView.StopMedia();
	                        //if ( NativeAgent.NativeCheckMedia(targetWid, targetHei, checkingFile)) { // 
	                            startServer();    
	                       // } else {
	                       //     btnStart.setEnabled(true);
	                              //Prepareing is error, please try again.
	                       //     Toast.makeText(streamvideo.this, getString(R.string.msg_prepare_error2),Toast.LENGTH_SHORT).show();
	                       // }
	                    } 
	                }, 2000); // 2 seconds to release 
	            } else {
	            	//Your phone do not support MP4+AMR_BN format!!
	                Toast.makeText(streamvideo.this, R.string.msg_prepare_error1,Toast.LENGTH_SHORT).show();
	            }
	        } else {
	            stopServer();
	        }
	    
	    }
	    
	    private void copyResourceFile(int rid, String targetFile) throws IOException {
	        InputStream fin = ((Context)this).getResources().openRawResource(rid);
	        FileOutputStream fos = new FileOutputStream(targetFile);  

	        int     length;
	        byte[] buffer = new byte[1024*32]; 
	        while( (length = fin.read(buffer)) != -1){
	            fos.write(buffer,0,length);
	        }
	        fin.close();
	        fos.close();
	    }

	    StreamingServer.OnRequestListen streamingRequest = new StreamingServer.OnRequestListen() {
	        @Override
	        public InputStream onRequest() {
	            Log.d("IPCAM", "Request live streaming...");
	            if ( startStreaming() == false) ///
	                return null;
	            try {
	                InputStream ins = httpLoop.getInputStream(); 
	                return ins;
	            } catch (IOException e) {
	                e.printStackTrace();
	                Log.d("IPCAM", "call httpLoop.getInputStream() error");
	                stopStreaming();              
	            } 
	            Log.d("IPCAM", "Return a null response to request");
	            return null;
	        }
	        
	        @Override 
	        public void requestDone() {
	            Log.d("IPCAM", "Request live streaming is Done!");
	            stopStreaming();     
	        }
	    };
	    
}
