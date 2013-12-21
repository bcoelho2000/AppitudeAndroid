package org.therrabitway.appitude.Media;

import android.os.Environment;
import android.util.Log;

import org.therrabitway.appitude.Factory.AlbumStorageFactory;
import org.therrabitway.appitude.R;

import java.io.File;

/**
 * Created by BCoelho2000 on 21-12-2013.
 */
public class MediaManager
{
    protected static String AlbumName = "Appitude"; // Get this on a ctor?

    public static File GetAlbumDirectory() {
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
                        Log.d("Appitude.MediaManager.GetAlbumDirectory", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.d("Appitude.MediaManager.GetAlbumDirectory", "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }
}
