package de.benboecker.kochbuch.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import javax.crypto.NullCipher;

import de.benboecker.kochbuch.R;
import de.benboecker.kochbuch.activities.RecipeTabActivity;
import de.benboecker.kochbuch.adapters.CookingStepAdapter;
import de.benboecker.kochbuch.model.CookingStep;
import de.benboecker.kochbuch.model.Recipe;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class CookingStepFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, MultilineTextInputDialogFragment.TextInputDialogListener {

	private ListView listView;
	private Recipe recipe;
	private CookingStepAdapter adapter;
	private Realm realm = Realm.getDefaultInstance();
	private long selectedCookingStepID = -1;


	public CookingStepFragment() {}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);

		return inflater.inflate(R.layout.fragment_ingredients, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();

		if (adapter == null) {
			setupData();
			setupInterface();
		}
	}
	@Override
	public void onClick(View view) {
		MultilineTextInputDialogFragment mltInputFragment = new MultilineTextInputDialogFragment();
		mltInputFragment.setListener(this);
		mltInputFragment.show(this.getFragmentManager(), "step");
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		CookingStep cookingStep = this.adapter.getItem(i);

		selectedCookingStepID = cookingStep.getId();

		MultilineTextInputDialogFragment dialogFragment = new MultilineTextInputDialogFragment();
		dialogFragment.setText(cookingStep.getDescription());
		dialogFragment.setListener(this);
		dialogFragment.show(getFragmentManager(), "cookingStep");
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		if (v.getId() == R.id.list_view) {
			menu.setHeaderTitle(R.string.options);
			menu.add(Menu.NONE, 0, 0, R.string.delete);
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);

		final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

		if (item.getItemId() == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
			builder.setMessage("Wirklich löschen?");
			builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {

					/*Ingredient ingredient = adapter.getItem(info.position);

					realm.beginTransaction();
					recipe.getIngredients().remove(ingredient);
					realm.commitTransaction();
					IngredientsFragment.this.adapter.notifyDataSetChanged();*/

				}
			}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {}
			});
			builder.create().show();
		}

		return true;
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
		listView = (ListView) view.findViewById(R.id.list_view);
		listView.setOnItemClickListener(this);

		// Live Reloading klappt sonst nicht
		if (adapter != null) {
			listView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}

		listView.invalidate();
		registerForContextMenu(listView);

		FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
		fab.setOnClickListener(CookingStepFragment.this);
	}


	private void setupData() {
		// Live Reloading klappt sonst nicht
		if (recipe != null) {
			adapter = new CookingStepAdapter(getContext(), recipe.getSteps());
		}
	}

	@Override
	public void onTextInput(String text) {

		realm.beginTransaction();
		CookingStep cookingStep;
		if (selectedCookingStepID == -1) {
			cookingStep = CookingStep.newCookingStep();
			recipe.getSteps().add(cookingStep);
		} else {
			cookingStep = realm.where(CookingStep.class).equalTo("id", selectedCookingStepID).findFirst();
		}

		cookingStep.setDescription(text);
		realm.copyToRealmOrUpdate(cookingStep);
		realm.commitTransaction();

		adapter.notifyDataSetChanged();
		selectedCookingStepID = -1;
	}
}
