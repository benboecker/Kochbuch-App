package de.benboecker.kochbuch.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import de.benboecker.kochbuch.R;

/**
 * Created by Benni on 14.12.16.
 */

public class MultilineTextInputDialogFragment extends DialogFragment implements TextWatcher, DialogInterface.OnShowListener {

	public interface TextInputDialogListener {
		void onTextInput(String text);
		String getTextDialogTitle(MultilineTextInputDialogFragment fragment);
	}

	private MultilineTextInputDialogFragment.TextInputDialogListener listener;
	private EditText editText;
	private AlertDialog dialog;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View dialogView = inflater.inflate(R.layout.dialog_multiline_text_input, null);
		editText = (EditText) dialogView.findViewById(R.id.multiline_edit_text);
		editText.addTextChangedListener(this);

		builder.setView(dialogView)
				.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						String text = MultilineTextInputDialogFragment.this.editText.getText().toString();
						MultilineTextInputDialogFragment.this.listener.onTextInput(text);
					}
				})
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						MultilineTextInputDialogFragment.this.getDialog().cancel();
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

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			this.listener = (MultilineTextInputDialogFragment.TextInputDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement TextInputDialogListener");
		}
	}

	@Override
	public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

	@Override
	public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
		boolean noText = editText.getText().toString().equals("");
		dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(!noText);
	}

	@Override
	public void afterTextChanged(Editable editable) {}
}
