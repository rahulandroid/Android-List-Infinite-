/*******************************************************************************
 * Copyright 2014 Rahulandroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.rahul.list.activity;

import android.os.Bundle;
import com.rahul.list.Constants;
import com.rahul.list.R;
import com.rahul.list.fragment.ImageGalleryFragment;
import com.rahul.list.fragment.ImageGridFragment;
import com.rahul.list.fragment.ImageListFragment;
import com.rahul.list.fragment.ImagePagerFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * @author Rahulandroid (https://github.com/rahulandroid)
 */
public class SimpleImageActivity extends FragmentActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		int frIndex = getIntent().getIntExtra(Constants.Extra.FRAGMENT_INDEX, 0);
		 Fragment fr;
		String tag;
		int titleRes;
		switch (frIndex) {
			default:
			case ImageListFragment.INDEX:
				tag = ImageListFragment.class.getSimpleName();
				fr = getSupportFragmentManager().findFragmentByTag(tag);
				if (fr == null) {
					fr = new ImageListFragment();
				}
				titleRes = R.string.ac_name_image_list;
				break;
			case ImageGridFragment.INDEX:
				tag = ImageGridFragment.class.getSimpleName();
				fr = getSupportFragmentManager().findFragmentByTag(tag);
				if (fr == null) {
					fr = new ImageGridFragment();
				}
				titleRes = R.string.ac_name_image_grid;
				break;
			case ImagePagerFragment.INDEX:
				tag = ImagePagerFragment.class.getSimpleName();
				fr = getSupportFragmentManager().findFragmentByTag(tag);
				if (fr == null) {
					fr = new Fragment();
					fr.setArguments(getIntent().getExtras());
				}
				titleRes = R.string.ac_name_image_pager;
				break;
			case ImageGalleryFragment.INDEX:
				tag = ImageGalleryFragment.class.getSimpleName();
				fr = getSupportFragmentManager().findFragmentByTag(tag);
				if (fr == null) {
					fr = new ImageGalleryFragment();
				}
				titleRes = R.string.ac_name_image_gallery;
				break;
		}

		setTitle(titleRes);
		getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fr, tag).commit();
	}
}