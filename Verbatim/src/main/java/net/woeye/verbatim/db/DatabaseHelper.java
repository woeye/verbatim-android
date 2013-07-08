package net.woeye.verbatim.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper instance = null;

    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "verbatim";
    private Context ctx;

    public static DatabaseHelper getInstance(Context ctx) {
        if (instance == null) {
            instance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return instance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "";

        // Collection
        sql = "create table collection (" +
                "col_id integer primary key, " +
                "name" +
               ")";
        db.execSQL(sql);

        // Card
        sql = "create table card (" +
                "card_id integer primary key, " +
                "col_id integer, " +
                "front text, " +
                "back text, " +
                "level integer, " +
                "last_training integer, " + // unix timestamp
                "foreign key(col_id) references collection(col_id)" +
              ")";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
