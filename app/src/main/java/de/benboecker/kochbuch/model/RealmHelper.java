package de.benboecker.kochbuch.model;

import io.realm.Realm;
import io.realm.RealmObjectSchema;

/**
 * Created by Benni on 19.12.16.
 */

public class RealmHelper<C> {
	public C getNew(String identifier) {
		C newRealmObject = null;
		Realm realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		//newRealmObject = realm.createObject(C.class, RealmHelper.getNextID(C.class, identifier));
		realm.commitTransaction();

		return newRealmObject;
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
