package com.example.socialmediaapp.RecyclerAdpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialmediaapp.ModelData.PostModel;
import com.example.socialmediaapp.R;

import java.util.ArrayList;

public class ProfileRecylcerViewAdpater extends RecyclerView.Adapter<ProfileRecylcerViewAdpater.viewHolder> {

    Context context;
    ArrayList<PostModel> arrayList;

    public ProfileRecylcerViewAdpater(Context context, ArrayList<PostModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ProfileRecylcerViewAdpater.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_recylcer, parent, false);
        return new ProfileRecylcerViewAdpater.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileRecylcerViewAdpater.viewHolder holder, int position) {
        PostModel uploadInfo = arrayList.get(position);

        if ((uploadInfo.getPostMessage()).contentEquals("none")) {
            Glide.with(context).load(uploadInfo.getImageURL()).into(holder.postImage);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView postImage;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.postImage);
        }
    }
}
