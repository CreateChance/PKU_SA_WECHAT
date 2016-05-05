package cn.edu.pku.wechat.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import cn.edu.pku.wechat.activities.MainActivity;
import cn.edu.pku.wechat.fragment.ChatFragment;
import cn.edu.pku.wechat.fragment.ContactsFragment;
import cn.edu.pku.wechat.fragment.MeFragment;

public class MainFragmentAdapter extends FragmentPagerAdapter{
	public final static int TAB_COUNT = 3;
	public MainFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int id) {
		switch (id) {
		case MainActivity.TAB_CHAT:
			ChatFragment chatFragment = new ChatFragment();
			return chatFragment;
		case MainActivity.TAB_CONTACTS:
			ContactsFragment contactsFragment = new ContactsFragment();
			return contactsFragment;
		case MainActivity.TAB_ME:
			MeFragment meFragment = new MeFragment();
			return meFragment;		
		}
		return null;
	}

	@Override
	public int getCount() {
		return TAB_COUNT;
	}
}