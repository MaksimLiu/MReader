package com.maksimliu.mreader.db;

import com.maksimliu.mreader.bean.GankBean;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.Sort;

/**
 * Created by MaksimLiu on 2017/3/4.
 */

public class DbHelper<T> {

    private static volatile DbHelper INSTANCE;

    public static final int GET_FIRST_MODEL = 1;

    public static final int GET_LAST_MODEL = 2;

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


    /**
     * 异步插入数据
     *
     * @param object
     */
    public void save(final RealmObject object) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                realm.copyToRealm(object);


            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        });

    }

    public T get(Class<? extends RealmObject> object, String sel, String query,int type) {


        if (type==GET_FIRST_MODEL){
            return (T) realm.where(object).equalTo(sel, query).findFirst();
        }else {
            return (T) realm.where(object).equalTo(sel,"2017-03-15").findAllSorted(sel,Sort.DESCENDING).first();
        }
    }


}
