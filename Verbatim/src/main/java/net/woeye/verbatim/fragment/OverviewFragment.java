package net.woeye.verbatim.fragment;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import net.woeye.verbatim.R;
import net.woeye.verbatim.db.CardDAO;
import net.woeye.verbatim.db.DatabaseHelper;
import net.woeye.verbatim.model.Card;

import java.util.List;

public class OverviewFragment extends Fragment {
    private CardDAO cardDao;
    private CardAdapter cardAdapter;

    public OverviewFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        DatabaseHelper helper = new DatabaseHelper(activity);
        SQLiteDatabase db = helper.getWritableDatabase();
        this.cardDao = new CardDAO(db);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GridView gridView = (GridView)inflater.inflate(R.layout.overview_fragment, container, false);

        cardAdapter = new CardAdapter(getActivity(), this.cardDao);
        gridView.setAdapter(cardAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                /*Intent intent = new Intent(getActivity(), EditCardActivity.class);
                intent.putExtra("cardId", id);
                startActivity(intent);*/
                FragmentManager fm = getFragmentManager();
                EditCardFragment dialog = new EditCardFragment((Card)cardAdapter.getItem(position));
                dialog.show(fm, "dialog");

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
