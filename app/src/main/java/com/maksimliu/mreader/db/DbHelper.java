package com.maksimliu.mreader.db;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by MaksimLiu on 2017/3/4.
 */

public class DbHelper<T> {

    private static volatile DbHelper INSTANCE;

    public static final int GET_FIRST_MODEL = 1;

    public static final int GET_LAST_MODEL = 2;

    private Realm realm;


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

    /**
     * 获取符合查询条件的本地数据
     * @param object Model
     * @param column 查询字段
     * @param query 查询条件
     * @return Model
     */
    public T getLocalData(Class<? extends RealmObject> object, String column, String query) {


        return (T) realm.where(object)
                .equalTo(column, query)
                .findFirst();

    }

    /**
     * 查询本地最新的数据
     * @param object Model
     * @return Model
     */
    public T getLocalLatest(final Class<? extends RealmObject> object) {


        return (T) realm.where(object)
                .findAll()
                .last();
    }


}
