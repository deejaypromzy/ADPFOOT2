package com.projectwork.adp.adpfoot;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

class LawsAdapter extends RecyclerView.Adapter<LawsAdapter.ChildViewHolder> {

    //Member variables
    private ArrayList<Laws_Database> mSportsData;
    private Context mContext;

    LawsAdapter(Context context, ArrayList<Laws_Database> sportsData) {
        this.mSportsData = sportsData;
        this.mContext = context;


    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChildViewHolder(mContext, LayoutInflater.from(mContext).
                inflate(R.layout.laws_template, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {

        //Get the current sport
        Laws_Database currentChild = mSportsData.get(position);

        //Bind the data to the views
        holder.bindTo(currentChild);

    }

    @Override
    public int getItemCount() {
        return mSportsData.size();
    }


    /**
     * ChildViewHolder class that represents each row of data in the RecyclerView
     */
    static class ChildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title, sub_title;
        private ImageView imgLaw;
        private Context mContext;
        private Laws_Database mCurrentChild;


        ChildViewHolder(final Context context, View itemView) {
            super(itemView);


            //get views
            imgLaw = itemView.findViewById(R.id.lawImg);
            title = itemView.findViewById(R.id.title);
            sub_title = itemView.findViewById(R.id.subtitle);

            itemView.setOnClickListener(this);
            mContext = context;


        }


        void bindTo(Laws_Database currentChild) {

            //Populate the textviews with data
            title.setText(currentChild.getTitle());
            sub_title.setText((currentChild.getSub_title()));
            //Get the current sport
            mCurrentChild = currentChild;


            GlideApp.with(mContext)
                    .load(currentChild.
                            getImg())
                    .placeholder(R.drawable.law_bg)
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .into(imgLaw);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext,law_one.class);
            intent.putExtra("title",mCurrentChild.getTitle());
            intent.putExtra("sub_title",mCurrentChild.getSub_title());
            intent.putExtra("audio",mCurrentChild.getAudio());
            intent.putExtra("video",mCurrentChild.getVideo());
            intent.putExtra("image",mCurrentChild.getImg());
            intent.putExtra("details",mCurrentChild.getDetail());
            mContext.startActivity(intent);

        }
    }
}