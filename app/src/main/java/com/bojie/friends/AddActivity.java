package com.bojie.friends;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by bojiejiang on 3/2/15.
 */
public class AddActivity extends FragmentActivity {
    private static final String LOG_TAG = AddActivity.class.getSimpleName();
    private TextView mName_tv, mEmail_tv, mPhone_tv;
    private Button mButton;
    private ContentResolver mContentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mName_tv = (TextView) findViewById(R.id.tv_friend_name);
        mEmail_tv = (TextView) findViewById(R.id.tv_friend_email);
        mPhone_tv = (TextView) findViewById(R.id.tv_friend_phone);

        mContentResolver = AddActivity.this.getContentResolver();

        mButton = (Button) findViewById(R.id.btn_save);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    ContentValues values = new ContentValues();
                    values.put(FriendsContract.FriendsColumns.FRIENDS_NAME, mName_tv.getText().toString());
                    values.put(FriendsContract.FriendsColumns.FRIENDS_EMAIL, mEmail_tv.getText().toString());
                    values.put(FriendsContract.FriendsColumns.FRIENDS_PHONE, mPhone_tv.getText().toString());
                    Uri returned = mContentResolver.insert(FriendsContract.URI_TABLE, values);
                    Log.d(LOG_TAG, "record is returned is " + returned.toString());
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please ensure you have entered valid data",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean isValid() {
        if (mName_tv.getText().toString().length() == 0 ||
                mPhone_tv.getText().toString().length() == 0 ||
                mEmail_tv.getText().toString().length() == 0) {
            return false;
        }

        return true;
    }

    private boolean someDataEntered() {
        if (mName_tv.getText().toString().length() > 0 ||
                mEmail_tv.getText().toString().length() > 0 ||
                mPhone_tv.getText().toString().length() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (someDataEntered()) {
            FriendsDialog dialog = new FriendsDialog();
            Bundle args = new Bundle();
            args.putString(FriendsDialog.DIALOG_TYPE, FriendsDialog.CONFIRM_EXIT);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "confirm-exit");
        } else {
            super.onBackPressed();
        }
    }
}
