package de.benboecker.kochbuch.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.benboecker.kochbuch.R;
import de.benboecker.kochbuch.model.Ingredient;
import de.benboecker.kochbuch.model.RealmHelper;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Benni on 08.12.16.
 */

public class IngredientDialogFragment extends DialogFragment implements TextWatcher, DialogInterface.OnShowListener {

	public interface IngredientDialogListener {
		void onNewIngredient(Ingredient ingredient);
		void onDismiss();
	}

	private IngredientDialogListener listener;
	private Ingredient ingredient = null;
	private AutoCompleteTextView ingredientTextView;
	private EditText quantityTextView;
	private Spinner unitSpinner;
	private AlertDialog dialog;
	private Realm realm;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		realm = Realm.getDefaultInstance();
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View dialogView = inflater.inflate(R.layout.dialog_ingredient, null);
		ingredientTextView = (AutoCompleteTextView) dialogView.findViewById(R.id.ingredient);
		quantityTextView = (EditText) dialogView.findViewById(R.id.quantity);
		unitSpinner = (Spinner) dialogView.findViewById(R.id.unit);
		ingredientTextView.addTextChangedListener(this);

		builder.setView(dialogView)
				.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						IngredientDialogFragment.this.saveButtonPressed();
					}
				})
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						IngredientDialogFragment.this.getDialog().cancel();
					}
				});

		setAutocompleteDataSource();
		dialog = builder.create();
		dialog.setOnShowListener(this);
		return dialog;
	}

	@Override
	public void onShow(DialogInterface dialog) {
		this.dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

		if (ingredient != null) {
			ingredientTextView.setText(ingredient.getName());
			quantityTextView.setText(""+ingredient.getQuantity());

			List<String> units = Arrays.asList(getResources().getStringArray(R.array.units));
			unitSpinner.setSelection(units.indexOf(ingredient.getUnit()));
		}

	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		dialog.setTitle("Zutat bearbeiten");

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

	@Override
	public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
		boolean noText = ingredientTextView.getText().toString().equals("");
		dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(!noText);
	}

	@Override
	public void afterTextChanged(Editable editable) {}


	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}


	private void saveButtonPressed() {
		String ingredientName = IngredientDialogFragment.this.ingredientTextView.getText().toString();
		String quantityString = IngredientDialogFragment.this.quantityTextView.getText().toString();
		String unit = IngredientDialogFragment.this.unitSpinner.getSelectedItem().toString();
		long quantity;
		try {
			quantity = Long.parseLong(quantityString);
		} catch (Exception e) {
			quantity = 0;
		}

		if (ingredient == null) {
			ingredient = Ingredient.newIngredient();
			listener.onNewIngredient(ingredient);
		}

		realm.beginTransaction();

		ingredient.setName(ingredientName);
		ingredient.setQuantity(quantity);
		ingredient.setUnit(unit);
		realm.copyToRealmOrUpdate(ingredient);

		realm.commitTransaction();

		listener.onDismiss();
	}


	private void setAutocompleteDataSource() {
		ArrayList<String> suggestions = new ArrayList();
		RealmResults<Ingredient> savedIngredients = realm.where(Ingredient.class).findAllSorted("name");
		List<String> defaultIngrdients = Arrays.asList(getResources().getStringArray(R.array.ingredients));

		for (Ingredient ingredient : savedIngredients) {
			if (!suggestions.contains(ingredient.getName())) {
				suggestions.add(ingredient.getName());
			}
		}

		for (String ingredient : defaultIngrdients) {
			if (!suggestions.contains(ingredient)) {
				suggestions.add(ingredient);
			}
		}

		ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, suggestions);
		ingredientTextView.setAdapter(adapter);
	}


	public void setListener(IngredientDialogListener listener) {
		this.listener = listener;
	}
}
