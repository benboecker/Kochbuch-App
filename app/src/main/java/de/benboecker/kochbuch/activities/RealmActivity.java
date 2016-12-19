package de.benboecker.kochbuch.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import io.realm.Realm;

/**
 * Created by Benni on 07.12.16.
 */

public class RealmActivity extends Activity {
	protected Realm realm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		realm = Realm.getDefaultInstance();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		realm.close();
	}

}
