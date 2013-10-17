/*
 * Copyright (C) 2013 Berwyn Codeweaver
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codeweaver.eqbeats.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.codeweaver.eqbeats.model.User;

import java.sql.SQLException;

/**
 * Created by Berwyn Codeweaver on 03/09/2013.
 */
public class UserHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = "UserHelper";
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<User, Integer> userDao = null;

    public UserHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        Log.d(TAG, "Creating user DB");
        try {
            TableUtils.createTable(connectionSource, User.class);
        } catch (SQLException e) {
            Log.d(TAG, "Failed to create DB, crashing app");
            throw new RuntimeException("Failed to create user db");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {

    }
}
