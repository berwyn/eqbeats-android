package org.codeweaver.eqbeats.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.codeweaver.eqbeats.model.Track;

/**
 * Created by Berwyn Codeweaver on 31/10/13.
 */
public class TrackStore extends Store<Track> {

    private static final String DATABASE_NAME = "tracks.db";
    private static final int    DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tracks";
    private static final String CREATE_STATEMENT = String.format("create table %s (%s int primary key autoincrement,)"
                                                                +"%s text not null,"
                                                                +"%s text not null"
                                                                );

    public TrackStore(Context ctxt) {
        super(ctxt, DATABASE_NAME, DATABASE_VERSION, TABLE_NAME);
    }

    @Override
    public Track findByID(String id) {
        return null;
    }

    @Override
    public Track saveByID(String id, Track model) {
        return null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
