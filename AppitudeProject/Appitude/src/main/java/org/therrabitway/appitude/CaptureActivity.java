package org.therrabitway.appitude;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;

import org.therrabitway.appitude.Factory.AlbumStorageFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CaptureActivity extends ActionBarActivity {

    static final int CAPTURE_REQUEST_CODE = 1;
    static final String JPEG_FILE_PREFIX = "Appitude";
    static final String JPEG_FILE_SUFFIX = ".jpg";

    protected String AlbumName = "Appitude"; //getResources().getString(R.string.app_name);

    private String m_CurrentPhotoPath;

    private File GetAlbumDirectory() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
        {
            storageDir = AlbumStorageFactory.Create(AlbumName).GetAlbumStoragePath();

            if (storageDir != null)
            {
                if (! storageDir.mkdirs())
                {
                    if (! storageDir.exists())
                    {
                        Log.d("Appitude.CaptureActivity.GetAlbumDirectory", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private File CreateImageFile() throws IOException
    {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File f = File.createTempFile(
                        JPEG_FILE_PREFIX + timeStamp + "_",
                        JPEG_FILE_SUFFIX,
                        GetAlbumDirectory());

        m_CurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    private void GalleryAddPicture(String filePath) {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        mediaScanIntent.setData(Uri.fromFile(new File(filePath)));
        this.sendBroadcast(mediaScanIntent);
    }




    private void ApplyPictureOnImageView(ImageView mImageView, String picturePath)
    {
        /* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picturePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        int REQUIRED_SIZE = 400;
        while (!(photoW / 2 < REQUIRED_SIZE || photoH / 2 < REQUIRED_SIZE)) {

            photoW /= 2;
            photoH /= 2;
            scaleFactor *= 2;
        }


        /*
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }
        */

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(picturePath, bmOptions);

		/* Associate the Bitmap to the ImageView */
        mImageView.setImageBitmap(bitmap);

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            File picture = CreateImageFile();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picture));
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        startActivityForResult(takePictureIntent, CAPTURE_REQUEST_CODE);
    }

     /*
        * Photos taken by the ACTION_IMAGE_CAPTURE are not registered in the MediaStore automatically on all devices.
        * The official Android guide gives that example: http://developer.android.com/guide/topics/media/camera.html#intent-receive But that does not work either on all devices.
        * The only reliable method I am aware of consists in saving the path to the picture in a local variable.
        *
        * Beware that your app may get killed while in background (while the camera app is running),
        *   so you must save the path during onSaveInstanceState.
        *
        * Solution: Create a temporary file name where the photo will be stored when starting the intent.
        *
        * Source: http://stackoverflow.com/questions/12952859/capturing-images-with-mediastore-action-image-capture-intent-in-android
        * */
    /** Called when the user clicks the Capture button */
    public void CaptureBtnSaveOnClick(View view)
    {
        Intent returnIntent = new Intent();
        //returnIntent.putExtra("result",result);
        setResult(RESULT_OK,returnIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // Check which request we're responding to
        if (requestCode == CAPTURE_REQUEST_CODE)
        {
            // Make sure the request was successful
            if (resultCode == RESULT_OK)
            {
                ImageView img = (ImageView) findViewById(R.id.CaptureImageView);

                // The user took a photo
                if(data != null)
                {
                    Bundle extras = data.getExtras();
                    Bitmap mImageBitmap = (Bitmap) extras.get("data");
                    img.setImageBitmap(mImageBitmap);
                }
                else
                {
                    ApplyPictureOnImageView(img, m_CurrentPhotoPath);
                }

                // Add it to the gallery
                GalleryAddPicture(m_CurrentPhotoPath);
            }
        }
        else
        {

        }

    }

    private void SaveImage(Bitmap finalBitmap)
    {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Appitude/");
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        MediaScannerConnection.scanFile(this,new String[]{file.toString()}, null, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.capture, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_capture, container, false);
            return rootView;
        }
    }









}
