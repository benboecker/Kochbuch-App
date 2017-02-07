package de.benboecker.kochbuch.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import de.benboecker.kochbuch.R;
import de.benboecker.kochbuch.activities.RecipeTabActivity;
import de.benboecker.kochbuch.model.FontManager;
import de.benboecker.kochbuch.model.Recipe;
import de.benboecker.kochbuch.view.FontTextView;
import io.realm.Realm;
import io.realm.RealmChangeListener;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment implements TextView.OnEditorActionListener, View.OnClickListener {

	Recipe recipe;
	EditText personsEditText;
	EditText durationEditText;
	ImageButton imageButton;
	FontTextView imageDescriptionTextView;
	CheckBox vegetarianCheckBox;
	CheckBox veganCheckBox;

	private Realm realm = Realm.getDefaultInstance();

	// Stores an images filepath while the picture is being taken. This way, we can discard the resulting file if the shot was cancelled.
	private String tempImageFilePath = "";

	protected static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1000;
	protected static final int PERMISSION_ACTIVITY_REQUEST_CODE = 1001;

	public RecipeFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_recipe, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		setupInterface();
	}

	@Override
	public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
		if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT) {
			realm.beginTransaction();
			switch (textView.getId()) {
				case R.id.persons:
					recipe.setNumberOfPersons(Integer.parseInt(textView.getText().toString()));
					break;
				case R.id.duration:
					recipe.setTime(Integer.parseInt(textView.getText().toString()));
					break;
				default: break;
			}
			realm.commitTransaction();
		}

		return false;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		try {
			RecipeTabActivity tabActivity = (RecipeTabActivity)context;
			recipe = tabActivity.getRecipe();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void onClick(View view) {
		if (view.equals(imageButton)) {
			tryToTakePhoto();
		} else if (view.equals(vegetarianCheckBox)) {
			vegetarianCheckBoxValueChanged(((CheckBox)view).isChecked());
		} else if (view.equals(veganCheckBox)) {
			veganCheckBoxValueChanged(((CheckBox)view).isChecked());
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		if (resultCode == RESULT_OK && requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			realm.beginTransaction();
			recipe.setImageFileName(tempImageFilePath);
			realm.copyToRealmOrUpdate(recipe);
			realm.commitTransaction();

			loadRecipeImage();
		} else if (resultCode == RESULT_CANCELED) {
			Toast.makeText(getContext(), "Kein Bild aufgenommen", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == PERMISSION_ACTIVITY_REQUEST_CODE) {
			tryToTakePhoto();
		}
	}


	public void setupInterface() {
		View view = this.getView();
		setHasOptionsMenu(true);
		Typeface font = FontManager.getInstance().getFont("fonts/IndieFlower.ttf");

		personsEditText = (EditText) view.findViewById(R.id.persons);
		durationEditText = (EditText) view.findViewById(R.id.duration);

		// Setting the typeface because a custom subclass can not be edited...
		personsEditText.setTypeface(font);
		durationEditText.setTypeface(font);

		personsEditText.setOnEditorActionListener(this);
		durationEditText.setOnEditorActionListener(this);

		imageButton = (ImageButton) view.findViewById(R.id.recipe_image);
		imageButton.setOnClickListener(this);

		imageDescriptionTextView = (FontTextView) view.findViewById(R.id.imageDescription);

		// Setting the typeface because a custom subclass does not display the actual checkbox.
		vegetarianCheckBox = (CheckBox) view.findViewById(R.id.vegetarianCheckBox);
		vegetarianCheckBox.setTypeface(font);
		vegetarianCheckBox.setOnClickListener(this);

		veganCheckBox = (CheckBox) view.findViewById(R.id.veganCheckBox);
		veganCheckBox.setTypeface(font);
		veganCheckBox.setOnClickListener(this);

		if (recipe != null) {
			personsEditText.setText(recipe.getNumberOfPersons() + "");
			durationEditText.setText(recipe.getTime() + "");

			vegetarianCheckBox.setChecked(recipe.isVegetarian());
			veganCheckBox.setChecked(recipe.isVegan());

			if (!recipe.isVegetarian()) {
				AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
				animation.setDuration(0);
				animation.setFillAfter(true);
				veganCheckBox.startAnimation(animation);
			}

			loadRecipeImage();
		}
	}


	private void loadRecipeImage() {
		Bitmap recipeImage = recipe.getImage((Activity) getContext());

		if (recipeImage != null) {
			imageButton.setImageBitmap(recipeImage);
			imageDescriptionTextView.setAlpha(0);
		}
	}


	private boolean hasStoragePermission() {
		if (Build.VERSION.SDK_INT >= 23) {
			int writePermission = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

			if (writePermission == PackageManager.PERMISSION_GRANTED) {
				return true;
			} else {

				String[] permissions = {
						android.Manifest.permission.WRITE_EXTERNAL_STORAGE
				};

				ActivityCompat.requestPermissions((RecipeTabActivity) getContext(), permissions, PERMISSION_ACTIVITY_REQUEST_CODE);

				return false;
			}
		} else {
			return true;
		}
	}


	private void tryToTakePhoto() {
		if (hasStoragePermission()) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			tempImageFilePath = "/image_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
			File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
			File imageFile = new File(storageDir + tempImageFilePath);

			Uri imageUri = Uri.fromFile(imageFile);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //set the image file name

			startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		}
	}


	private void vegetarianCheckBoxValueChanged(Boolean isChecked) {
		realm.beginTransaction();
		recipe.setVegetarian(isChecked);

		float from = isChecked ? 0.0f : 1.0f;
		float to = isChecked ? 1.0f : 0.0f;
		AlphaAnimation animation = new AlphaAnimation(from, to);
		animation.setDuration(500);
		animation.setFillAfter(true);
		veganCheckBox.startAnimation(animation);

		veganCheckBox.setEnabled(isChecked);

		if (!isChecked) {
			recipe.setVegan(isChecked);
			veganCheckBox.setChecked(isChecked);
		}

		realm.commitTransaction();
	}


	private void veganCheckBoxValueChanged(Boolean isChecked) {
		realm.beginTransaction();
		recipe.setVegan(isChecked);

		if (isChecked) {
			recipe.setVegetarian(isChecked);
			vegetarianCheckBox.setChecked(isChecked);
		}

		realm.commitTransaction();
	}
}
