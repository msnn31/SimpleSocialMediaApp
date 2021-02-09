package com.example.socialmediaapp.RecyclerAdpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialmediaapp.ModelData.PostModel;
import com.example.socialmediaapp.ModelData.ProfileDataModel;
import com.example.socialmediaapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecylcerViewAdapterHome extends RecyclerView.Adapter<RecylcerViewAdapterHome.viewHolder> {

    Context context;
    ArrayList<PostModel> arrayList;
    DatabaseReference profileref;
    public static final String Database_Path = "socialmedia";
    String user_name;
//    private OnItemCickListner mListener;

    public RecylcerViewAdapterHome(Context context, ArrayList<PostModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

//    public void setOnItemClickListener(OnItemCickListner listener){
//        mListener = listener;
//    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recylcer, parent, false);
        return new viewHolder(view);
//        return new viewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        PostModel uploadInfo = arrayList.get(position);

        profileref = FirebaseDatabase.getInstance().getReference(Database_Path).child("Profile");

        profileref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot mysnap : snapshot.getChildren()) {
                        ProfileDataModel pmodel = mysnap.getValue(ProfileDataModel.class);
                        user_name = pmodel.getUsername();
                    }
                    holder.userName.setText(user_name.trim());
                }
                else {
                    user_name = "none";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        holder.userName.setText(uploadInfo.getUsername());

        if ((uploadInfo.getImageURL()).contentEquals("none")){
            holder.ShowPostMessageTextView.setText(uploadInfo.getPostMessage());
            holder.ShowPostMessageTextView.setVisibility(View.VISIBLE);

        }
        else if ((uploadInfo.getPostMessage()).contentEquals("none")) {
            Glide.with(context).load(uploadInfo.getImageURL()).into(holder.viewImage);
            holder.viewImage.setVisibility(View.VISIBLE);
            holder.Caption.setText(uploadInfo.getImagename());
            holder.Caption.setVisibility(View.VISIBLE);
        }

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "liked ", Toast.LENGTH_SHORT).show();
//            }
//        });

        //For Like Count
//        if (uploadInfo.getLike() == 0){
//            holder.likeTxt.setVisibility(View.GONE);
//        }
//        else if (uploadInfo.getLike() >= 1){
//            holder.likeTxt.setText(String.valueOf(uploadInfo.getLike()) +" Likes");
//        }

        Date currentTime = Calendar.getInstance().getTime();

        int postTime = String.valueOf(currentTime).compareTo(uploadInfo.getPostTime());

        holder.time.setText(postTime + " mins ago");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

//    public interface OnItemCickListner{
//        void onItemClick(int position);
//    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        ImageView viewImage;
        TextView Caption;
        TextView ShowPostMessageTextView;
//        ImageButton likeBtn;
        TextView likeTxt;
        TextView time;
        ImageView likeBtn;
//        DatabaseReference databaseReference;
//        public static final String Database_Path = "socialmedia";

        public viewHolder(@NonNull View itemView) {
//        public viewHolder(@NonNull View itemView, OnItemCickListner listener) {
            super(itemView);
            userName = itemView.findViewById(R.id.usernameTextview);
            viewImage = itemView.findViewById(R.id.viewImage);
            Caption = itemView.findViewById(R.id.Caption);
            ShowPostMessageTextView = itemView.findViewById(R.id.ShowPostMessageTextView);
//            likeBtn = itemView.findViewById(R.id.likeBtn);
            likeTxt = itemView.findViewById(R.id.likeTxt);
            time = itemView.findViewById(R.id.time);
            likeBtn = itemView.findViewById(R.id.likeBtn);

//            likeBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null){
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION){
//                            listener.onItemClick(position);
//                        }
//                    }
//                }
//            });
        }

//        public void postlike(){
//            likeBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    int likeItem = getItemCount();
//                    databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path).child("Posts").
//
//
//                    HashMap<String,Object> updatemodel=new HashMap<>();
//                    updatemodel.put("username",username);
//                    updatemodel.put("userBio",userbio);
//                    updatemodel.put("fname",firstname);
//                    updatemodel.put("lname",lastname);
//                    updatemodel.put("email",emailid);
//                    updatemodel.put("phoneNo",phoneno);
//                    updatemodel.put("imageURL",downloadUrl.toString());
//                    showref.updateChildren(updatemodel, new DatabaseReference.CompletionListener() {
//                        @Override
//                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                            Toast.makeText(getContext(), "Database Values updated...", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                }
//            });
//        }
    }
}