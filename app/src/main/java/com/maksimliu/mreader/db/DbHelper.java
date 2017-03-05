package com.maksimliu.mreader.db;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by MaksimLiu on 2017/3/4.
 */

public class DbHelper {

    private static volatile DbHelper INSTANCE;

    private static Realm realm;


    public DbHelper() {

        realm = Realm.getDefaultInstance();

    }

    public static DbHelper getInstance() {

        if (INSTANCE == null) {
            synchronized (DbHelper.class) {

                if (INSTANCE == null) {
                    return new DbHelper();
                }
            }
        }

        return INSTANCE;
    }


    public static void save(RealmObject object) {

        realm.beginTransaction();
        realm.copyToRealm(object);
        realm.commitTransaction();

    }
}
