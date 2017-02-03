package de.benboecker.kochbuch.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import de.benboecker.kochbuch.R;
import de.benboecker.kochbuch.model.FontManager;

/**
 * Created by Benni on 31.01.17.
 */

public class FontTextView extends TextView {

	public FontTextView(Context context) {
		this(context, null);
	}

	public FontTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FontTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		if (isInEditMode()) {
			return;
		}

		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);

		if (ta != null) {
			//String fontAsset = "fonts/Barrio-Regular.ttf";//ta.getString(R.styleable.FontTextView_typefaceAsset);
			String fontAsset = ta.getString(R.styleable.FontTextView_typefaceAsset);

			Typeface tf = FontManager.getInstance().getFont(fontAsset);
			int style = Typeface.NORMAL;

			if (getTypeface() != null) {
				style = getTypeface().getStyle();
			}
			if (tf != null) {
				setTypeface(tf, style);
			} else {
				Log.d("FontTextView", String.format("Could not create a font from asset: %s", fontAsset));
			}
		}
	}
}
