package de.benboecker.kochbuch.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.List;

import de.benboecker.kochbuch.R;
import de.benboecker.kochbuch.datasources.RecipeDataSource;
import de.benboecker.kochbuch.fragments.TextInputDialogFragment;
import de.benboecker.kochbuch.model.Ingredient;
import de.benboecker.kochbuch.model.Recipe;

/**
 * Created by Benni on 19.11.16.
 */

public class RecipeGridActivity extends FragmentActivity implements TextInputDialogFragment.TextInputDialogListener {

	private RecipeDataSource dataSource;
	private GridView gridView;
	private List<Recipe> recipes;
	private ArrayAdapter<Recipe> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe_list);

		this.dataSource = new RecipeDataSource(this);
		this.dataSource.open();

		this.setupInterface();

		this.recipes = this.dataSource.getAllRecipes();
		this.adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, this.recipes);
		this.gridView.setAdapter(adapter);
	}

	@Override
	protected void onResume() {
		this.dataSource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		this.dataSource.close();
		super.onPause();
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

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void setupInterface() {
		this.gridView = (GridView) this.findViewById(R.id.grid_view);
		this.gridView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}

			public void onItemClick(AdapterView<?> adapter, View v, int position){
				Recipe recipe = (Recipe) adapter.getItemAtPosition(position);


			}
		});

		Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
		this.setActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) this.findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				TextInputDialogFragment textInput = new TextInputDialogFragment();
				textInput.show(getFragmentManager(), "");
			}
		});
	}

	public void onTextInput(String text) {
		Recipe newRecipe = this.dataSource.createRecipe(text);
		this.adapter.add(newRecipe);
		this.adapter.notifyDataSetChanged();
	}



}
