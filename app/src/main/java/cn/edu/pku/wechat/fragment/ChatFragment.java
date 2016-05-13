package cn.edu.pku.wechat.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.pku.wechat.R;
import cn.edu.pku.wechat.activities.ChatActivity;
import cn.edu.pku.wechat.decoding.Intents;

public class ChatFragment extends Fragment {

	private final String TAG = "ChatFragment";

    private ListView listView = null;
    private List<Info> infoList = new ArrayList<>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_chat, container, false);
		initView(view);
		return view;
	}
	/**
	 * 对布局中控件进行初始化
	 */
	public void initView(View view) {
		listView = (ListView) view.findViewById(R.id.recent_msg_list);
        initInfo();

        listView.setAdapter(new ListViewAdapter());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // start chat activity here.
                Log.d(TAG, "onItemClick");
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra(ChatActivity.EXTRA_USERNAME, infoList.get(position).getUsername());
                startActivity(intent);
            }
        });
	}

    private void initInfo() {
        Log.d(TAG, "init info");
        infoList.clear();
        int i = 0;
        Info info;
        while (i < 10) {
            info = new Info();
            info.setUsername("User" + i);
            info.setMessage("Message");
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
            return makeItemView(infoList.get(position).getUsername(),
                    infoList.get(position).getMessage());
        }

        public long getItemId(int position) {
            return position;
        }

        //draw item
        private View makeItemView(String username, String msg) {
            LayoutInflater inflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View itemView = inflater.inflate(R.layout.recent_msg_item, null);

            TextView userView = (TextView) itemView.findViewById(R.id.username);
            userView.setText(username);
            TextView msgView = (TextView) itemView.findViewById(R.id.new_msg);
            msgView.setText(msg);

            return itemView;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            return makeItemView(infoList.get(position).getUsername(),
                    infoList.get(position).getMessage());
        }
    }

    private class Info {
        private String username;
        private String message;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "Info{" +
                    "username='" + username + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
