package de.benboecker.kochbuch.model;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Benni on 07.12.16.
 */

public class KochbuchApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		Realm.init(this);
		RealmConfiguration config = new RealmConfiguration.Builder()
				.name("kochbuch.realm")
				.build();
		Realm.setDefaultConfiguration(config);
	}
}
