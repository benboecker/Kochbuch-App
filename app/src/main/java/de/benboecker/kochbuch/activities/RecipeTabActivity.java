package de.benboecker.kochbuch.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar ;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import de.benboecker.kochbuch.R;
import de.benboecker.kochbuch.adapters.PagerAdapter;
import de.benboecker.kochbuch.fragments.RecipeFragment;
import de.benboecker.kochbuch.fragments.TextInputDialogFragment;
import de.benboecker.kochbuch.model.Recipe;
import io.realm.Realm;

public class RecipeTabActivity extends AppCompatActivity implements TextInputDialogFragment.TextInputDialogListener {

	private Recipe recipe;
	private Realm realm = Realm.getDefaultInstance();

	private ViewPager pager;
	private TabLayout tabLayout;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe_tab);

		setupData();
		setupInterface();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_recipe, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		switch (id) {
			case R.id.share:
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_TEXT, recipe.getShareString());
				sendIntent.setType("text/plain");
				this.startActivity(sendIntent);
				break;
			case R.id.rename:
				TextInputDialogFragment textInput = new TextInputDialogFragment();
				textInput.show(getFragmentManager(), "recipe");
				break;
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTextInput(String text) {
		realm.beginTransaction();
		recipe.setName(text);
		realm.commitTransaction();

		setTitle(recipe.getName());
	}

	@Override
	public String getTextDialogTitle(TextInputDialogFragment fragment) {
		return getResources().getString(R.string.recipe_name);
	}

	@Override
	public String getDefaultText(TextInputDialogFragment fragment) {
		return recipe.getName();
	}


	private void setupInterface() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		pager = (ViewPager) findViewById(R.id.view_pager);
		tabLayout = (TabLayout) findViewById(R.id.tab_layout);

		// Fragment manager to add fragment in viewpager we will pass object of Fragment manager to adpater class.
		FragmentManager manager = this.getSupportFragmentManager();

		//object of PagerAdapter passing fragment manager object as a parameter of constructor of PagerAdapter class.
		PagerAdapter adapter = new PagerAdapter(manager);

		//set Adapter to view pager
		pager.setAdapter(adapter);

		//set tablayout with viewpager
		tabLayout.setupWithViewPager(pager);

		// adding functionality to tab and viewpager to manage each other when a page is changed or when a tab is selected
		pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

		//Setting tabs from adpater
		tabLayout.setTabsFromPagerAdapter(adapter);

		setTitle(recipe.getName());
	}


	private void setupData() {
		Bundle extras = this.getIntent().getExtras();
		if (extras != null) {
			long recipeID = extras.getLong("id");
			recipe = realm.where(Recipe.class).equalTo("id", recipeID).findFirst();
		}
	}


	public Recipe getRecipe() {
		return recipe;
	}

}
