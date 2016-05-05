package cn.edu.pku.wechat.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.edu.pku.wechat.R;

public class ContactsFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_contacts, container, false);
		initView(view);
		return view;
	}
	/**
	 * 对布局中控件进行初始化
	 */
	public void initView(View view){
		
	}
}
