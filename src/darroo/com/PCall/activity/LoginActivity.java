package darroo.com.PCall.activity;

import darroo.com.PCall.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity {
	
	Button button1,button2;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		button1 = (Button)findViewById(R.id.button_dl);
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
			}
		});
		
		button2 = (Button)findViewById(R.id.button_zc);
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,RegisteredActivity.class);
				startActivity(intent);
			}
		});
	}

}
