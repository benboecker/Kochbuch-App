package de.benboecker.kochbuch.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.benboecker.kochbuch.R;
import de.benboecker.kochbuch.model.Recipe;

/**
 * Created by Benni on 07.12.16.
 */

public class RecipeAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private Context context;
	private List<Recipe> recipes = null;

	public RecipeAdapter(Context context) {
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(List<Recipe> details) {
		this.recipes = details;
	}

	public int getCount() {
		if (recipes == null) {
			return 0;
		}
		return recipes.size();
	}

	public Recipe getItem(int position) {
		if (recipes == null || recipes.get(position) == null) {
			return null;
		}
		return recipes.get(position);
	}

	public long getItemId(int i) {
		return i;
	}

	public View getView(int position, View currentView, ViewGroup parent) {
		if (currentView == null) {
			currentView = inflater.inflate(R.layout.grid_cell_recipe, parent, false);
		}

		Recipe recipe = recipes.get(position);

		if (recipe != null) {
			TextView recipeNameTextView = (TextView) currentView.findViewById(R.id.recipe_name);
			recipeNameTextView.setText(recipe.getName());
			recipeNameTextView.setBackgroundResource(R.color.transparentBlack);
			Bitmap recipeImage = recipe.getImage((Activity) context);

			if (recipeImage != null) {
				ImageView recipeImageView = (ImageView) currentView.findViewById(R.id.recipe_image);
				recipeImageView.setImageBitmap(recipeImage);
			}
		}

		return currentView;
	}
}