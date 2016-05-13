package cn.edu.pku.wechat.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.pku.wechat.R;

public class ContactsFragment extends Fragment {

	private final String TAG = "ContactsFragment";

	private ListView mListView = null;
	private List<Info> infoList = new ArrayList<>();

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
		mListView = (ListView) view.findViewById(R.id.friends_list);
        initInfo();
        mListView.setAdapter(new ListViewAdapter());
	}

	private void initInfo() {
		Log.d(TAG, "init info");
		infoList.clear();
		int i = 0;
		Info info;
		while (i < 10) {
			info = new Info();
			info.setUsername("User" + i);
			infoList.add(info);
			i++;
		}
	}

	private class ListViewAdapter extends BaseAdapter {

		public ListViewAdapter() {

		}

		public int getCount() {
			return infoList.size();
		}

		public View getItem(int position) {
			return makeItemView(infoList.get(position).getUsername());
		}

		public long getItemId(int position) {
			return position;
		}

		//draw item
		private View makeItemView(String username) {
			LayoutInflater inflater = (LayoutInflater) getActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View itemView = inflater.inflate(R.layout.list_friend_item, null);

			TextView userView = (TextView) itemView.findViewById(R.id.username);
			userView.setText(username);

			return itemView;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			return makeItemView(infoList.get(position).getUsername());
		}
	}


	private class Info {
		private String username;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		@Override
		public String toString() {
			return "Info{" +
					"username='" + username + '\'' +
					'}';
		}
	}
}
