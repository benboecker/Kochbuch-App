package de.benboecker.kochbuch.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.List;

import de.benboecker.kochbuch.R;
import de.benboecker.kochbuch.datasources.IngredientDataSource;
import de.benboecker.kochbuch.model.Ingredient;

public class IngredientListActivity extends FragmentActivity {

	private IngredientDataSource dataSource;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ingedient_list);

		this.dataSource = new IngredientDataSource(this);
		this.dataSource.open();

		this.setupInterface();

		List<Ingredient> values =this.dataSource.getAllIngredients();
		ArrayAdapter<Ingredient> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, values);
		this.listView.setAdapter(adapter);
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
		this.listView = (ListView) this.findViewById(R.id.list_view);

		Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
		this.setActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) this.findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
			IngredientListActivity.this.dataSource.createIngredient("Hackfleisch");
			}
		});
	}
}
