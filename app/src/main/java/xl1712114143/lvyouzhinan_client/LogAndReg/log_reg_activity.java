package xl1712114143.lvyouzhinan_client.LogAndReg;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xl1712114143.lvyouzhinan_client.R;

public class log_reg_activity extends AppCompatActivity {

    private TabLayout loglayout;
    private ViewPager reglayout;
    public static final int MOVABLE_COUNT = 2;
    private int tabCount = 2;
    private List<String> tabs;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_reg);

        loglayout = findViewById(R.id.logLayout);
        reglayout = findViewById(R.id.regLayout);

        initDatas();
        initViewPager();
        initTabLayout();
    }

    private void initTabLayout() {
        //MODE_FIXED标签栏不可滑动，各个标签会平分屏幕的宽度
        loglayout.setTabMode(tabCount <= MOVABLE_COUNT ? TabLayout.MODE_FIXED : TabLayout.MODE_SCROLLABLE);
        //指示条的颜色
        loglayout.setSelectedTabIndicatorColor(getResources().getColor(android.R.color.holo_blue_dark));
        //关联tabLayout和ViewPager,两者的选择和滑动状态会相互影响
        loglayout.setupWithViewPager(reglayout);
        //自定义标签布局
        for (int i = 0; i < tabs.size(); i++) {
            TabLayout.Tab tab = loglayout.getTabAt(i);
            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.tabview_activity, loglayout, false);
            tv.setText(tabs.get(i));
            tab.setCustomView(tv);
        }
    }


    private void initViewPager() {
        reglayout.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    private void initDatas() {
        tabs = new ArrayList<>();
        tabs.add("登录");
        tabs.add("注册");

        fragments = new ArrayList<>();

            fragments.add(new Fragment_log());
            fragments.add(new Fragment_reg());


    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
