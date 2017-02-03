package de.benboecker.kochbuch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.benboecker.kochbuch.R;
import de.benboecker.kochbuch.model.CookingStep;

/**
 * Created by Benni on 14.12.16.
 */

public class CookingStepAdapter extends ArrayAdapter {
	private Context context;
	private List<CookingStep> steps;

	public CookingStepAdapter(Context context, List<CookingStep> steps) {
		super(context, android.R.layout.simple_list_item_2);
		this.steps = steps;
		this.context = context;
	}

	@Override
	public int getCount() {
		return steps.size();
	}

	@Override
	public CookingStep getItem(int position) {
		return steps.get(position);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_cell_cooking_step, parent, false);
		}

		CookingStep step = getItem(position);

		if (step != null) {
			TextView cookingStepTextView = (TextView) convertView.findViewById(R.id.step);
			TextView numberTextView = (TextView) convertView.findViewById(R.id.number);

			cookingStepTextView.setText(step.getDescription());
			numberTextView.setText((position + 1) + ".");
		}

		return convertView;
	}
}
