package com.akaita.fda;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.akaita.fda.database.objects.Artist;
import com.akaita.fda.update.UpdateDatabaseTask;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements ArtistFragment.OnArtistSelectedListener, ArtistFragment.OnArtistListUpdatedListener {
    public static final String URL_1 = "http://i.img.co/data/data.json";
    public static final String TAG_MAIN_FRAGMENT = "main_fragment";
    public static final String TAG_ALBUM_FRAGMENT = "album_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showFragmentArtists();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onArtistSelected(Artist artist) {
        showFragmentAlbum(artist);
    }

    private void showFragmentArtists() {
        // Create new fragment and transaction
        MainActivityFragment newFragment = new MainActivityFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.fragment, newFragment, TAG_MAIN_FRAGMENT);
        //transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    private void showFragmentAlbum(Artist artist) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundl = new Bundle();
        bundl.putLong(AlbumFragment.EXTRA_ARTIST_ID, artist.getId());

        // Create new fragment and transaction
        AlbumFragment newFragment = new AlbumFragment();
        newFragment.setArguments(bundl);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.fragment, newFragment, TAG_ALBUM_FRAGMENT);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onArtistListUpdated() {
        MainActivityFragment mainActivityFragment = (MainActivityFragment)
                getSupportFragmentManager().findFragmentByTag(TAG_MAIN_FRAGMENT);

        if (mainActivityFragment != null) {
            mainActivityFragment.updateSlidingTabs();
        }
    }
}
