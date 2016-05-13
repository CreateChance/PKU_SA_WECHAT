package cn.edu.pku.wechat.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import cn.edu.pku.wechat.R;
import cn.edu.pku.wechat.adapter.MainFragmentAdapter;

public class MainActivity extends FragmentActivity implements OnClickListener {

	public static final int TAB_CHAT = 0;
	public static final int TAB_CONTACTS = 1;
	public static final int TAB_ME = 2;

	private ViewPager viewPager;
	private RadioButton main_tab_chat, main_tab_contacts, main_tab_me;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		addListener();
	}

	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		main_tab_chat = (RadioButton) findViewById(R.id.main_tab_chat);
		main_tab_contacts = (RadioButton) findViewById(R.id.main_tab_contacts);
		main_tab_me = (RadioButton) findViewById(R.id.main_tab_me);

		main_tab_chat.setOnClickListener(this);
		main_tab_contacts.setOnClickListener(this);
		main_tab_me.setOnClickListener(this);

		MainFragmentAdapter adapter = new MainFragmentAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);
	}

	@SuppressWarnings("deprecation")
	private void addListener() {
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int id) {
				switch (id) {
				case TAB_CHAT:
					main_tab_chat.setChecked(true);
					break;
				case TAB_CONTACTS:
					main_tab_contacts.setChecked(true);
					break;
				case TAB_ME:
					main_tab_me.setChecked(true);
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.main_tab_chat:
			viewPager.setCurrentItem(TAB_CHAT);
			break;
		case R.id.main_tab_contacts:
			viewPager.setCurrentItem(TAB_CONTACTS);
			break;
		case R.id.main_tab_me:
			viewPager.setCurrentItem(TAB_ME);
			break;		
		default:
			break;
		}

	}
}
