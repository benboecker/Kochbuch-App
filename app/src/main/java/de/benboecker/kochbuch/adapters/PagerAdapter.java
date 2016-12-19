package de.benboecker.kochbuch.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import de.benboecker.kochbuch.fragments.IngredientsFragment;
import de.benboecker.kochbuch.fragments.RecipeFragment;

/**
 * Created by Benni on 02.12.16.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

	public PagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {

		Fragment frag = null;

		switch (position) {
			case 0:
				frag = new RecipeFragment();
				break;
			case 1:
				frag = new RecipeFragment();
				break;
			case 2:
				frag = new IngredientsFragment();
				break;
		}

		return frag;
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {

		String title = "";

		switch (position) {
			case 0:
				title = "Rezept";
				break;
			case 1:
				title = "Zubereitung";
				break;
			case 2:
				title = "Zutaten";
				break;
		}

		return title;
	}
}
