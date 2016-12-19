package de.benboecker.kochbuch.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

import de.benboecker.kochbuch.R;
import de.benboecker.kochbuch.adapters.CookingStepAdapter;
import de.benboecker.kochbuch.adapters.IngredientAdapter;
import de.benboecker.kochbuch.fragments.IngredientDialogFragment;
import de.benboecker.kochbuch.fragments.MultilineTextInputDialogFragment;
import de.benboecker.kochbuch.model.CookingStep;
import de.benboecker.kochbuch.model.Ingredient;
import de.benboecker.kochbuch.model.Recipe;

/**
 * Created by Benni on 14.12.16.
 */

public class CookingStepListActivity extends RealmActivity implements AdapterView.OnItemClickListener, View.OnClickListener, IngredientDialogFragment.IngredientDialogListener {

	ListView listView;
	Recipe recipe;
	CookingStepAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cooking_step_list);
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (adapter == null) {
			setupData();
			setupInterface();
		}
	}


	@Override
	public void onClick(View view) {
		MultilineTextInputDialogFragment dialogFragment = new MultilineTextInputDialogFragment();
		dialogFragment.show(getFragmentManager(), "cooking_step");
	}

	@Override
	public void onNewIngredient(Ingredient ingredient) {
		recipe.getIngredients().add(ingredient);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		CookingStep step = this.adapter.getItem(i);

		/*IngredientDialogFragment ingredientDialogFragment = new IngredientDialogFragment();
		ingredientDialogFragment.setIngredient(ingredient);
		ingredientDialogFragment.show(getFragmentManager(), "ingredient");*/
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

		final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

		if (item.getItemId() == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Wirklich löschen?");
			builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {

					CookingStep step = adapter.getItem(info.position);

					realm.beginTransaction();
					recipe.getSteps().remove(step);
					realm.commitTransaction();
					CookingStepListActivity.this.adapter.notifyDataSetChanged();

				}
			}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
				}
			});
			builder.create().show();
		}

		return true;
	}


	private void setupInterface() {
		listView = (ListView) this.findViewById(R.id.list_view);
		listView.setOnItemClickListener(this);
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		listView.invalidate();
		registerForContextMenu(listView);

		Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
		this.setActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) this.findViewById(R.id.fab);
		fab.setOnClickListener(this);
	}


	private void setupData() {
		Bundle extras = this.getIntent().getExtras();
		if (extras != null) {
			long recipeID = extras.getLong("id");
			recipe = realm.where(Recipe.class).equalTo("id", recipeID).findFirst();
			adapter = new CookingStepAdapter(this, recipe.getSteps());
		}
	}

}