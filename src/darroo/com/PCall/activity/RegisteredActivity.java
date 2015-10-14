package darroo.com.PCall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import darroo.com.PCall.R;

public class RegisteredActivity extends Activity{
Button button;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registered_activity);
		button = (Button)findViewById(R.id.button_zc);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RegisteredActivity.this,LoginActivity.class);
				startActivity(intent);
			}
		});
	}

}
