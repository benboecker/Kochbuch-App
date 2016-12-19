package de.benboecker.kochbuch.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import de.benboecker.kochbuch.R;
import de.benboecker.kochbuch.model.RealmIndex;
import de.benboecker.kochbuch.model.Recipe;
import io.realm.Realm;

public class RecipeActivity extends RealmActivity implements TextView.OnEditorActionListener {

	EditText recipeNameEditText;
	EditText personsEditText;
	EditText durationEditText;
	Recipe recipe;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe);

		setupData();
		setupInterface();
	}

	@Override
	public void onBackPressed() {
		if (saveData()) {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case android.R.id.home:
				return !saveData(); // Negated because 'return true' means 'override the default back functionality' -> 'return false' doesn't return
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
		if (i == EditorInfo.IME_ACTION_DONE) {
			saveData();
		}
		return false;
	}

	private void setupInterface() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setActionBar(toolbar);

		ActionBar actionBar = getActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		recipeNameEditText = (EditText) findViewById(R.id.recipe_name);
		personsEditText = (EditText) findViewById(R.id.persons);
		durationEditText = (EditText) findViewById(R.id.duration);

		recipeNameEditText.setOnEditorActionListener(this);
		personsEditText.setOnEditorActionListener(this);
		durationEditText.setOnEditorActionListener(this);

		if (recipe != null) {
			String name = recipe.getName();
			System.out.println(name);

			recipeNameEditText.setText(recipe.getName());
			personsEditText.setText(recipe.getDefaultNumberOfPersons() + "");
			durationEditText.setText(recipe.getTime() + "");
		}

		Button button = (Button) findViewById(R.id.button2);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (recipe != null) {
					Intent intent = new Intent(RecipeActivity.this, IngredientListActivity.class);
					intent.putExtra("id", recipe.getId());
					startActivity(intent);
				}
			}
		});
	}

	private void setupData() {
		Bundle extras = this.getIntent().getExtras();
		if (extras != null) {
			long recipeID = extras.getLong("id");
			recipe = realm.where(Recipe.class).equalTo("id", recipeID).findFirst();
		}
	}

	private boolean saveData() {
		if (recipeNameEditText.getText().toString() == "") {
			return (recipe == null);
		}

		realm.beginTransaction();
		recipe.setName(recipeNameEditText.getText().toString());
		recipe.setDefaultNumberOfPersons(Integer.parseInt(personsEditText.getText().toString()));
		recipe.setTime(Integer.parseInt(durationEditText.getText().toString()));
		realm.commitTransaction();

		return true;
	}

}
