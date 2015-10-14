package darroo.com.PCall.activity;

import com.zxing.activity.CaptureActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import darroo.com.PCall.R;

public class DetailedInformationActivity  extends Activity{
Button button;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailedinformationactivity);
		button = (Button)findViewById(R.id.button_qs);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DetailedInformationActivity.this,MainActivity.class);
				startActivity(intent);
			}
		});
	}
}
