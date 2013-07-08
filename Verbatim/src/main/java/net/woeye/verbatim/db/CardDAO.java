package net.woeye.verbatim.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.woeye.verbatim.model.Card;

import java.util.ArrayList;
import java.util.List;

public class CardDAO {
    private SQLiteDatabase db;

    public CardDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public List<Card> getCardsOfCollection(final long colId) {
        ArrayList<Card> result = new ArrayList<Card>();

        String query = "select card_id, front, back, level, last_training" +
            " from card where col_id = ?";
        Cursor cursor = db.rawQuery(query, new String[] { String.valueOf(colId)} );
        if (cursor.moveToFirst()) {
            do {
                int pos = 0;
                Card card = new Card();
                card.setId(cursor.getLong(pos++));
                card.setCollectionId(colId);
                card.setFront(cursor.getString(pos++));
                card.setBack(cursor.getString(pos++));
                card.setLevel(cursor.getInt(pos++));
                result.add(card);
            } while (cursor.moveToNext());
        }

        return result;
    }

    public Card getCardById(long id) {
        Card card = new Card();

        String query = "select col_id, front, back, level, last_training" +
            " from card where card_id = ?";
        Cursor cursor = db.rawQuery(query, new String[] { String.valueOf(id)} );
        if (cursor.moveToFirst()) {
            int pos = 0;
            card.setId(id);
            card.setCollectionId(pos++);
            card.setFront(cursor.getString(pos++));
            card.setBack(cursor.getString(pos++));
            card.setLevel(cursor.getInt(pos++));
        }

        return card;
    }

    public void insertCard(Card card) {
        ContentValues cv = new ContentValues();
        cv.put("col_id", card.getCollectionId());
        cv.put("front", card.getFront());
        cv.put("back", card.getBack());
        cv.put("level", card.getLevel());

        long id = db.insert("card", null, cv);
        card.setId(id);
    }

    public void updateCard(Card card) {
        ContentValues cv = new ContentValues();
        cv.put("front", card.getFront());
        cv.put("back", card.getBack());
        cv.put("level", card.getLevel());

        db.update("card", cv, "card_id = ?", new String[] { String.valueOf(card.getId()) });
    }
}
