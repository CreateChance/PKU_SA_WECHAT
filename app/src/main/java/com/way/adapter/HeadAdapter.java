package com.way.adapter;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.way.app.PushApplication;
import com.way.push.R;
import com.way.wheel.adapters.AbstractWheelAdapter;

/**
 * /** HeadAdapter
 */
public class HeadAdapter extends AbstractWheelAdapter {
	// Image size
	final int IMAGE_WIDTH = 50;
	final int IMAGE_HEIGHT = 50;

	// Slot machine symbols
	private final int items[] = PushApplication.heads;

	// Cached images
	private List<SoftReference<Bitmap>> images;

	private LayoutInflater inflater;
	private Context context;

	/**
	 * Constructor
	 */
	public HeadAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		images = new ArrayList<SoftReference<Bitmap>>(items.length);
		for (int id : items) {
			images.add(new SoftReference<Bitmap>(loadImage(id)));
		}
	}

	/**
	 * Loads image from resources
	 */
	private Bitmap loadImage(int id) {
		Bitmap bitmap = BitmapFactory
				.decodeResource(context.getResources(), id);
		Bitmap scaled = Bitmap.createScaledBitmap(bitmap, IMAGE_WIDTH,
				IMAGE_HEIGHT, true);
		bitmap.recycle();
		return scaled;
	}

	@Override
	public int getItemsCount() {
		return items.length;
	}

	@Override
	public View getItem(int index, View cachedView, ViewGroup parent) {
		if (cachedView == null) {
			cachedView = inflater.inflate(R.layout.head_select_layout, null);
		}
		ImageView img = (ImageView) cachedView.findViewById(R.id.head);
		SoftReference<Bitmap> bitmapRef = images.get(index);
		Bitmap bitmap = bitmapRef.get();
		if (bitmap == null) {
			bitmap = loadImage(items[index]);
			images.set(index, new SoftReference<Bitmap>(bitmap));
		}
		img.setImageBitmap(bitmap);

		return cachedView;
	}
}
