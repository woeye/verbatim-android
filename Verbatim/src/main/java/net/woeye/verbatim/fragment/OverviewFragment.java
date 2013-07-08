package net.woeye.verbatim.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import net.woeye.verbatim.EditCardActivity;
import net.woeye.verbatim.R;
import net.woeye.verbatim.db.CardDAO;
import net.woeye.verbatim.db.DatabaseHelper;
import net.woeye.verbatim.model.Card;

import java.util.List;

public class OverviewFragment extends Fragment {
    private CardDAO cardDao;
    private CardAdapter cardAdapter;

    static final String[] numbers = new String[] {
            "Come stai?", "Non lo so", "C", "D", "E",
            "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z",
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };

    public OverviewFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        DatabaseHelper helper = new DatabaseHelper(activity);
        SQLiteDatabase db = helper.getWritableDatabase();
        this.cardDao = new CardDAO(db);

        // TEST-CODE!
        /*for (int i = 0; i<5; i++) {
            Card c = new Card();
            c.setFront("front " + i);
            c.setBack("back " + i);
            this.cardDao.insertCard(c);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GridView gridView = (GridView)inflater.inflate(R.layout.overview_fragment, container, false);

        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                R.layout.card, numbers);
        gridView.setAdapter(adapter);*/

        cardAdapter = new CardAdapter(getActivity(), this.cardDao);
        gridView.setAdapter(cardAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(getActivity(), EditCardActivity.class);
                intent.putExtra("cardId", id);
                startActivity(intent);
            }
        });

        return gridView;
    }

    @Override
    public void onResume() {
        super.onResume();
        cardAdapter.reload();
    }

    /*@Override
    public void onStart() {
        super.onStart();
        cardAdapter.reload();
    }*/

    static class CardAdapter extends BaseAdapter {
        private Context ctx;
        private CardDAO cardDao;
        private List<Card> cards;

        public CardAdapter(Context ctx, CardDAO cardDao) {
            this.ctx = ctx;
            this.cardDao = cardDao;
            reload();
        }

        public void reload() {
            this.cards = cardDao.getCardsOfCollection(0);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return cards.size();
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)this.ctx.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
            );


            if (convertView == null) {
                convertView = new TextView(this.ctx);
                convertView = inflater.inflate(R.layout.card, null);
            }

            Card card = cards.get(pos);
            TextView tv = (TextView)convertView;
            tv.setText(card.getFront());

            return tv;
        }

        @Override
        public long getItemId(int pos) {
            return this.cards.get(pos).getId();
        }

        @Override
        public Object getItem(int pos) {
            return this.cards.get(pos);
        }
    }
}
