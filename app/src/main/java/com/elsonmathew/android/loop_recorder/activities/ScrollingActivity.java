package com.elsonmathew.android.loop_recorder.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Bundle;

import com.elsonmathew.android.loop_recorder.R;
import com.elsonmathew.android.loop_recorder.adapters.RecordingAdapter;
import com.elsonmathew.android.loop_recorder.database.AppDatabase;
import com.elsonmathew.android.loop_recorder.database.Recording;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RxRoom;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity {

	private RecyclerView recyclerView;
	private LinearLayoutManager layoutManager;
	private RecordingAdapter recordingAdapter;
	boolean mStartRecording = true;
	private MediaRecorder recorder;
	private String baseFileName;
	private String filename;
	private List<Recording> recordingList = new ArrayList<>();


	private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
	private boolean permissionToRecordAccepted = false;
	private String [] permissions = { Manifest.permission.RECORD_AUDIO};
	private String recordingTitle;
	private AppDatabase db;



	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrolling);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

		db = Room.databaseBuilder(getApplicationContext(),
											  AppDatabase.class, "recording-db").allowMainThreadQueries().build();



		baseFileName = getExternalCacheDir().getAbsolutePath();

		final FloatingActionButton fab = findViewById(R.id.fab);
		fab.setColorFilter(Color.BLUE);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick (View view) {
				onRecord(mStartRecording);
				if (mStartRecording) {
					fab.setColorFilter(Color.RED);
				} else {
					fab.setColorFilter(Color.BLUE);
				}
				mStartRecording = !mStartRecording;
			}
		});

		recyclerView = findViewById(R.id.my_recycler_view);
		recyclerView.setHasFixedSize(true);

		layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		recordingAdapter = new RecordingAdapter(db.recordingDao().getAll());
		recyclerView.setAdapter(recordingAdapter);

		recordingAdapter.setRecordingClickListener(new View.OnClickListener() {
			@Override
			public void onClick (final View v) {


			}
		});
	}



	private void onRecord (final boolean start) {
		if (start) {
			enterRecordingNameDialog();
		} else {
			stopRecording();
		}
	}



	private void startRecording () {
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setOutputFile(filename);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		try {
			recorder.prepare();
		} catch (IOException e) {
			Log.e("TAG", "prepare() failed");
		}

		recorder.start();
	}


	private void stopRecording () {
		Recording recording = new Recording();
		recording.date = new Date().toString();
		recording.fileName = filename;
		recording.title = recordingTitle;
		recordingList.add(recording);
		recordingAdapter.setItems(recordingList);
		recordingAdapter.notifyDataSetChanged();
		db.recordingDao().insert(recording);
		recorder.stop();
		recorder.release();
		recorder = null;

	}



	private void enterRecordingNameDialog () {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View viewInflated = LayoutInflater.from(this).inflate(R.layout.enter_title_dialog, null);
		// Set up the input
		final EditText input = viewInflated.findViewById(R.id.input);
		// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
		builder.setTitle("Please enter a title.")
				.setView(viewInflated)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				recordingTitle = input.getText().toString();
				filename = baseFileName + recordingTitle ;
				startRecording();
				dialog.dismiss();
			}
		}).setCancelable(false).show();
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode){
			case REQUEST_RECORD_AUDIO_PERMISSION:
				permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
				break;
		}
		if (!permissionToRecordAccepted ) finish();
	}


	@Override
	public boolean onCreateOptionsMenu (Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_scrolling, menu);
		return true;
	}



	@Override
	public boolean onOptionsItemSelected (MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//TODO Impl later
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
