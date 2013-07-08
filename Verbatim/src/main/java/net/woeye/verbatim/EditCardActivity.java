package net.woeye.verbatim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import net.woeye.verbatim.db.CardDAO;
import net.woeye.verbatim.db.DatabaseHelper;
import net.woeye.verbatim.model.Card;

public class EditCardActivity extends Activity {
    private CardDAO cardDao;
    private Card card = new Card();
    private boolean isNewCard = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_card);

        DatabaseHelper helper = DatabaseHelper.getInstance(this);
        cardDao = new CardDAO(helper.getWritableDatabase());
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle extras = getIntent().getExtras();
        if (extras.getBoolean("newCard")) {
            isNewCard = true;
            setTitle(R.string.label_new_card);
        } else {
            card = cardDao.getCardById(extras.getLong("cardId"));

            TextView fs = (TextView)findViewById(R.id.front_side);
            TextView bs = (TextView)findViewById(R.id.back_side);

            fs.setText(card.getFront());
            bs.setText(card.getBack());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_save_card:
                saveCard();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveCard() {

        TextView fs = (TextView)findViewById(R.id.front_side);
        TextView bs = (TextView)findViewById(R.id.back_side);

        if (isNewCard) {
            cardDao.insertCard(card);
        }

        card.setFront(fs.getText().toString());
        card.setBack(bs.getText().toString());
        cardDao.updateCard(card);
    }
}
