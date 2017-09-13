package cam.surveil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ViewFlipper;
import android.view.MenuItem;
import android.widget.Toast;


public class pictureFrame extends Activity 	implements
	AdapterView.OnItemSelectedListener {
	
	/** This is a  Menu */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	super.onCreateOptionsMenu(menu);
    	menu.add(0,1,0,"Setting Mode");
    	menu.add(1,2,1,"Close");
    	Toast.makeText(pictureFrame.this, "Menu Clicked", Toast.LENGTH_SHORT).show();
    	Log.w("Menu Box", "Open MenuBox");

    	return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item){
     	 switch(item.getItemId()){
     	 case 1:
     		  Log.w("Menu Item","Item 1 was clicked");
     		  Toast.makeText(pictureFrame.this, "Setting Mode Clicked", Toast.LENGTH_SHORT).show();
     		  showDialog();
     		  break;

     	 case 2:
     		 Log.w("Menu Item","Item 2 was clicked");
     		 Toast.makeText(pictureFrame.this,"Close Clicked",Toast.LENGTH_SHORT).show();
     		 break; 
     	 }
     	 return true;
     	}

    /** DialogBox after call MenuBox */
    public void showDialog(){
    	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    	dialog.setTitle("Welcome To Setting Mode");
    	dialog.setMessage("Please to choose for flipping");
    	
    	final Spinner spinnerText = new Spinner(this);
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
  		android.R.layout.simple_spinner_dropdown_item, mStrings);
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinnerText.setAdapter(adapter);
        spinnerText.setOnItemSelectedListener(this);
        
    	LinearLayout.LayoutParams count = new LinearLayout.LayoutParams(
    	LinearLayout.LayoutParams.MATCH_PARENT
    			,LinearLayout.LayoutParams.MATCH_PARENT);
    	spinnerText.setLayoutParams(count);
    	dialog.setView(spinnerText);
    	
    	dialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Close Clicked", Toast.LENGTH_SHORT).show();
			}
		});
    	/** Show Alert Dialog */
    	AlertDialog alertDialog = dialog.create();
    	alertDialog.show();
    }
    
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window win = getWindow();
		win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);		
        win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); 
        
        setContentView(R.layout.main2);
        flipper1 = ((ViewFlipper)this.findViewById(R.id.viewFlipper1));
        flipper1.startFlipping();
    }
    
	public void onItemSelected(AdapterView parent, View v, int position, long id) {
		switch (position){
			case 0:
				Toast.makeText(pictureFrame.this, "Turn Up",Toast.LENGTH_SHORT).show();
				Log.w("case0","Push Up");
				flipper1.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_up_in));
				flipper1.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_up_out));
				break;
			case 1:
				Toast.makeText(pictureFrame.this, "Push Left",Toast.LENGTH_SHORT).show();
				Log.w("case1","Push Left");
				flipper1.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
				flipper1.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
				break;
			case 2:
				Toast.makeText(pictureFrame.this, "Fade",Toast.LENGTH_SHORT).show();
				Log.w("case2","Fade");
				flipper1.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.hyperspace_in));
				flipper1.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.hyperspace_out));
				break;
			case 3:
				Toast.makeText(pictureFrame.this, "Hyperspace",Toast.LENGTH_SHORT).show();
				Log.w("case3","Hyperspace");
				flipper1.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
				flipper1.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
			case 4:
				Toast.makeText(pictureFrame.this, "Slide in left",Toast.LENGTH_SHORT).show();
				Log.w("case4","Slide in left");
				flipper1.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
				flipper1.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
				break;	
			default:
				flipper1.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_up_in));
				flipper1.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_up_out));
				break;
				
		}
		
		// TODO Auto-generated method stub
		
	}
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	private String[] mStrings = {"Push up", "Push left", "Fade", "Hyperspace","Slide in Left"};
	private ViewFlipper flipper1;
	
}

    
