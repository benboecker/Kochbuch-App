package de.benboecker.kochbuch.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toolbar;

import de.benboecker.kochbuch.R;
import de.benboecker.kochbuch.adapters.PagerAdapter;
import de.benboecker.kochbuch.fragments.IngredientDialogFragment;
import de.benboecker.kochbuch.model.Ingredient;
import de.benboecker.kochbuch.model.Recipe;
import io.realm.Realm;

public class RecipeTabActivity extends AppCompatActivity implements IngredientDialogFragment.IngredientDialogListener {

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


		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setActionBar(toolbar);

		ActionBar actionBar = getActionBar();
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



	}


	private void setupInterface() {

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

	@Override
	public void onNewIngredient(Ingredient ingredient) {
		realm.beginTransaction();;
		recipe.getIngredients().add(ingredient);
		realm.commitTransaction();
	}
}
