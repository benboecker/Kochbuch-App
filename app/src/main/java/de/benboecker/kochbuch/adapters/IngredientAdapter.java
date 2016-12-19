package de.benboecker.kochbuch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.util.ArrayList;
import java.util.List;

import de.benboecker.kochbuch.R;
import de.benboecker.kochbuch.model.Ingredient;
import de.benboecker.kochbuch.model.Recipe;

/**
 * Created by Benni on 07.12.16.
 */

public class IngredientAdapter extends ArrayAdapter {
	private Context context;
	private List<Ingredient> ingredients;

	public IngredientAdapter(Context context, List<Ingredient> ingredients) {
		super(context, android.R.layout.simple_list_item_2);
		this.ingredients = ingredients;
		this.context = context;
	}

	@Override
	public int getCount() {
		return ingredients.size();
	}

	@Override
	public Ingredient getItem(int position) {
		return ingredients.get(position);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.row_ingredient, parent, false);
		}

		Ingredient ingredient = getItem(position);

		if (ingredient != null) {
			TextView ingredientNameTextView = (TextView) convertView.findViewById(R.id.text1);
			TextView quantityTextView = (TextView) convertView.findViewById(R.id.text2);

			ingredientNameTextView.setText(ingredient.getName());
			if (ingredient.getQuantity() > 0) {
				quantityTextView.setText(ingredient.getQuantity() + " " + ingredient.getUnit());
			}
		}

		return convertView;
	}
}
