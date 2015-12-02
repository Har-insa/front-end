package com.hardis.connect.activity;

/**
 * Created by Hassan on 12/1/2015.
 */
    import android.animation.Animator;
    import android.animation.AnimatorListenerAdapter;
    import android.animation.AnimatorSet;
    import android.animation.ObjectAnimator;
    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.os.Bundle;
    import android.os.Handler;
    import android.support.v7.app.ActionBarActivity;
    import android.support.v7.widget.Toolbar;
    import android.view.ContextThemeWrapper;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.Toast;

    import com.github.clans.fab.FloatingActionButton;
    import com.github.clans.fab.FloatingActionMenu;
    import com.hardis.connect.R;

    import java.util.ArrayList;
    import java.util.List;


public class CovoiturageOfferDetailsActivity extends ActionBarActivity {


    private FloatingActionButton fab_book;
    private FloatingActionButton fab_cancel;
    private FloatingActionMenu menu_book;
    private List<FloatingActionMenu> menus = new ArrayList<>();
    private Handler mUiHandler = new Handler();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.details_offre_covoiturage);

//handle the FAB : Menu + buttons
            menu_book = (FloatingActionMenu) findViewById(R.id.menu_book);

            menu_book.setOnMenuButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menu_book.toggle(true);
                }
            });
            menus.add(menu_book);
            menu_book.hideMenuButton(false);

            int delay = 400;
            for (final FloatingActionMenu menu : menus) {
                mUiHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        menu.showMenuButton(true);
                    }
                }, delay);
                delay += 150;
            }

            menu_book.setClosedOnTouchOutside(true);

//buttons
            fab_book = (FloatingActionButton) findViewById(R.id.fab_book);
            fab_cancel = (FloatingActionButton) findViewById(R.id.fab_cancel);


            fab_book.setOnClickListener(clickListener);
            fab_cancel.setOnClickListener(clickListener);




        }


        private void createAndShowAlertDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Réservation");
            builder.setMessage("Vous voullez réserver une place ?");
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //TODO
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //TODO
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fab_book:
                    createAndShowAlertDialog();
                    menu_book.close(true);
                    break;
                case R.id.fab_cancel:
                    menu_book.close(true);
                    finish();
                    break;
            }


        }
    };
    }