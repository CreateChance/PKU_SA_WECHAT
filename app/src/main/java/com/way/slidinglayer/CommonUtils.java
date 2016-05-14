package com.way.slidinglayer;

import java.util.Random;

/**
 * Common Utils for Android.
 * 
 */
public class CommonUtils {

	private static Random mRandom;

	/**
	 * Get a random boolean
	 */
	public static boolean getNextRandomBoolean() {

		if (mRandom == null) {
			mRandom = new Random();
		}

		return mRandom.nextBoolean();
	}

}