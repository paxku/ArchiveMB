package com.akaita.fda.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.akaita.fda.database.objects.Album;
import com.akaita.fda.database.objects.Artist;
import com.akaita.fda.database.objects.ArtistAlbum;
import com.akaita.fda.database.objects.ArtistGenre;
import com.akaita.fda.database.objects.Genre;
import com.akaita.fda.database.objects.Type;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by mikel on 18/05/2015.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        Log.i(getClass().toString(), "Creating database");
        try {
            TableUtils.createTable(connectionSource, Artist.class);
            TableUtils.createTable(connectionSource, ArtistAlbum.class);
            TableUtils.createTable(connectionSource, ArtistGenre.class);
            TableUtils.createTable(connectionSource, Genre.class);
            TableUtils.createTable(connectionSource, Album.class);
            TableUtils.createTable(connectionSource, Type.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.i(getClass().toString(), "Upgrading database: " + String.valueOf(oldVersion)+"-->"+String.valueOf(newVersion) );
        dropDatabase(db);
    }

    private void dropDatabase(SQLiteDatabase db) {
        Log.i(getClass().toString(), "Dropping database: " + db.toString() );
        try {
            TableUtils.dropTable(connectionSource, Artist.class, true);
            TableUtils.dropTable(connectionSource, ArtistAlbum.class, true);
            TableUtils.dropTable(connectionSource, ArtistGenre.class, true);
            TableUtils.dropTable(connectionSource, Genre.class, true);
            TableUtils.dropTable(connectionSource, Album.class, true);
            TableUtils.dropTable(connectionSource, Type.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
