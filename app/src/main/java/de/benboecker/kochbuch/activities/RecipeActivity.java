package de.benboecker.kochbuch.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import de.benboecker.kochbuch.R;
import de.benboecker.kochbuch.datasources.RecipeDataSource;
import de.benboecker.kochbuch.model.Recipe;

public class RecipeActivity extends AppCompatActivity {

	EditText recipeNameEditText;
	Recipe recipe;
	RecipeDataSource dataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe);

		this.dataSource = new RecipeDataSource(this);
		this.dataSource.open();

		Bundle extras = this.getIntent().getExtras();
		if (extras != null) {
			long id = extras.getLong("id");
			this.recipe = this.dataSource.getRecipe(id);
		}

		setupInterface();
		//showKeyboardFor(recipeNameEditText);
	}

	private void setupInterface() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		recipeNameEditText = (EditText) findViewById(R.id.recipe_name);
		recipeNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					if (recipe == null) {
						RecipeActivity.this.recipe = RecipeActivity.this.dataSource.createRecipe(textView.getText().toString());
					} else {
						String oldName = recipe.getName();
						recipe.setName(textView.getText().toString());
						boolean success = RecipeActivity.this.dataSource.updateRecipe(recipe);

						if (success) {
							System.out.println("Successfully updated " + oldName + " to " + recipe.getName());
						}
					}

					return true;
				}
				return false;
			}
		});

		if (recipe != null) {
			recipeNameEditText.setText(recipe.getName());
		}
	}

	private void showKeyboardFor(EditText editText) {
		editText.requestFocus();
		InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}
}
