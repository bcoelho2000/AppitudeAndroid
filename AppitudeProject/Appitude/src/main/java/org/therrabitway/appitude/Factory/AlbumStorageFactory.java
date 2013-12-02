package org.therrabitway.appitude.Factory;

import android.os.Build;

/**
 * Created by BCoelho2000 on 30-11-2013.
 */
public class AlbumStorageFactory
{
    public static IAlbumStorageManager Create(String albumName)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            return new FroyoAlbumStorageManager(albumName);
        } else {
            return new DefaultAlbumStorageManager(albumName);
        }
    }
}
