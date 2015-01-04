/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.saf;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import cn.salesuite.saf.inject.Injector;
import cn.salesuite.saf.inject.annotation.InjectView;
import cn.salesuite.saf.inject.annotation.OnClick;

import com.example.android.apis.R;


/**
 * from List8
 * 
 * A list view that demonstrates the use of setEmptyView. This example alos uses
 * a custom layout file that adds some extra buttons to the screen.
 */
public class SAFListDemo1 extends ListActivity {

    PhotoAdapter mAdapter;
    
	// TODO SAF中通过@InjectView注解来注册 页面元素,默认通过属性名称，查找相同id的页面元素
    @InjectView
    private Button clear;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use a custom layout file
        setContentView(R.layout.saf_list_1);
        //	TODO 通过此方法注册，实现具体页面的注入 
        Injector.injectInto(this);
        
        // Tell the list view which view to display when the list is empty
        getListView().setEmptyView(findViewById(R.id.empty));
        
        // Set up our adapter
        mAdapter = new PhotoAdapter(this);
        setListAdapter(mAdapter);
        

    }
    
    @OnClick(id = R.id.clear)
    public void btnClearOnClickListener() {
    	// TODO SAF中通过@OnClick 注解来注册 页面元素的OnClickListener件
        // Wire up the clear button to remove all photos
    	mAdapter.clearPhotos();
    }
    
    // Wire up the add button to add a new photo
    @OnClick(id = R.id.add)
    public void btnAddOnClickListener() {
            mAdapter.addPhotos();
   }
    

    /**
     * A simple adapter which maintains an ArrayList of photo resource Ids. 
     * Each photo is displayed as an image. This adapter supports clearing the
     * list of photos and adding a new photo.
     *
     */
    public class PhotoAdapter extends BaseAdapter {

        private Integer[] mPhotoPool = {
                R.drawable.sample_thumb_0, R.drawable.sample_thumb_1, R.drawable.sample_thumb_2,
                R.drawable.sample_thumb_3, R.drawable.sample_thumb_4, R.drawable.sample_thumb_5,
                R.drawable.sample_thumb_6, R.drawable.sample_thumb_7};

        private ArrayList<Integer> mPhotos = new ArrayList<Integer>();
        
        private LayoutInflater mInflater;
        
        public PhotoAdapter(Context c) {
            mContext = c;
            mInflater = LayoutInflater.from(mContext);
        }

        public int getCount() {
            return mPhotos.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            // Make an ImageView to show a photo
        	ViewHolder viewHolder = null;
        	if (convertView == null) {
        		convertView = mInflater.inflate(R.layout.saf_list_1_item, null);
        		viewHolder = new ViewHolder(convertView);
        		convertView.setTag(viewHolder);
				
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
        	viewHolder.imgView.setImageResource(mPhotos.get(position));
        	viewHolder.imgView.setAdjustViewBounds(true);
//        	viewHolder.imgView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.WRAP_CONTENT,
//                    LayoutParams.WRAP_CONTENT));
            // Give it a nice background
        	viewHolder.imgView.setBackgroundResource(R.drawable.picture_frame);
            return convertView;
        }

        private Context mContext;

        public void clearPhotos() {
            mPhotos.clear();
            notifyDataSetChanged();
        }
        
        public void addPhotos() {
            int whichPhoto = (int)Math.round(Math.random() * (mPhotoPool.length - 1));
            int newPhoto = mPhotoPool[whichPhoto];
            mPhotos.add(newPhoto);
            notifyDataSetChanged();
        }

    }
    
    class ViewHolder {
    	
    	@InjectView
    	public ImageView imgView;
    	 
    	public ViewHolder(View view) {
			// TODO SAF注册Adapter中的ViewHolder
    		Injector.injectInto(this, view);
		}
    	
    }
}