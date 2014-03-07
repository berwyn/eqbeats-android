package org.codeweaver.eqbeats.db;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Like all SQLiteOpenHelpers, subclasses <strong>MUST</strong> override
 * #onCreate and #onUpgrade
 * <br/>
 * Created by Berwyn Codeweaver on 31/10/13.
 */
public abstract class Store<T> extends SQLiteOpenHelper {

    private String dbName;
    private int dbVersion;
    private String tableName;

    public Store(Context ctxt, String dbName, int dbVersion, String tableName) {
        super(ctxt, dbName, null, dbVersion);
        this.dbName = dbName;
        this.dbVersion = dbVersion;
        this.tableName = tableName;
    }

    public abstract T findByID(String id);
    public abstract T saveByID(String id, T model);

}
