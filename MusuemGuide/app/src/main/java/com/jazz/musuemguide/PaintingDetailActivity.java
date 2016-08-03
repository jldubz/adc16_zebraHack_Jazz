package com.jazz.musuemguide;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class PaintingDetailActivity extends AppCompatActivity {
    private static final String TAG = "PaintingDetailActivity";

    public static final String ACTION_LAUNCH = "com.jazz.action.PaintingDetail";

    public static final String PARAM_TITLE       = "com.jazz.extra.Title";
    public static final String PARAM_ARTIST      = "com.jazz.extra.Artist";
    public static final String PARAM_DESCRIPTION = "com.jazz.extra.Date";
    public static final String PARAM_ASSET_PATH  = "com.jazz.extra.AssetPath";

    private TextView  mTitle;
    private TextView  mArtist;
    private TextView  mDescription;
    private ImageView mPaintingSwatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painting_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Playing audio...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        // Put up the data
//        showDataTest();
        showDataProduction();
    }

    private void showDataTest() {
        mTitle = (TextView) findViewById(R.id.painting_detail_title);
        mTitle.setText("Mona Lisa");
        mArtist = (TextView) findViewById(R.id.painting_detail_artist);
        mArtist.setText("Leonardo DaVinci");
        mDescription = ((TextView) findViewById(R.id.painting_detail_description));
        mDescription.setText("Mysterious smiler. Fear not, dear viewer: it PROBABLY isn't you " +
                             "she's laughing at.");
        mPaintingSwatch = (ImageView) findViewById(R.id.painting_detail_swatch);
        InputStream picStream = null;
        try {
            picStream = getAssets().open("monalisa.jpg");
            final Drawable pic = Drawable.createFromStream(picStream, null);
            pic.setAlpha(128);
            mPaintingSwatch.setImageDrawable(pic);
        } catch (IOException e) {
            Log.e(TAG, "Failed to get image asset", e);
        } finally {
            if (picStream != null) try {
                picStream.close();
            } catch (IOException e) {
                Log.e(TAG, "Failed to close image stream", e);
            }
        }
    }

    private void showDataProduction() {
        mTitle = (TextView) findViewById(R.id.painting_detail_title);
        mTitle.setText(getIntent().getStringExtra(PARAM_TITLE));
        mArtist = (TextView) findViewById(R.id.painting_detail_artist);
        mArtist.setText(getIntent().getStringExtra(PARAM_ARTIST));
        mDescription = ((TextView) findViewById(R.id.painting_detail_description));
        mDescription.setText(getIntent().getStringExtra(PARAM_DESCRIPTION));
        mPaintingSwatch = (ImageView) findViewById(R.id.painting_detail_swatch);
        InputStream picStream = null;
        try {
            picStream = getAssets().open(getIntent().getStringExtra(PARAM_ASSET_PATH));
            final Drawable pic = Drawable.createFromStream(picStream, null);
            pic.setAlpha(128);
            mPaintingSwatch.setImageDrawable(pic);
        } catch (IOException e) {
            Log.e(TAG, "Failed to get image asset", e);
        } finally {
            if (picStream != null) try {
                picStream.close();
            } catch (IOException e) {
                Log.e(TAG, "Failed to close image stream", e);
            }
        }
    }
}