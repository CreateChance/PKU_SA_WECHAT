package com.way.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.way.activity.ChatActivity;
import com.way.activity.MainActivity;
import com.way.app.PushApplication;
import com.way.bean.User;
import com.way.common.util.L;
import com.way.common.util.SharePreferenceUtil;
import com.way.db.MessageDB;
import com.way.db.RecentDB;
import com.way.db.UserDB;
import com.way.push.R;
import com.way.xlistview.XExpandableListView;
import com.way.xlistview.XExpandableListView.IXListViewListener;

public class LeftFragment extends Fragment {
	private static final String[] groups = { "未分组好友", "我的好友", "我的同学", "我的家人",
			"我的同事" };

	private PushApplication mApplication;
	private UserDB mUserDB;
	private RecentDB mRecentDB;
	private MessageDB mMsgDB;
	private SharePreferenceUtil mSpUtil;
	private XExpandableListView xListView;
	private LayoutInflater mInflater;
	private List<String> mGroup;// 组名
	private Map<Integer, List<User>> mChildren;// 每一组对应的child
	private MyExpandableListAdapter mAdapter;

	public void updateAdapter() {
		if (xListView != null) {
			L.i("update friend...");
			initUserData();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_left_fragment, container,
				false);
		initView(view);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApplication = PushApplication.getInstance();
		mUserDB = mApplication.getUserDB();
		mMsgDB = mApplication.getMessageDB();
		mRecentDB = mApplication.getRecentDB();
		mSpUtil = mApplication.getSpUtil();
	}

	@Override
	public void onResume() {
		super.onResume();
		initUserData();
	}

	private void initView(View view) {
		mInflater = LayoutInflater.from(getActivity());

		// title
		view.findViewById(R.id.ivTitleBtnLeft).setVisibility(View.GONE);
		view.findViewById(R.id.ivTitleBtnRigh).setVisibility(View.GONE);
		TextView title = (TextView) view.findViewById(R.id.ivTitleName);
		title.setText(R.string.left_title_name);

		xListView = (XExpandableListView) view
				.findViewById(R.id.friend_xlistview);

		xListView.setGroupIndicator(null);
		xListView.setPullLoadEnable(false);// 禁止下拉加载更多
		xListView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				// handler.postDelayed(add, 2000);
				initUserData();
				xListView.setAdapter(mAdapter);
				xListView.stopRefresh();
				xListView.setRefreshTime(System.currentTimeMillis());
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
			}
		});
//		xListView.setOnChildClickListener(new OnChildClickListener() {
//
//			@Override
//			public boolean onChildClick(ExpandableListView parent, View v,
//					int groupPosition, int childPosition, long id) {
//				// TODO Auto-generated method stub
//				User u = (User) mAdapter.getChild(groupPosition, childPosition);
//				mMsgDB.clearNewCount(u.getUserId());// 新消息置空
//				Intent toChatIntent = new Intent(getActivity(),
//						ChatActivity.class);
//				toChatIntent.putExtra("user", u);
//				startActivity(toChatIntent);
//				return false;
//			}
//		});
	}

	/**
	 * 初始化好友数组
	 */
	private void initUserData() {
		// TODO Auto-generated method stub
		// 实例化组名
		mGroup = new ArrayList<String>();
		mChildren = new HashMap<Integer, List<User>>();// 给每一组实例化child
		List<User> dbUsers = mUserDB.getUser();// 查询本地数据库所有好友
		// 初始化组名和child
		for (int i = 0; i < groups.length; ++i) {
			mGroup.add(groups[i]);// 组名
			List<User> childUsers = new ArrayList<User>();// 每一组的child
			mChildren.put(i, childUsers);
		}
		// 给每一组添加数据
		for (User u : dbUsers) {
			for (int i = 0; i < mGroup.size(); ++i) {
				if (u.getGroup() == i) {
					mChildren.get(i).add(u);
				}
			}
		}
		mAdapter = new MyExpandableListAdapter(mGroup, mChildren);
		xListView.setAdapter(mAdapter);
	}

	public class MyExpandableListAdapter extends BaseExpandableListAdapter {
		private List<String> mGroup;// 组名
		private Map<Integer, List<User>> mChildren;// 每一组对应的child

		public MyExpandableListAdapter(List<String> group,
				Map<Integer, List<User>> children) {
			// TODO Auto-generated constructor stub
			mGroup = group;
			mChildren = children;
		}

		public void addUser(User u) {
			int groupId = u.getGroup();
			if (groupId < mGroup.size()) {
				mChildren.get(groupId).add(u);
				notifyDataSetChanged();// 更新一下
			}
		}

		public Object getChild(int groupPosition, int childPosition) {
			return mChildren.get(groupPosition).get(childPosition);
		}

		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		public int getChildrenCount(int groupPosition) {
			return mChildren.get(groupPosition).size();
		}

		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.contact_list_item_for_buddy, null);
			}
			TextView nick = (TextView) convertView
					.findViewById(R.id.contact_list_item_name);
			final User u = (User) getChild(groupPosition, childPosition);
			nick.setText(u.getNick());
			TextView state = (TextView) convertView
					.findViewById(R.id.cpntact_list_item_state);
			state.setText(u.getUserId());
			ImageView head = (ImageView) convertView.findViewById(R.id.icon);
			head.setImageResource(PushApplication.heads[u.getHeadIcon()]);
			convertView.setOnClickListener(new android.view.View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mMsgDB.clearNewCount(u.getUserId());// 新消息置空
					Intent toChatIntent = new Intent(getActivity(),
							ChatActivity.class);
					toChatIntent.putExtra("user", u);
					startActivity(toChatIntent);
				}
			});
			convertView.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					// TODO Auto-generated method stub
					new AlertDialog.Builder(getActivity())
							.setMessage("确定删除 " + u.getNick()+" 吗？")
							.setPositiveButton(android.R.string.ok,
									new OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											mUserDB.delUser(u);
											updateAdapter();
											mRecentDB.delRecent(u.getUserId());
											((MainActivity) getActivity())
													.upDateList();
										}
									})
							.setNegativeButton(android.R.string.cancel, null)
							.create().show();
					return false;
				}
			});
			return convertView;
		}

		public Object getGroup(int groupPosition) {
			return mGroup.get(groupPosition);
		}

		public int getGroupCount() {
			return mGroup.size();
		}

		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		/**
		 * create group view and bind data to view
		 */
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.contact_buddy_list_group, null);
			}
			TextView groupName = (TextView) convertView
					.findViewById(R.id.group_name);
			groupName.setText(getGroup(groupPosition).toString());
			TextView onlineNum = (TextView) convertView
					.findViewById(R.id.online_count);
			onlineNum.setText(getChildrenCount(groupPosition) + "/"
					+ getChildrenCount(groupPosition));
			ImageView indicator = (ImageView) convertView
					.findViewById(R.id.group_indicator);
			if (isExpanded)
				indicator.setImageResource(R.drawable.indicator_expanded);
			else
				indicator.setImageResource(R.drawable.indicator_unexpanded);
			return convertView;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		public boolean hasStableIds() {
			return true;
		}

	}

}
