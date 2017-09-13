package cam.surveil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class NotificationReceiver extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent goWork = new Intent(getApplicationContext(),IP_cam_surveillanceActivity.class);
		startActivity(goWork);
	}
}
