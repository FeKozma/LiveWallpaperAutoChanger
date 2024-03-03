package com.example.LiveWallpaperAutoChanger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.LiveWallpaperAutoChanger.Categories.CategiryList;
import com.example.LiveWallpaperAutoChanger.R;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		CategiryList simpleListFragment = new CategiryList(getBaseContext());
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.container, simpleListFragment).commit();
	}
}