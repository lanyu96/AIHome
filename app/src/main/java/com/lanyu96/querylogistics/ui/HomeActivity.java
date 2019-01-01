package com.lanyu96.querylogistics.ui;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.lanyu96.querylogistics.R;
import com.lanyu96.querylogistics.fragment.HomeFragment;
import com.lanyu96.querylogistics.fragment.MeFragment;
import com.lanyu96.querylogistics.fragment.PackagesFragment;
import com.lanyu96.querylogistics.fragment.WeatherFragment;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class HomeActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    private MeFragment meFragment;
    private PackagesFragment packagesFragment;
    private WeatherFragment weatherFragment;
    //    private ViewPager viewPager;
    private NavigationTabBar navigationTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //实例化一个MenuInflater对象
        MenuInflater inflater = getMenuInflater();
        //解析菜单文件
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Toast.makeText(this, "点击了关于菜单", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void initUI() {
//        viewPager = findViewById(R.id.vp_horizontal_ntb);

        //实例化fragment
        homeFragment = new HomeFragment();
        meFragment = new MeFragment();
        packagesFragment = new PackagesFragment();
        weatherFragment = new WeatherFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.act_all_fragment_fl, homeFragment)
                .add(R.id.act_all_fragment_fl, meFragment)
                .add(R.id.act_all_fragment_fl,packagesFragment)
                .add(R.id.act_all_fragment_fl,weatherFragment).commitAllowingStateLoss();
        getSupportFragmentManager().beginTransaction().show(homeFragment)
                .hide(meFragment)
                .hide(packagesFragment)
                .hide(weatherFragment).commitAllowingStateLoss();


        navigationTabBar = findViewById(R.id.ntb_horizontal);

        //设置底部Tab 图标的标题
        String[] titles = new String[]{"主页", "快递","天气", "我的"};
        ArrayList<View> views = new ArrayList<>();
        //设置每个View的不同背景
//        int[] viewBgs = new int[]{Color.RED, Color.GRAY, Color.BLUE};
        //设置底部Tab的各个图标
        int[] icons = new int[]{R.drawable.icon_home, R.drawable.icon_setting,R.drawable.icon_weather, R.drawable.icon_me};
        //用来生成各个不同选项的
        ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            models.add(
                    new NavigationTabBar.Model.Builder(
                            getResources().getDrawable(icons[i]),
                            Color.parseColor("#00000000"))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_sixth))
                            .title(titles[i])
//                            .badgeTitle("NTB")    //角标
                            .build()
            );
            View view = new View(this);
//            view.setBackgroundColor(viewBgs[i]);
//            views.add(view);
        }
//        viewPager.setAdapter(new TabAdapter(this, views));

        navigationTabBar.setModels(models);
        navigationTabBar.setModelIndex(0);
        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {
                switch (index) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().show(homeFragment)
                                .hide(meFragment)
                                .hide(weatherFragment)
                                .hide(packagesFragment).commitAllowingStateLoss();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().show(packagesFragment)
                                .hide(meFragment)
                                .hide(weatherFragment)
                                .hide(homeFragment).commitAllowingStateLoss();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().show(weatherFragment)
                                .hide(homeFragment)
                                .hide(meFragment)
                                .hide(packagesFragment).commitAllowingStateLoss();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction().show(meFragment)
                                .hide(homeFragment)
                                .hide(packagesFragment)
                                .hide(weatherFragment)
                                .commitAllowingStateLoss();
                }

            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {

            }
        });

    }
}
