package com.example.qq.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.qq.R;
import com.example.qq.fragments.ContactFragment;
import com.example.qq.fragments.PersonalFragment;
import com.example.qq.fragments.SchoolFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private ContactFragment contactFragment;
    private PersonalFragment personalFragment;
    private SchoolFragment schoolFragment;
    private List<Fragment> fragmentList;
    private RadioGroup radioGroup;
    private String [] titles = {"个人主页","联系人","校园风采"};
    private TextView titleTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取登录信息
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", -1);

        viewPager = findViewById(R.id.viewpager);
        radioGroup = findViewById(R.id.rdgp);
        titleTxt = findViewById(R.id.titleTxt);

        //监听
        radioGroup.setOnCheckedChangeListener(this);
        viewPager.addOnPageChangeListener(this);
        //
        fragmentList = new ArrayList<>();
        fragmentList.add(personalFragment = new PersonalFragment());
        fragmentList.add(contactFragment = new ContactFragment());
        fragmentList.add(schoolFragment = new SchoolFragment());
        //初始fragment的用户信息
        Bundle bundle = new Bundle();
        bundle.putInt("userId",userId);
        personalFragment.setArguments(bundle);
        contactFragment.setArguments(bundle);


        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int idx = 0;
        switch (checkedId)
        {
            case R.id.personalRdBtn: idx = 0;break;
            case R.id.contactRdBtn:idx = 1; break;
            case R.id.schoolRdBtn: idx = 2;break;
        }
        viewPager.setCurrentItem(idx);
        titleTxt.setText(titles[idx]);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        RadioButton btn = (RadioButton) radioGroup.getChildAt(i);
        btn.setChecked(true);
        titleTxt.setText(titles[i]);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
