package com.example.LiveWallpaperAutoChanger.Categories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.LiveWallpaperAutoChanger.R;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<CategoryData> {

	private Context mContext;
	private List<CategoryData> categoryList;
	public CategoryAdapter(@NonNull Context context, @NonNull List<CategoryData> objects) {
		super(context, 0, objects);
		mContext = context;
		categoryList = objects;
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		View listItem = convertView;
		if(listItem == null)
			listItem = LayoutInflater.from(mContext).inflate(R.layout.category,parent,false);

		CategoryData currentCattegory = categoryList.get(position);

		TextView name = listItem.findViewById(R.id.name);
		name.setText(currentCattegory.getName());

		Button add = listItem.findViewById(R.id.addImage);
		add.setOnClickListener(v -> {
			currentCattegory.imageChooser(mContext);
		});

		GridView listImages = listItem.findViewById(R.id.list_images);
		listImages.setAdapter(currentCattegory.getImages());

		return listItem;
	}
}
