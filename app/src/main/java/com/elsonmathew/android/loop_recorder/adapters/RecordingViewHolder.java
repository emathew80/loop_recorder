package com.elsonmathew.android.loop_recorder.adapters;

import android.view.View;
import android.widget.TextView;

import com.elsonmathew.android.loop_recorder.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by elson.mathew on 9/8/20.
 */
public class RecordingViewHolder extends RecyclerView.ViewHolder {

	private TextView title;
	private TextView date;


	public RecordingViewHolder (final View view) {
		super(view);
		title = view.findViewById(R.id.recording_title);
		date = view.findViewById(R.id.recording_date);
	}

	public void setRecordingTitle(final String text){
		title.setText(text);
	}

	public void setRecordingDate(final String text){
		date.setText(text);
	}
}
