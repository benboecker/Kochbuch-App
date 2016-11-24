package de.benboecker.kochbuch.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import de.benboecker.kochbuch.R;

/**
 * Created by Benni on 19.11.16.
 */

public class TextInputDialogFragment extends DialogFragment {

	public interface TextInputDialogListener {
		void onTextInput(String text);
	}

	TextInputDialogListener listener;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View dialogView = inflater.inflate(R.layout.dialog_text_input, null);

		builder.setView(dialogView)
				.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						EditText valueView = (EditText) dialogView.findViewById(R.id.text_input);
						TextInputDialogFragment.this.listener.onTextInput(valueView.getText().toString());
					}
				})
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						TextInputDialogFragment.this.getDialog().cancel();
					}
				});

		return builder.create();
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
}
