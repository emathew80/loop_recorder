package com.elsonmathew.android.loop_recorder.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created by elson.mathew on 9/9/20.
 */
@Database(entities = { Recording.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
	public abstract RecordingDao recordingDao();
}