/*
 * Copyright (C) 2016 Google Inc.
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

package com.projectwork.adp.adpfoot;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/***
 * The adapter class for the RecyclerView, contains the sports data
 */
class GlossaryAdapter extends RecyclerView.Adapter<GlossaryAdapter.ChildViewHolder>  {

    //Member variables
    private GradientDrawable mGradientDrawable;
    private ArrayList<Glossory_Database> mSportsData;
    private Context mContext;

    /**
     * Constructor that passes in the sports data and the context
     * @param sportsData ArrayList containing the sports data
     * @param context Context of the application
     */
    GlossaryAdapter(Context context, ArrayList<Glossory_Database> sportsData) {
        this.mSportsData = sportsData;
        this.mContext = context;


    }


    /**
     * Required method for creating the viewholder objects.
     * @param parent The ViewGroup into which the new View is added after it is
     *               bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly create ChildViewHolder.
     */
    @Override
    public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChildViewHolder(mContext, LayoutInflater.from(mContext).
                inflate(R.layout.glossoarytemplate, parent, false), mGradientDrawable);
    }

    /**
     * Required method that binds the data to the viewholder.
     * @param holder The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(ChildViewHolder holder, int position) {

        //Get the current sport
        Glossory_Database currentGlossoryDatabase = mSportsData.get(position);

        //Bind the data to the views
        holder.bindTo(currentGlossoryDatabase);

    }


    /**
     * Required method for determining the size of the data set.
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mSportsData.size();
    }


    /**
     * ChildViewHolder class that represents each row of data in the RecyclerView
     */
    static class ChildViewHolder extends RecyclerView.ViewHolder {

        private TextView name,definition;
        private Context mContext;
        private Glossory_Database mCurrentGlossoryDatabase;



        /**
         * Constructor for the ChildViewHolder, used in onCreateViewHolder().
         * @param itemView The rootview of the list_item.xml layout file
         */
        ChildViewHolder(final Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);

            //Initialize the views
            name = itemView.findViewById(R.id.name);
            definition = itemView.findViewById(R.id.definition);

            mContext = context;


        }


        void bindTo(Glossory_Database currentGlossoryDatabase){
            //Populate the textviews with data
            name.setText(currentGlossoryDatabase.getName());
            definition.setText((currentGlossoryDatabase.getDefinition()));

            //Get the current sport
            mCurrentGlossoryDatabase = currentGlossoryDatabase;


            //Load the images into the ImageView using the Glide library
//            Glide.with(mContext).load(currentGlossoryDatabase.
//                    getImageResource()).placeholder(mGradientDrawable).into(mSportsImage);
        }
    }
}
