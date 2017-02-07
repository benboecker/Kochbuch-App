package de.benboecker.kochbuch.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.text.AndroidCharacter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import de.benboecker.kochbuch.R;

/**
 * Created by Benni on 19.11.16.
 */

public class TextInputDialogFragment extends DialogFragment implements TextWatcher, DialogInterface.OnShowListener {

	public interface TextInputDialogListener {
		void onTextInput(String text);
		String getTextDialogTitle(TextInputDialogFragment fragment);
		String getDefaultText(TextInputDialogFragment fragment);
	}

	private TextInputDialogListener listener;
	private AutoCompleteTextView editText;
	private AlertDialog dialog;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View dialogView = inflater.inflate(R.layout.dialog_text_input, null);
		editText = (AutoCompleteTextView) dialogView.findViewById(R.id.text_input);
		editText.addTextChangedListener(this);

		builder.setView(dialogView)
				.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						String text = TextInputDialogFragment.this.editText.getText().toString();
						TextInputDialogFragment.this.listener.onTextInput(text);
					}
				})
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						TextInputDialogFragment.this.getDialog().cancel();
					}
				});

		dialog = builder.create();
		dialog.setOnShowListener(this);
		return dialog;
	}

	@Override
	public void onShow(DialogInterface dialog) {
		this.dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		dialog.setTitle(listener.getTextDialogTitle(this));
		editText.setText(listener.getDefaultText(this));

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			this.listener = (TextInputDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement TextInputDialogListener");
		}
	}

	@Override
	public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

	@Override
	public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
		boolean noText = editText.getText().toString().equals("");
		Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
		if (button != null) {
			button.setEnabled(!noText);
		}
	}

	@Override
	public void afterTextChanged(Editable editable) {

	}
}
