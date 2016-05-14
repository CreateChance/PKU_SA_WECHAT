package com.way.activity;

import java.util.LinkedList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.way.adapter.RecentAdapter;
import com.way.app.PushApplication;
import com.way.baidupush.client.PushMessageReceiver;
import com.way.bean.MessageItem;
import com.way.bean.RecentItem;
import com.way.bean.User;
import com.way.common.util.HomeWatcher;
import com.way.common.util.HomeWatcher.OnHomePressedListener;
import com.way.common.util.JsonUtil;
import com.way.common.util.L;
import com.way.common.util.NetUtil;
import com.way.common.util.SharePreferenceUtil;
import com.way.common.util.T;
import com.way.db.MessageDB;
import com.way.db.RecentDB;
import com.way.db.UserDB;
import com.way.fragment.LeftFragment;
import com.way.fragment.RightFragment;
import com.way.push.R;
import com.way.slidingmenu.BaseSlidingFragmentActivity;
import com.way.slidingmenu.SlidingMenu;
import com.way.swipelistview.BaseSwipeListViewListener;
import com.way.swipelistview.SwipeListView;

public class MainActivity extends BaseSlidingFragmentActivity implements
		OnClickListener, PushMessageReceiver.EventHandler,
		OnHomePressedListener {
	public static final int NEW_MESSAGE = 0x000;// 有新消息
	public static final int NEW_FRIEND = 0x001;// 有好友加入
	protected SlidingMenu mSlidingMenu;
	private SwipeListView mRecentListView;
	private TextView mEmpty;
	private LinkedList<RecentItem> mRecentDatas;
	private RecentAdapter mAdapter;
	private PushApplication mApplication;
	private UserDB mUserDB;
	private MessageDB mMsgDB;
	private RecentDB mRecentDB;
	private SharePreferenceUtil mSpUtil;
	private View mNetErrorView;
	private LeftFragment mLeftFragment;
	private TextView mTitleName;
	private HomeWatcher mHomeWatcher;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case NEW_FRIEND:
				User u = (User) msg.obj;
				// mUserDB.addUser(u);
				mLeftFragment = (LeftFragment) getSupportFragmentManager()
						.findFragmentById(R.id.main_left_fragment);
				mLeftFragment.updateAdapter();// 更新
				T.showShort(mApplication, "好友列表已更新!");
				break;
			case NEW_MESSAGE:
				String message = (String) msg.obj;
				String userId = JsonUtil.getFromUserId(message);
				String nick = JsonUtil.getFromUserNick(message);
				String content = JsonUtil.getMsgContent(message);
				int headId = 0;
				try {
					headId = Integer
							.parseInt(JsonUtil.getFromUserHead(message));
				} catch (Exception e) {
					L.e("head is not integer  " + e);
				}
				if (mUserDB.selectInfo(userId) == null) {// 如果不存在此好友，则添加到数据库
					User user = new User(userId,
							JsonUtil.getChannelId(message), nick, headId, 0);
					mUserDB.addUser(user);
					mLeftFragment = (LeftFragment) getSupportFragmentManager()
							.findFragmentById(R.id.main_left_fragment);
					mLeftFragment.updateAdapter();// 更新一下好友列表
				}
				// TODO Auto-generated method stub
				MessageItem item = new MessageItem(
						MessageItem.MESSAGE_TYPE_TEXT, nick,
						System.currentTimeMillis(), content, headId, true, 1);
				mMsgDB.saveMsg(userId, item);
				// 保存到最近会话列表
				RecentItem recentItem = new RecentItem(userId, headId, nick,
						content, 0, System.currentTimeMillis());
				mRecentDB.saveRecent(recentItem);
				mAdapter.addFirst(recentItem);
				T.showShort(mApplication, nick + ":" + content);
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initSlidingMenu();
		setContentView(R.layout.main_center_layout);
		initData();
		initView(savedInstanceState);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mHomeWatcher = new HomeWatcher(this);
		mHomeWatcher.setOnHomePressedListener(this);
		mHomeWatcher.startWatch();
		if (!NetUtil.isNetConnected(this))
			mNetErrorView.setVisibility(View.VISIBLE);
		else {
			mNetErrorView.setVisibility(View.GONE);
		}
		PushMessageReceiver.ehList.add(this);
		initRecentData();
		mApplication.getNotificationManager().cancel(
				PushMessageReceiver.NOTIFY_ID);
		PushMessageReceiver.mNewNum = 0;
		if (mTitleName != null)
			mTitleName.requestFocusFromTouch();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mHomeWatcher.setOnHomePressedListener(null);
		mHomeWatcher.stopWatch();
		PushMessageReceiver.ehList.remove(this);// 暂停就移除监听
	}

	private void initData() {
		mApplication = PushApplication.getInstance();
		mSpUtil = mApplication.getSpUtil();
		mUserDB = mApplication.getUserDB();
		mMsgDB = mApplication.getMessageDB();
		mRecentDB = mApplication.getRecentDB();
	}

	private void initRecentData() {
		// TODO Auto-generated method stub
		mRecentDatas = mRecentDB.getRecentList();
		mAdapter = new RecentAdapter(this, mRecentDatas, mRecentListView);
		mRecentListView.setAdapter(mAdapter);

	}
	public void upDateList(){
		initRecentData();
	}

	private void initView(Bundle savedInstanceState) {
		mNetErrorView = findViewById(R.id.net_status_bar_top);
		mNetErrorView.setOnClickListener(this);

		mSlidingMenu.setSecondaryMenu(R.layout.main_right_layout);
		FragmentTransaction mFragementTransaction = getSupportFragmentManager()
				.beginTransaction();
		Fragment mFrag = new RightFragment();
		mFragementTransaction.replace(R.id.main_right_fragment, mFrag);
		mFragementTransaction.commit();
		// TODO Auto-generated method stub
		findViewById(R.id.ivTitleBtnLeft)
				.setOnClickListener(this);
		findViewById(R.id.ivTitleBtnRigh)
				.setOnClickListener(this);
		mTitleName = (TextView) findViewById(R.id.ivTitleName);
		mTitleName.setText(mSpUtil.getNick());

		mRecentListView = (SwipeListView) findViewById(R.id.recent_listview);
		mEmpty = (TextView) findViewById(R.id.empty);
		mRecentListView.setEmptyView(mEmpty);
		mRecentListView
				.setSwipeListViewListener(new BaseSwipeListViewListener() {
					@Override
					public void onOpened(int position, boolean toRight) {
					}

					@Override
					public void onClosed(int position, boolean fromRight) {
					}

					@Override
					public void onListChanged() {
					}

					@Override
					public void onMove(int position, float x) {
					}

					@Override
					public void onStartOpen(int position, int action,
							boolean right) {
						// L.d("swipe", String.format(
						// "onStartOpen %d - action %d", position, action));
					}

					@Override
					public void onStartClose(int position, boolean right) {
						// L.d("swipe",
						// String.format("onStartClose %d", position));
					}

					@Override
					public void onClickFrontView(int position) {
						// L.d("swipe",
						// String.format("onClickFrontView %d", position));
						// T.showShort(mApplication, "item onClickFrontView ="
						// + mAdapter.getItem(position));
						RecentItem item = (RecentItem) mAdapter
								.getItem(position);
						User u = new User(item.getUserId(), "", item.getName(), item.getHeadImg(), 0);
						mMsgDB.clearNewCount(item.getUserId());
						Intent toChatIntent = new Intent(MainActivity.this,
								ChatActivity.class);
						toChatIntent.putExtra("user", u);
						startActivity(toChatIntent);
					}

					@Override
					public void onClickBackView(int position) {
						mRecentListView.closeOpenedItems();// 关闭打开的项
					}

					@Override
					public void onDismiss(int[] reverseSortedPositions) {
						for (int position : reverseSortedPositions) {
							mAdapter.remove(position);
						}
						// mAdapter.notifyDataSetChanged();
					}
				});
	}

	private void initSlidingMenu() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int mScreenWidth = dm.widthPixels;// 获取屏幕分辨率宽度
		// TODO Auto-generated method stub
		setBehindContentView(R.layout.main_left_layout);// 设置左菜单
		FragmentTransaction mFragementTransaction = getSupportFragmentManager()
				.beginTransaction();
		Fragment mFrag = new LeftFragment();
		mFragementTransaction.replace(R.id.main_left_fragment, mFrag);
		mFragementTransaction.commit();
		// customize the SlidingMenu
		mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);// 设置是左滑还是右滑，还是左右都可以滑，我这里左右都可以滑
		mSlidingMenu.setShadowWidth(mScreenWidth / 40);// 设置阴影宽度
		mSlidingMenu.setBehindOffset(mScreenWidth / 8);// 设置菜单宽度
		mSlidingMenu.setFadeDegree(0.35f);// 设置淡入淡出的比例
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		mSlidingMenu.setShadowDrawable(R.drawable.slidingmenu_shadow);// 设置左菜单阴影图片
		mSlidingMenu.setSecondaryShadowDrawable(R.drawable.right_shadow);// 设置右菜单阴影图片
		mSlidingMenu.setFadeEnabled(true);// 设置滑动时菜单的是否淡入淡出
		mSlidingMenu.setBehindScrollScale(0.333f);// 设置滑动时拖拽效果
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ivTitleBtnLeft:
			mSlidingMenu.showMenu(true);
			break;
		case R.id.ivTitleBtnRigh:
			mSlidingMenu.showSecondaryMenu(true);
			break;
		case R.id.net_status_bar_info_top:
			// 跳转到网络设置
			startActivity(new Intent(
					android.provider.Settings.ACTION_WIFI_SETTINGS));
			break;
		default:
			break;
		}
	}

	@Override
	public void onMessage(String message) {
		// TODO Auto-generated method stub
		Message handlerMsg = handler.obtainMessage(NEW_MESSAGE);
		handlerMsg.obj = message;
		handler.sendMessage(handlerMsg);
	}

	@Override
	public void onBind(String method, int errorCode, String content) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNotify(String title, String content) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNetChange(boolean isNetConnected) {
		// TODO Auto-generated method stub
		if (!isNetConnected) {
			T.showShort(this, R.string.net_error_tip);
			mNetErrorView.setVisibility(View.VISIBLE);
		} else {
			mNetErrorView.setVisibility(View.GONE);
		}
	}

	@Override
	public void onNewFriend(User u) {
		// TODO Auto-generated method stub
		Message handlerMsg = handler.obtainMessage(NEW_FRIEND);
		handlerMsg.obj = u;
		handler.sendMessage(handlerMsg);
	}

	/**
	 * 连续按两次返回键就退出
	 */
	private long firstTime;

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (System.currentTimeMillis() - firstTime < 3000) {
			mApplication.showNotification();
			finish();
		} else {
			firstTime = System.currentTimeMillis();
			if (mSpUtil.getMsgNotify())
				T.showShort(this, R.string.press_again_backrun);
			else
				T.showShort(this, R.string.press_again_exit);
		}
	}

	@Override
	public void onHomePressed() {
		// 先判断应用是否在运行，
		mApplication.showNotification();
	}

	@Override
	public void onHomeLongPressed() {
		// do nothing
	}

}
