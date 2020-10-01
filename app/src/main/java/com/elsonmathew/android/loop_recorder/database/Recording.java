package com.elsonmathew.android.loop_recorder.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by elson.mathew on 9/9/20.
 */
@Entity
public class Recording {

	@PrimaryKey
	public int uid;

	@ColumnInfo(name = "recording_title")
	public String title;

	@ColumnInfo(name = "date")
	public String date;

	@ColumnInfo(name = "file_name")
	public String fileName;

}
