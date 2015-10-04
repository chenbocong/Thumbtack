package darroo.com.PCall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import darroo.com.PCall.R;
import darroo.com.PCall.activity.HelpFragment;
import darroo.com.PCall.activity.PersonFragment;
import darroo.com.PCall.activity.ReportFragment;
import darroo.com.PCall.activity.SettingFragment;

/**
 * Created by Hankkin on 2015/7/13.
 * 项目主界面
 */
public class MainActivity extends FragmentActivity {
    /*底部按钮四张图片*/
    private ImageView[] mTabs;
    /*我的Fragment*/
    private PersonFragment personFragment;
    /*上报Fragment*/
    private ReportFragment reportFragment;
    /*求助Fragment*/
    private HelpFragment helpFragment;
    /*设置Fragment*/
    private SettingFragment settingFragment;
    /*Fragment数组*/
    private Fragment[] fragments;
    /*Fragment索引*/
    private int index;
    /*当前Fragment索引*/
    private int currentTabIndex = 0;
    /*标题文本*/
    private TextView titleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        helpFragment = new HelpFragment();
        reportFragment = new ReportFragment();
        settingFragment = new SettingFragment();
        personFragment = new PersonFragment();
        fragments = new Fragment[]{helpFragment, reportFragment, settingFragment, personFragment};
        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_help, helpFragment).add(R.id.fragment_help, reportFragment)
                .add(R.id.fragment_help, settingFragment)
                .hide(settingFragment).hide(reportFragment).show(helpFragment).commit();
    }

    /**
     * 底部导航栏按钮切换事件
     * by Hankkin at:2015年7月13日 17:47:18
     *
     * @param view
     */
    public void onTabClicked(View view) {
        switch (view.getId()) {

            case R.id.rl_help:
                index = 0;
                titleTv.setText("求助");
                break;
            case R.id.rl_report:
                index = 1;
                titleTv.setText("上报");
                break;
            case R.id.rl_setting:
                index = 2;
                titleTv.setText("设置");
                break;
            case R.id.rl_person:
                index = 3;
                titleTv.setText("我的");
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_help, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }

    /**
     * 初始化组件
     * by Hankkin at:2015年7月13日 17:46:49
     */
    private void initViews() {

        mTabs = new ImageView[4];
        mTabs[0] = (ImageView) findViewById(R.id.help_iv);
        mTabs[1] = (ImageView) findViewById(R.id.report_iv);
        mTabs[2] = (ImageView) findViewById(R.id.setting_iv);
        mTabs[3] = (ImageView) findViewById(R.id.person_iv);
        mTabs[0].setSelected(true);

        titleTv = (TextView) findViewById(R.id.title_tv);
    }


}