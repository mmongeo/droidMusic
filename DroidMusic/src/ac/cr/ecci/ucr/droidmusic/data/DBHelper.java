package ac.cr.ecci.ucr.droidmusic.data;

import java.sql.SQLException;

import ac.cr.ecci.ucr.droidmusic.R;
import ac.cr.ecci.ucr.droidmusic.bo.Song;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


public class DBHelper extends OrmLiteSqliteOpenHelper {

	private static final String TAG = "DB";
	private static final String DATABASE_NAME = "droidMusic.db";
	private static final int DATABASE_VERSION = 1;
	private static DBHelper helper;
	private Dao<Song, Integer> dao;

	public static DBHelper getHelper(Context context) {
		if(helper == null){
			helper = new DBHelper(context);
		}
		return helper;
	}
	
	private DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {

		Log.d(TAG, "onCreate");

		try {
			TableUtils.createTable(connectionSource, Song.class);
		} catch (SQLException e) {
			Log.e(TAG, "Can't create database", e);
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {

	}
	
	/**
	 * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
	 * value.
	 */
	public Dao<Song, Integer> getDao() throws SQLException {
		if (dao == null) {
			dao = getDao(Song.class);
		}
		return dao;
	}

//	private void insertData() throws SQLException {
//		Dao<Score, Integer> scoreDao = getDao(Score.class);
//		scoreDao.createOrUpdate(new Score("Rubén", Integer.MAX_VALUE));
//		scoreDao.createOrUpdate(new Score("Mario", 0));
//		scoreDao.createOrUpdate(new Score("Marco", 0));
//	}

}