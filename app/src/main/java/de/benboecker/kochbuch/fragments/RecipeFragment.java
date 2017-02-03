package de.benboecker.kochbuch.fragments;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.AndroidCharacter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.jar.Manifest;

import de.benboecker.kochbuch.R;
import de.benboecker.kochbuch.activities.RecipeTabActivity;
import de.benboecker.kochbuch.model.Recipe;
import io.realm.Realm;

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
	private Realm realm = Realm.getDefaultInstance();

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
		if (i == EditorInfo.IME_ACTION_DONE) {
			//saveData();
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
		tryToTakePhoto();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		if (resultCode == RESULT_OK && requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

			loadRecipeImage();

		} else if (resultCode == RESULT_CANCELED) {
			Toast.makeText(getContext(), "Kein Bild aufgenommen", Toast.LENGTH_SHORT);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == PERMISSION_ACTIVITY_REQUEST_CODE) {
			tryToTakePhoto();
		}
	}


	private void setupInterface() {
		View view = this.getView();
		setHasOptionsMenu(true);

		personsEditText = (EditText) view.findViewById(R.id.persons);
		durationEditText = (EditText) view.findViewById(R.id.duration);

		personsEditText.setOnEditorActionListener(this);
		durationEditText.setOnEditorActionListener(this);

		imageButton = (ImageButton) view.findViewById(R.id.placeholder);
		imageButton.setOnClickListener(this);

		if (recipe != null) {
			personsEditText.setText(recipe.getDefaultNumberOfPersons() + "");
			durationEditText.setText(recipe.getTime() + "");
			loadRecipeImage();
		}
	}


	private void loadRecipeImage() {
		File file = new File(Environment.getExternalStorageDirectory().getPath(), recipe.getImageFileName());
		Uri uri = Uri.fromFile(file);
		Bitmap bitmap;

		try {
			bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
			imageButton.setImageBitmap(bitmap);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private boolean hasStoragePermission() {
		if (Build.VERSION.SDK_INT >= 23) {
			int readPermission = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE);
			int writePermission = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

			if (readPermission == PackageManager.PERMISSION_GRANTED && writePermission == PackageManager.PERMISSION_GRANTED) {
				return true;
			} else {

				String[] permissions = {
						android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
						android.Manifest.permission.READ_EXTERNAL_STORAGE
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

			String fileName = "image_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
			File imageFile = new File(Environment.getExternalStorageDirectory(), fileName);

			realm.beginTransaction();
			recipe.setImageFileName(fileName);
			realm.commitTransaction();

			Uri imageUri = Uri.fromFile(imageFile);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //set the image file name

			startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		}
	}
}
