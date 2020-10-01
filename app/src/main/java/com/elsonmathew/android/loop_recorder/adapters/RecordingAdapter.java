package com.elsonmathew.android.loop_recorder.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elsonmathew.android.loop_recorder.R;
import com.elsonmathew.android.loop_recorder.database.Recording;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by elson.mathew on 9/8/20.
 */
public class RecordingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private List<Recording> dataSet;
	private View.OnClickListener recordingClickListener;



	public RecordingAdapter (@NonNull final List<Recording> dataSet) {
		this.dataSet = dataSet;
	}



	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder (@NonNull final ViewGroup parent, final int viewType) {
		View view;
		RecyclerView.ViewHolder holder;
		view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_recording_item, parent, false);
		holder = new RecordingViewHolder(view);

		return holder;
	}



	@Override
	public void onBindViewHolder (@NonNull final RecyclerView.ViewHolder holder, final int position) {
		final RecordingViewHolder vh = (RecordingViewHolder) holder;

		Recording recording = dataSet.get(position);
		vh.setRecordingTitle(recording.title);
		vh.setRecordingDate(recording.date);

	}



	@Override
	public int getItemCount () {
		return dataSet.size();
	}



	public void setItems (final List<Recording> recordingList) {
		this.dataSet = recordingList;
	}


	public final void setRecordingClickListener (View.OnClickListener itemClickListener) {
		this.recordingClickListener = recordingClickListener;
	}
}
