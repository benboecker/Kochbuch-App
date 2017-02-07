package de.benboecker.kochbuch.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import de.benboecker.kochbuch.R;
import de.benboecker.kochbuch.model.FontManager;

/**
 * Created by Benni on 06.02.17.
 */

public class FontEditText extends EditText {

	public FontEditText(Context context) {
		this(context, null);
	}

	public FontEditText(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FontEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);

		if (ta != null) {
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
