package net.woeye.verbatim.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import net.woeye.verbatim.R;
import net.woeye.verbatim.model.Card;

public class EditCardFragment extends DialogFragment {
    private EditDialogListener mListener;
    private Card mCard = null;

    public EditCardFragment() {
        this(new Card());
    }

    public EditCardFragment(Card card) {
        mCard = card;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (EditDialogListener)activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.edit_card, null);

        final TextView fs = (TextView)rootView.findViewById(R.id.front_side);
        fs.setText(mCard.getFront());

        final TextView bs = (TextView)rootView.findViewById(R.id.back_side);
        bs.setText(mCard.getBack());

        // TODO: Use resource
        String buttonText = mCard.isNew() ? "Add" : "Save";

        builder.setView(rootView)
            .setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int id) {
                    mCard.setFront(fs.getText().toString());
                    mCard.setBack(bs.getText().toString());
                    mListener.onAddOrSave(mCard);
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int id) {
                }
            });

        return builder.create();
    }

    public static interface EditDialogListener {
        public void onAddOrSave(Card card);
    }
}
