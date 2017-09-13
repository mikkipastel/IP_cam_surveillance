package cam.surveil;

import java.io.IOException;

import cam.surveil.DatabaseAdapter;
import cam.surveil.NetInfoAdapter;

import android.app.Activity;
import android.database.Cursor;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
//import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.TextView;

public class showIPport extends Activity{
	TextView show_slide;
	String ip,port,slide;
	int time;
	protected TextView show_ip = null;
    protected TextView show_port = null;
	private EditText idText = null;
	
	static DatabaseAdapter db;
	public String idSave = "0";
	public String idSave2 = "1";
	
    boolean inServer = false;
    StreamingServer strServer;
    final String resourceDirectory = "../sdcard/FORTH2_IPCAM/raw";
    
    Button test;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.ipport); 
		
		//set IP & Port
		show_ip = (TextView)findViewById(R.id.showip);
		show_port = (TextView)findViewById(R.id.showport); 
		
		idText = (EditText) findViewById(R.id.editTextID);
		
		db = new DatabaseAdapter(this);
        db.close();
        
        db.open();
        Cursor c = db.getAllIPport();
        if(c.moveToFirst())
        {
        	do{
        		displayIPport(c);
        	}while(c.moveToNext());
        }
        //c.moveToNext()
        db.close();
		
		//button
		/*saveall.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ip = show_ip.getText().toString();
				port = show_port.getText().toString();
				idSave = idText.getText().toString();
				
				Intent connect = new Intent(setting_all.this, databaseProgram.class);
				
				connect.putExtra("sendIptext", ip);
				connect.putExtra("sendPort", port);
				
				startActivity(connect);
				
				db.open();
				
				if(!TextUtils.isEmpty(idSave))
				{
					db.deleteIPport(Integer.valueOf(idSave));
				}

				db.insertIPport(ip, port, "save IP and port", "000");
				
				db.close();
				
				Toast.makeText(getBaseContext(), "Save IP and port Completed", Toast.LENGTH_SHORT).show();
			}
			
		});*/
		
        //show ip & port
        startServer();
        
        //test preference
        /*test = (Button)findViewById(R.id.button1);
        test.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent goTest = new Intent(getApplicationContext(),capturetime.class);
				startActivity(goTest);
			}
        	
        });*/
		
	}
	
	 private void startServer() {
	        inServer = true;
	        //btnStart.setText( getString(R.string.action_stop) );
	        //btnStart.setEnabled(true);    
	        NetInfoAdapter.Update(this);
	        show_ip.setText("http://" + NetInfoAdapter.getInfo("IP"));
	        show_port.setText("8080");

	        try {
	            strServer = new StreamingServer(8080, resourceDirectory); 
	        } catch( IOException e ) {
	            e.printStackTrace();
	            Toast.makeText(this, "Can't start http server..", Toast.LENGTH_SHORT);
	        }
	    }
	
	public void displayIPport(Cursor c){
		
        idText.append(c.getString(0));
        show_ip.append(c.getString(1));
        show_port.append(c.getString(2));
        
    }
	
}

	
