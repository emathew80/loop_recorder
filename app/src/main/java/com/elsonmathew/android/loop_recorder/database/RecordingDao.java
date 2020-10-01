package com.elsonmathew.android.loop_recorder.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * Created by elson.mathew on 9/9/20.
 */
@Dao
public interface RecordingDao {

	@Query("SELECT * FROM Recording")
	List<Recording> getAll();

	@Query("SELECT * FROM Recording WHERE uid IN (:recordingIds)")
	List<Recording> loadAllByIds(int[] recordingIds);

	@Query("SELECT * FROM Recording WHERE recording_title LIKE :title LIMIT 1")
	Recording findByTitle(String title);

	@Insert
	void insert(Recording... recording);

	@Delete
	void delete(Recording recordings);
}
