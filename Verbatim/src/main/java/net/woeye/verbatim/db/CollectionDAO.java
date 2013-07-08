package net.woeye.verbatim.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import net.woeye.verbatim.model.Collection;

import java.util.ArrayList;
import java.util.List;

public class CollectionDAO {
    private SQLiteDatabase db;

    public CollectionDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public List<Collection> getCollections() {
        ArrayList<Collection> result = new ArrayList<Collection>();

        return result;
    }

    public void insertCollection(Collection col) {
        ContentValues cv = new ContentValues();
        cv.put("name", col.getName());

        long id = db.insert("collection", null, cv);
        col.setId(id);
    }
}
