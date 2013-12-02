package org.therrabitway.appitude.Factory;

import android.os.Environment;

import java.io.File;

/**
 * Created by BCoelho2000 on 01-12-2013.
 */
public class DefaultAlbumStorageManager implements IAlbumStorageManager {

    // Standard storage location for digital camera files
    private static final String CAMERA_DIR = "/dcim/";

    private String m_AlbumName;

    public DefaultAlbumStorageManager(String albumName)
    {
        m_AlbumName = albumName;
    }

    @Override
    public File GetAlbumStoragePath() {
        return new File (Environment.getExternalStorageDirectory() + CAMERA_DIR + m_AlbumName);
    }
}
