package com.akaita.fda.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by mikel on 18/05/2015.
 */
@DatabaseTable(tableName = "artistalbum")
public class ArtistAlbum {
    public static final String ARTIST_ID_FIELD_NAME = "artist_id";
    public static final String ALBUM_ID_FIELD_NAME = "album_id";

    /**
     * This id is generated by the database and set on the object when it is passed to the create method. An id is
     * needed in case we need to update or delete this object in the future.
     */
    @DatabaseField(generatedId = true)
    int id;

    // This is a foreign object which just stores the id from the User object in this table.
    @DatabaseField(foreign = true, columnName = ARTIST_ID_FIELD_NAME)
    Artist artist;

    // This is a foreign object which just stores the id from the Post object in this table.
    @DatabaseField(foreign = true, columnName = ALBUM_ID_FIELD_NAME)
    Album album;

    ArtistAlbum() {
        // for ormlite
    }

    public ArtistAlbum(Artist artist, Album album) {
        this.artist = artist;
        this.album = album;
    }
}
