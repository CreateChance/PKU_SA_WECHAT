package com.way.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.way.adapter.HeadAdapter;
import com.way.adapter.SexAdapter;
import com.way.app.PushApplication;
import com.way.bean.User;
import com.way.common.util.JsonUtil;
import com.way.common.util.L;
import com.way.common.util.SendMsgAsyncTask;
import com.way.common.util.SharePreferenceUtil;
import com.way.common.util.T;
import com.way.db.UserDB;
import com.way.push.R;
import com.way.slidinglayer.SlidingLayer;
import com.way.slidinglayer.SlidingLayer.OnInteractListener;
import com.way.switchbtn.SwitchButton;
import com.way.wheel.WheelView;
import com.way.wheel.adapters.ArrayWheelAdapter;

public class RightFragment extends Fragment implements OnClickListener,
		OnCheckedChangeListener, OnInteractListener {
	public static final String SET_FILE_NAME = "Settings";
	public static final String MESSAGE_NOTIFY_KEY = "message_notify";
	public static final String MESSAGE_SOUND_KEY = "message_sound";
	public static final String SHOW_HEAD_KEY = "show_head";

	private PushApplication mApplication;
	private SharePreferenceUtil mSpUtil;
	private UserDB mUserDB;
	private LayoutInflater mInflater;
	private SwitchButton mMsgNotifySwitch;
	private SwitchButton mMsgSoundSwitch;
	private SwitchButton mShowHeadSwitch;
	private ImageView mHead;
	private TextView mNick;
	private View mAccountSetting;
	private View mMyProfile;
	private View mFaceJazzEffect;
	private View mAcountInfo;
	private View mFeedBack;
	private SlidingLayer mSlidingLayer;
	private Button mExitAppBtn, mComfirmExitBtn;

	private View mExitConfirmView;
	private View mAcountSetView;
	private View mFaceEffectView;
	private View mFeedBackView;
	private View mAboutView;

	private WheelView mFaceEffect;
	private Button mFaceEffectBtn;
	private EditText mFeedBackET;
	private Button mFeedBackBtn;

	private Button mAccountBtn;
	private WheelView mHeadWheel;
	private WheelView mSexWheel;
	private EditText mNickEt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mInflater = LayoutInflater.from(getActivity());
		mApplication = PushApplication.getInstance();
		mUserDB = mApplication.getUserDB();
		mSpUtil = mApplication.getSpUtil();

		initAcountSetView();
		initFaceEffectView();
		initExitView();
		initFeedBackView();
		initAboutView();

	}

	private void initFeedBackView() {
		// TODO Auto-generated method stub
		mFeedBackView = mInflater.inflate(R.layout.feed_back_view, null);
		mFeedBackET = (EditText) mFeedBackView.findViewById(R.id.fee_back_edit);
		mFeedBackBtn = (Button) mFeedBackView.findViewById(R.id.feed_back_btn);
		mFeedBackBtn.setOnClickListener(this);
	}

	private void initFaceEffectView() {
		String items[] = getActivity().getResources().getStringArray(
				R.array.jazzy_effects_ch);
		mFaceEffectView = mInflater.inflate(R.layout.face_jazz_effect_view,
				null);
		mFaceEffect = (WheelView) mFaceEffectView
				.findViewById(R.id.face_effect);
		mFaceEffectBtn = (Button) mFaceEffectView
				.findViewById(R.id.face_effect_btn);
		mFaceEffectBtn.setOnClickListener(this);
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(
				getActivity(), items);
		adapter.setTextSize(18);
		mFaceEffect.setViewAdapter(adapter);
		mFaceEffect.setCurrentItem(mSpUtil.getFaceEffect());
	}

	private void initAcountSetView() {
		// TODO Auto-generated method stub
		mAcountSetView = mInflater.inflate(R.layout.accout_set_view, null);
		mAccountBtn = (Button) mAcountSetView.findViewById(R.id.acount_set_btn);
		mAccountBtn.setOnClickListener(this);
		mNickEt = (EditText) mAcountSetView.findViewById(R.id.nick_ed);
		mHeadWheel = (WheelView) mAcountSetView.findViewById(R.id.acount_head);
		mSexWheel = (WheelView) mAcountSetView.findViewById(R.id.acount_sex);
		mHeadWheel.setViewAdapter(new HeadAdapter(getActivity()));
		mSexWheel.setViewAdapter(new SexAdapter(getActivity()));
	}

	private void initExitView() {
		// TODO Auto-generated method stub
		mExitConfirmView = mInflater.inflate(R.layout.exit_app_confirm, null);
		mComfirmExitBtn = (Button) mExitConfirmView
				.findViewById(R.id.confirm_exit_btn);
		mComfirmExitBtn.setOnClickListener(this);
	}

	// 关于
	private void initAboutView() {
		// TODO Auto-generated method stub
		mAboutView = mInflater.inflate(R.layout.about, null);
		TextView app_information = (TextView) mAboutView
				.findViewById(R.id.app_information);
		String myBlog = String.format("<a href=\"%s\">%s</a>",
				getString(R.string.my_blog_url),
				getString(R.string.my_blog_url));
		app_information.setText(Html.fromHtml(getString(
				R.string.app_information, myBlog)));
		app_information.setMovementMethod(LinkMovementMethod.getInstance());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		L.i("right onCreateView");
		View view = inflater.inflate(R.layout.main_right_fragment, container,
				false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		// title
		view.findViewById(R.id.ivTitleBtnLeft).setVisibility(View.GONE);
		view.findViewById(R.id.ivTitleBtnRigh).setVisibility(View.GONE);
		TextView title = (TextView) view.findViewById(R.id.ivTitleName);
		title.setText(R.string.right_title_name);

		mSlidingLayer = (SlidingLayer) view
				.findViewById(R.id.right_sliding_layer);

		mExitAppBtn = (Button) view.findViewById(R.id.exit_app);

		mMsgNotifySwitch = (SwitchButton) view
				.findViewById(R.id.message_notify_switch);
		mMsgSoundSwitch = (SwitchButton) view
				.findViewById(R.id.message_sound_switch);
		mShowHeadSwitch = (SwitchButton) view
				.findViewById(R.id.show_head_switch);
		mMsgNotifySwitch.setChecked(mSpUtil.getMsgNotify());
		mMsgSoundSwitch.setChecked(mSpUtil.getMsgSound());
		mShowHeadSwitch.setChecked(mSpUtil.getShowHead());

		mHead = (ImageView) view.findViewById(R.id.face);
		mHead.setImageResource(PushApplication.heads[mSpUtil.getHeadIcon()]);
		mNick = (TextView) view.findViewById(R.id.nick);
		mNick.setText(mSpUtil.getNick());

		mAccountSetting = view.findViewById(R.id.accountSetting);
		mMyProfile = view.findViewById(R.id.my_profile);
		mFaceJazzEffect = view.findViewById(R.id.face_jazz_effects);
		mAcountInfo = view.findViewById(R.id.set_about);
		mFeedBack = view.findViewById(R.id.set_feedback);
		setListener();

	}

	private void setListener() {
		// TODO Auto-generated method stub
		mSlidingLayer.setOnInteractListener(this);
		mMsgNotifySwitch.setOnCheckedChangeListener(this);
		mMsgSoundSwitch.setOnCheckedChangeListener(this);
		mShowHeadSwitch.setOnCheckedChangeListener(this);
		mAccountSetting.setOnClickListener(this);
		mMyProfile.setOnClickListener(this);
		mFaceJazzEffect.setOnClickListener(this);
		mAcountInfo.setOnClickListener(this);
		mFeedBack.setOnClickListener(this);
		mExitAppBtn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.exit_app:
			mSlidingLayer.removeAllViews();// 先移除所有的view,不然会报错
			if (!mSlidingLayer.isOpened()) {
				mSlidingLayer.addView(mExitConfirmView);
				mSlidingLayer.openLayer(true);
			}
			break;
		case R.id.confirm_exit_btn:
			if (mSlidingLayer.isOpened()) {
				mSlidingLayer.closeLayer(true);
			}
			getActivity().finish();
			break;
		case R.id.accountSetting:
			mSlidingLayer.removeAllViews();
			if (!mSlidingLayer.isOpened()) {
				mSlidingLayer.addView(mAcountSetView);
				mSlidingLayer.openLayer(true);
			}
			break;
		case R.id.my_profile:
			mSlidingLayer.removeAllViews();
			if (!mSlidingLayer.isOpened()) {
				mSlidingLayer.addView(mAcountSetView);
				mSlidingLayer.openLayer(true);
			}
			break;
		case R.id.face_jazz_effects:
			mSlidingLayer.removeAllViews();
			if (!mSlidingLayer.isOpened()) {
				mSlidingLayer.addView(mFaceEffectView);
				mSlidingLayer.openLayer(true);
			}
			break;
		case R.id.set_about:
			mSlidingLayer.removeAllViews();
			if (!mSlidingLayer.isOpened()) {
				mSlidingLayer.addView(mAboutView);
				mSlidingLayer.openLayer(true);
			}
			break;
		case R.id.set_feedback:
			mSlidingLayer.removeAllViews();
			if (!mSlidingLayer.isOpened()) {
				mSlidingLayer.addView(mFeedBackView);
				mSlidingLayer.openLayer(true);
			}
			break;
		case R.id.feed_back_btn:
			String content = mFeedBackET.getText().toString();
			if (!TextUtils.isEmpty(content)) {
				Intent intent = new Intent(Intent.ACTION_SENDTO);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_SUBJECT, "推聊Android客户端 - 信息反馈");
				intent.putExtra(Intent.EXTRA_TEXT, content);
				intent.setData(Uri.parse("mailto:way.ping.li@gmail.com"));
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getActivity().startActivity(intent);
				if (mSlidingLayer.isOpened()) {
					mSlidingLayer.closeLayer(true);
				}
			} else {
				T.showShort(getActivity(), "请输入一点点内容嘛！");
			}
			break;
		case R.id.face_effect_btn:
			mSpUtil.setFaceEffect(mFaceEffect.getCurrentItem());
			if (mSlidingLayer.isOpened()) {
				mSlidingLayer.closeLayer(true);
			}
			break;
		case R.id.acount_set_btn:
			String nick = mNickEt.getText().toString();
			if (TextUtils.isEmpty(nick)) {
				T.showShort(getActivity(), R.string.first_start_tips);
				return;
			}
			mSpUtil.setNick(nick);
			mSpUtil.setHeadIcon(mHeadWheel.getCurrentItem());
			mSpUtil.setTag(SexAdapter.SEXS[mSexWheel.getCurrentItem()]);
			User u = new User(mSpUtil.getUserId(), mSpUtil.getChannelId(),
					nick, mHeadWheel.getCurrentItem(), 0);
			mUserDB.addUser(u);
			new SendMsgAsyncTask(JsonUtil.createJsonMsg(
					System.currentTimeMillis(), "hi", mSpUtil.getTag()), "")
					.send();// 发送信息更新一下
			if (mSlidingLayer.isOpened()) {
				mSlidingLayer.closeLayer(true);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.message_notify_switch:
			mSpUtil.setMsgNotify(isChecked);
			break;
		case R.id.message_sound_switch:
			mSpUtil.setMsgSound(isChecked);
			break;
		case R.id.show_head_switch:
			mSpUtil.setShowHead(isChecked);
			break;
		default:
			break;
		}
	}

	@Override
	public void onOpen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		// mSlidingLayer.removeAllViews();
	}

	@Override
	public void onOpened() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClosed() {
		// TODO Auto-generated method stub
		mSlidingLayer.removeAllViews();
	}
}
