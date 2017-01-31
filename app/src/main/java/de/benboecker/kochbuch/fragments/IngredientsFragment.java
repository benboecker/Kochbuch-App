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

import de.benboecker.kochbuch.R;
import de.benboecker.kochbuch.activities.RecipeTabActivity;
import de.benboecker.kochbuch.adapters.IngredientAdapter;
import de.benboecker.kochbuch.model.Ingredient;
import de.benboecker.kochbuch.model.Recipe;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, IngredientDialogFragment.IngredientDialogListener {

	private ListView listView;
	private Recipe recipe;
	private IngredientAdapter adapter;
	private Realm realm = Realm.getDefaultInstance();


	public IngredientsFragment() {}


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
		IngredientDialogFragment ingredientDialogFragment = new IngredientDialogFragment();
		ingredientDialogFragment.setListener(this);

		ingredientDialogFragment.show(this.getFragmentManager(), "ingredient");
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		Ingredient ingredient = this.adapter.getItem(i);

		IngredientDialogFragment ingredientDialogFragment = new IngredientDialogFragment();
		ingredientDialogFragment.setIngredient(ingredient);
		ingredientDialogFragment.setListener(this);
		ingredientDialogFragment.show(getFragmentManager(), "ingredient");
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

					Ingredient ingredient = adapter.getItem(info.position);

					realm.beginTransaction();
					recipe.getIngredients().remove(ingredient);
					realm.commitTransaction();
					IngredientsFragment.this.adapter.notifyDataSetChanged();

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

	@Override
	public void onNewIngredient(Ingredient ingredient) {
		realm.beginTransaction();;
		recipe.getIngredients().add(ingredient);
		realm.commitTransaction();

		adapter.notifyDataSetChanged();
	}

	public void onDismiss() {
		adapter.notifyDataSetChanged();
	}

	private void setupInterface() {
		View view = this.getView();
		listView = (ListView) view.findViewById(R.id.list_view);
		listView.setOnItemClickListener(this);
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		listView.invalidate();
		registerForContextMenu(listView);

		FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
		fab.setOnClickListener(IngredientsFragment.this);
	}

	private void setupData() {
		adapter = new IngredientAdapter(this.getContext(), recipe.getIngredients());
	}
}
