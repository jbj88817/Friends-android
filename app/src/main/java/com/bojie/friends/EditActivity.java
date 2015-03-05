package com.bojie.friends;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by bojiejiang on 3/2/15.
 */
public class EditActivity extends FragmentActivity {

    private static final String LOG_TAG = EditActivity.class.getSimpleName();
    private TextView mName_tv, mEmail_tv, mPhone_tv;
    private Button mButton;
    private ContentResolver mContentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mName_tv = (TextView) findViewById(R.id.et_friendName);
        mEmail_tv = (TextView) findViewById(R.id.et_friendEmail);
        mPhone_tv = (TextView) findViewById(R.id.et_friendPhone);

        mContentResolver = EditActivity.this.getContentResolver();
        Intent intent = getIntent();
        final String _id = intent.getStringExtra(FriendsContract.FriendsColumns.FRIENDS_ID);
        String name = intent.getStringExtra(FriendsContract.FriendsColumns.FRIENDS_NAME);
        String phone = intent.getStringExtra(FriendsContract.FriendsColumns.FRIENDS_PHONE);
        String email = intent.getStringExtra(FriendsContract.FriendsColumns.FRIENDS_EMAIL);

        mName_tv.setText(name);
        mPhone_tv.setText(phone);
        mEmail_tv.setText(email);

        mButton = (Button) findViewById(R.id.btn_save);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(FriendsContract.FriendsColumns.FRIENDS_NAME, mName_tv.getText().toString());
                values.put(FriendsContract.FriendsColumns.FRIENDS_EMAIL, mEmail_tv.getText().toString());
                values.put(FriendsContract.FriendsColumns.FRIENDS_PHONE, mPhone_tv.getText().toString());
                Uri uri = FriendsContract.Friends.buildFriendUri(_id);
                int recordsUpdated = mContentResolver.update(uri, values, null, null);
                Log.d(LOG_TAG, "number of records update =" + recordsUpdated);
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }

        return true;
    }
}

