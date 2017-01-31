package de.benboecker.kochbuch.model;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.util.Hashtable;

/**
 * Created by Benni on 24.01.17.
 */

public class FontHelper {
	public static final String TAG = "UiUtil";
	private static final Hashtable<String, SoftReference<Typeface>> fontCache = new Hashtable<String, SoftReference<Typeface>>();

	public static void setCustomFont(TextView textView, Context ctx, AttributeSet attrs, int[] attributeSet, int fontId) {
		TypedArray a = ctx.obtainStyledAttributes(attrs, attributeSet);
		String customFont = a.getString(fontId);
		setCustomFont(textView, ctx, customFont);
		a.recycle();
	}

	public static void setCustomFont(Button button, Context ctx, AttributeSet attrs, int[] attributeSet, int fontId) {
		TypedArray a = ctx.obtainStyledAttributes(attrs, attributeSet);
		String customFont = a.getString(fontId);
		setCustomFont(button, ctx, customFont);
		a.recycle();
	}

	private static boolean setCustomFont(TextView textView, Context ctx, String asset) {
		if (TextUtils.isEmpty(asset)) {
			return false;
		}

		Typeface tf = null;

		try {
			tf = getFont(ctx, asset);

			textView.setTypeface(tf);
		} catch (Exception e) {
			Log.e(TAG, "Could not get typeface: " + asset, e);

			return false;
		}

		return true;
	}

	private static boolean setCustomFont(Button button, Context ctx, String asset) {
		if (TextUtils.isEmpty(asset)) {
			return false;
		}

		Typeface tf = null;

		try {
			tf = getFont(ctx, asset);

			button.setTypeface(tf);
		} catch (Exception e) {
			Log.e(TAG, "Could not get typeface: " + asset, e);

			return false;
		}

		return true;
	}

	public static Typeface getFont(Context c, String name) {
		synchronized (fontCache) {
			if (fontCache.get(name) != null) {
				SoftReference<Typeface> ref = fontCache.get(name);

				if (ref.get() != null) {
					return ref.get();
				}
			}

			Typeface typeface = Typeface.createFromAsset(c.getAssets(), "fonts/" + name);
			fontCache.put(name, new SoftReference<Typeface>(typeface));

			return typeface;
		}
	}
}
