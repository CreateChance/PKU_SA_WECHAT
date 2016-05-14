package com.way.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.way.push.R;
import com.way.wheel.adapters.AbstractWheelTextAdapter;

/**
 * Adapter for sex
 */
public class SexAdapter extends AbstractWheelTextAdapter {
	// names
	public static final String SEXS[] = new String[] { "美女", "帅哥", "人妖" };
	// flags
	public static final int FLAGS[] = new int[] { R.drawable.female, R.drawable.male,
			R.drawable.nomale };

	/**
	 * Constructor
	 */
	public SexAdapter(Context context) {
		super(context, R.layout.sex_select_layout, NO_RESOURCE);

		setItemTextResource(R.id.sex_name);
	}

	@Override
	public View getItem(int index, View cachedView, ViewGroup parent) {
		View view = super.getItem(index, cachedView, parent);
		ImageView img = (ImageView) view.findViewById(R.id.flag);
		img.setImageResource(FLAGS[index]);
		return view;
	}

	@Override
	public int getItemsCount() {
		return SEXS.length;
	}

	@Override
	protected CharSequence getItemText(int index) {
		return SEXS[index];
	}
}
