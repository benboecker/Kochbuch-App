package de.benboecker.kochbuch.model;

import io.realm.Realm;

/**
 * Created by Benni on 07.12.16.
 */

public class RealmIndex {
	static public long getNextID(Class c) {
		Realm realm = Realm.getDefaultInstance();

		Number id = realm.where(c).max("id");
		if (id != null) {
			return id.longValue() + 1;
		} else {
			return 0;
		}
	}
	static public long getNextID(Class c, String identifierField) {
		Realm realm = Realm.getDefaultInstance();

		Number id = realm.where(c).max(identifierField);
		if (id != null) {
			return id.longValue() + 1;
		} else {
			return 0;
		}
	}
}

