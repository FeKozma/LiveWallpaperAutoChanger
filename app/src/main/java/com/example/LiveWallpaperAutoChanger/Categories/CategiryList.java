package com.example.LiveWallpaperAutoChanger.Categories;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.LiveWallpaperAutoChanger.R;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CategiryList extends ListFragment {

	private List<CategoryData> cattegories;

	public CategiryList(Context context) {
		cattegories = Stream.of("Sunny", "Rainy", "Windy").map(catName -> new CategoryData(context, catName)).collect(Collectors.toList());
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		setListAdapter(new CategoryAdapter(getContext(), cattegories));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater
				.inflate(R.layout.categories, container, false);
	}

	@Override
	public void onListItemClick(ListView list, View v, int position, long id) {

		Toast.makeText(getActivity(),
				getListView().getItemAtPosition(position).toString(),
				Toast.LENGTH_LONG).show();
	}
}
