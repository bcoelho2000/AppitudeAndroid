package org.therrabitway.appitude;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import java.io.File;

public class MainActivity extends ActionBarActivity {

    static final int CAPTURE_REQUEST_CODE = 1;
    static final int REMEMBER_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainLayout, new PlaceholderFragment())
                    .commit();
        }*/

        String path = Environment.getExternalStorageDirectory().toString();
        File dir = new File(path, "/Appitude/");
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Called when the user clicks the Capture button */
    public void BtnClickCaptureOnClick(View view)
    {
        startActivityForResult(new Intent(this, CaptureActivity.class), CAPTURE_REQUEST_CODE);
    }

    public void BtnClickRememberOnClick(View view)
    {
        startActivityForResult(new Intent(this, RememberActivity.class), REMEMBER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // Check which request we're responding to
        if (requestCode == CAPTURE_REQUEST_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

            }
            else{
            }
        }
    }





    /**
     * A placeholder fragment containing a simple view.

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }*/

}
