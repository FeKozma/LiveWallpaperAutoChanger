package com.example.LiveWallpaperAutoChanger.Categories;

import android.content.Context;
import android.util.Log;
import android.widget.ListAdapter;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Collection;
import java.util.Collections;

public class CategoryData extends AppCompatActivity {
	private String name;

	private ImageAdapter images;

	public CategoryData(Context context, String name) {
		this.name = name;
		images = new ImageAdapter(context, Collections.emptyList());
	}

	public String getName() {
		return name;
	}

	public ListAdapter getImages() {
		return images;
	}

	void imageChooser(Context context) {


		ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
			registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(5), uri -> {
				// Callback is invoked after the user selects a media item or closes the
				// photo picker.
				if (uri != null) {
					Log.d("PhotoPicker", "Selected URI: " + uri);
					images.addAll((Collection<? extends ImageData>) uri.stream().map(ImageData::new));
					images.notifyDataSetChanged();
				} else {
					Log.d("PhotoPicker", "No media selected");
				}
			});

		String mimeType = "image/*";
		PickVisualMediaRequest p = (new PickVisualMediaRequest.Builder()
			.setMediaType(new ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType)).build());

		context.startActivity(pickMedia.getContract().createIntent(context, p));

	}


}
