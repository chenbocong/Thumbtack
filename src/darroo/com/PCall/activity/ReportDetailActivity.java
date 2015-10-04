package darroo.com.PCall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import darroo.com.PCall.R;

/**
 * Created by Hankkin on 2015/7/21.
 */
public class ReportDetailActivity extends Activity implements View.OnClickListener {

    private ReportDetailActivity instance;



    /*报警原因按钮*/
    private TextView tvReportWay;
    private ImageView backImg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.reportdetail);
        backImg = (ImageView) findViewById(R.id.back_btn);
        backImg.setOnClickListener(this);

        initViews();
    }


    private void initViews() {
        tvReportWay = (TextView) findViewById(R.id.tv_report_alarm);


    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_report_alarm:
                break;
            case R.id.back_btn:
                finish();
                break;
        }
    }
}