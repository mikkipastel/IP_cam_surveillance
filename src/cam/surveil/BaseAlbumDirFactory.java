package cam.surveil;

import java.io.File;

import android.os.Environment;

public final class BaseAlbumDirFactory extends AlbumStorageDirFactory {

	// Standard storage location for digital camera files
	private static final String PICTURES_DIR = "/FORTH2_IPCAM/"; //for mobile
	//private static final String CAMERA_DIR = "/dcim/"; //for tablet

	@Override
	public File getAlbumStorageDir(String albumName) {
		return new File (
				Environment.getExternalStorageDirectory()
				+ PICTURES_DIR //for mobile
				//+ CAMERA_DIR  //for tablet
				+ albumName
		);
	}
}
