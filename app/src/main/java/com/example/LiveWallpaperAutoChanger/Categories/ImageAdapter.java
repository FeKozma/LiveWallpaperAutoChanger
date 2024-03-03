package com.example.LiveWallpaperAutoChanger.Categories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.LiveWallpaperAutoChanger.R;

import java.util.List;

public class ImageAdapter extends ArrayAdapter<ImageData> {

	private Context mContext;
	private List<ImageData> imageList;

	public ImageAdapter(@NonNull Context context, @NonNull List<ImageData> objects) {
		super(context, 0, objects);
		mContext = context;
		imageList = objects;
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		View listItem = convertView;
		if(listItem == null)
			listItem = LayoutInflater.from(mContext).inflate(R.layout.image,parent,false);

		ImageData currentMovie = imageList.get(position);

		ImageView name = listItem.findViewById(R.id.backgroundImage);
		name.setVisibility(View.VISIBLE);
		//name.setText(currentMovie.getName());

		return listItem;
	}


}
