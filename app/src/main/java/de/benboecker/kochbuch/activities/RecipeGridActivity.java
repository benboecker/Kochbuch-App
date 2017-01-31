package de.benboecker.kochbuch.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toolbar;

import java.util.List;

import de.benboecker.kochbuch.R;
import de.benboecker.kochbuch.fragments.TextInputDialogFragment;
import de.benboecker.kochbuch.model.Recipe;
import de.benboecker.kochbuch.adapters.RecipeAdapter;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Benni on 19.11.16.
 */

public class RecipeGridActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener, TextInputDialogFragment.TextInputDialogListener {

	private GridView gridView;
	private RecipeAdapter adapter;
	protected Realm realm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		realm = Realm.getDefaultInstance();

		setContentView(R.layout.activity_recipe_grid);
	}

	@Override
	protected void onResume() {
		super.onResume();
		realm = Realm.getDefaultInstance();

		if (adapter == null) {
			setupData();
			setupInterface();
		}
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		realm.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_ingedient_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		switch (item.getItemId()) {
			// Respond to the action bar's Up/Home button
			case R.id.action_settings:
				return true;
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		if (v.getId() == R.id.grid_view) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

			menu.setHeaderTitle(R.string.options);
			menu.add(Menu.NONE, 0, 0, R.string.share);
			menu.add(Menu.NONE, 1, 1, R.string.delete);
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);

		final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

		if (item.getItemId() == 0) {
			System.out.println("Menu item 0");
		}
		if (item.getItemId() == 1) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Wirklich löschen?");
			builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							Recipe recipe = adapter.getItem(info.position);
							realm.beginTransaction();
							recipe.deleteFromRealm();
							realm.commitTransaction();
							RecipeGridActivity.this.adapter.notifyDataSetChanged();
						}
					})
					.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {

						}
					});
			builder.create().show();
		}

		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		Intent recipeIntent = new Intent(RecipeGridActivity.this, RecipeTabActivity.class);
		recipeIntent.putExtra("id", RecipeGridActivity.this.adapter.getItem(i).getId());
		RecipeGridActivity.this.startActivity(recipeIntent);
	}

	@Override
	public void onClick(View view) {
		TextInputDialogFragment textInput = new TextInputDialogFragment();
		textInput.show(getFragmentManager(), "recipe");
	}

	@Override
	public void onTextInput(String text) {
		Recipe recipe = Recipe.newRecipe();
		realm.beginTransaction();
		recipe.setName(text);
		realm.commitTransaction();

		Intent recipeIntent = new Intent(RecipeGridActivity.this, RecipeTabActivity.class);
		recipeIntent.putExtra("id", recipe.getId());
		RecipeGridActivity.this.startActivity(recipeIntent);
	}

	@Override
	public List<String> getAutoCompleteList() {
		return null;
	}


	public String getTextDialogTitle(TextInputDialogFragment fragment) {
		return "Neues Rezept";
	}


	private void setupInterface() {
		gridView = (GridView) this.findViewById(R.id.grid_view);
		gridView.setOnItemClickListener(RecipeGridActivity.this);
		gridView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		gridView.invalidate();
		registerForContextMenu(gridView);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(RecipeGridActivity.this);
	}


	private void setupData() {
		RealmResults<Recipe> results1 = realm.where(Recipe.class).findAllSorted("name");
		adapter = new RecipeAdapter(this);
		adapter.setData(results1);
	}
}
