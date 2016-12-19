package de.benboecker.kochbuch.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import de.benboecker.kochbuch.R;
import de.benboecker.kochbuch.adapters.PagerAdapter;

public class RecipeTabActivity extends AppCompatActivity {

	ViewPager pager;
	TabLayout tabLayout;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe_tab);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ActionBar actionBar = getSupportActionBar();
		//actionBar.setNavigationMode();

		pager = (ViewPager) findViewById(R.id.view_pager);
		tabLayout = (TabLayout) findViewById(R.id.tab_layout);


		// Fragment manager to add fragment in viewpager we will pass object of Fragment manager to adpater class.
		FragmentManager manager=getSupportFragmentManager();

		//object of PagerAdapter passing fragment manager object as a parameter of constructor of PagerAdapter class.
		PagerAdapter adapter=new PagerAdapter(manager);

		//set Adapter to view pager
		pager.setAdapter(adapter);

		//set tablayout with viewpager
		tabLayout.setupWithViewPager(pager);

		// adding functionality to tab and viewpager to manage each other when a page is changed or when a tab is selected
		pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

		//Setting tabs from adpater
		tabLayout.setTabsFromPagerAdapter(adapter);



	}

}
