package org.therrabitway.appitude.Technical;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by BCoelho2000 on 21-12-2013.
 */
public class BitmapManager
{

    public static Bitmap GetScaledBitmap(String picturePath, int targetWidth, int targetHeight, int targetSize)
    {
		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picturePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        while (!(photoW / 2 < targetSize || photoH / 2 < targetSize)) {

            photoW /= 2;
            photoH /= 2;
            scaleFactor *= 2;
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        return BitmapFactory.decodeFile(picturePath, bmOptions);
    }

    public static Bitmap GetScaledBitmap(Uri picturePath, ContentResolver resolver, int targetSize) throws FileNotFoundException
    {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(resolver.openInputStream(picturePath), null, o);

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (!(width_tmp / 2 < targetSize || height_tmp / 2 < targetSize)) {

            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;

        return BitmapFactory.decodeStream(resolver.openInputStream(picturePath), null, o2);
    }
}
