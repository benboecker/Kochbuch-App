package de.benboecker.kochbuch.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import de.benboecker.kochbuch.R;
import de.benboecker.kochbuch.activities.RecipeTabActivity;
import de.benboecker.kochbuch.model.Recipe;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment implements TextView.OnEditorActionListener  {

	Recipe recipe;
	EditText personsEditText;
	EditText durationEditText;

	public RecipeFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_recipe, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		setupInterface();
	}

	@Override
	public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
		if (i == EditorInfo.IME_ACTION_DONE) {
			//saveData();
		}
		return false;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		try {
			RecipeTabActivity tabActivity = (RecipeTabActivity)context;
			recipe = tabActivity.getRecipe();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void setupInterface() {
		View view = this.getView();
		setHasOptionsMenu(true);

		personsEditText = (EditText) view.findViewById(R.id.persons);
		durationEditText = (EditText) view.findViewById(R.id.duration);

		personsEditText.setOnEditorActionListener(this);
		durationEditText.setOnEditorActionListener(this);

		if (recipe != null) {
			personsEditText.setText(recipe.getDefaultNumberOfPersons() + "");
			durationEditText.setText(recipe.getTime() + "");
		}

	}



}
