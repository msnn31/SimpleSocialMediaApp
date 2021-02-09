package com.example.socialmediaapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.socialmediaapp.ModelData.PostModel;
import com.example.socialmediaapp.ModelData.ProfileDataModel;
import com.example.socialmediaapp.RecyclerAdpter.ProfileRecylcerViewAdpater;
import com.example.socialmediaapp.RecyclerAdpter.RecylcerViewAdapterHome;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {

    FrameLayout ProfileFrame;
    ImageView ProfileImage;
    TextView userName;
    DatabaseReference ref;
    TextView userBio;
    RecyclerView profilerecyclerView;
    ArrayList<PostModel> arrayList;
    ProfileRecylcerViewAdpater home;
    String userid;
    private FirebaseAuth mAuth;
    FragmentTransaction transaction;
    public static final String Database_Path = "socialmedia";

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profilerecyclerView = view.findViewById(R.id.profilerecylc);
        profilerecyclerView.setHasFixedSize(true);
        profilerecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        arrayList = new ArrayList<PostModel>();


        ProfileFrame = view.findViewById(R.id.ProfileFrame);
        ProfileImage = view.findViewById(R.id.ProfileImage);
        userName = view.findViewById(R.id.userName);
        userBio = view.findViewById(R.id.userBio);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userid = currentUser.getUid();
        transaction= getActivity().getSupportFragmentManager().beginTransaction();

        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.frameLayout, new EditProfileFragment());
                transaction.commit();
            }
        });

        ref = FirebaseDatabase.getInstance().getReference(Database_Path).child("Profile");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (isAdded()) {
                        for (DataSnapshot mysnap : snapshot.getChildren()) {
                            ProfileDataModel model = mysnap.getValue(ProfileDataModel.class);


                            userName.setText(model.getUsername());
                            userBio.setText(model.getUserBio());
//                    ProfileImage.setImageResource(model.getImageURL());
                            Glide.with(getActivity()).load(model.getImageURL()).into(ProfileImage);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed..", Toast.LENGTH_SHORT).show();
            }
        });

        setHasOptionsMenu(true);

        addRecylcer();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.profile_menu, menu);
//        getContext().getMenuInflater().inflate(R.menu.profile_menu, menu);
//        return true;

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.editProfile:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, new EditProfileFragment()).commit();
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), SignIn.class));
                Toast.makeText(getContext(), "Logout Successfully..", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addRecylcer() {
//            progressDialog = new ProgressDialog(getContext());
//            progressDialog.setMessage("Loading Data from Firebase Database");
//            progressDialog.show();
        Query query = FirebaseDatabase.getInstance().getReference(Database_Path).child("Posts").orderByChild("postMessage").equalTo("none");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot mysnap: snapshot.getChildren()){
                    PostModel model = mysnap.getValue(PostModel.class);
                    arrayList.add(model);
                }
                home = new ProfileRecylcerViewAdpater(getContext(), arrayList);
                profilerecyclerView.setAdapter(home);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load Data", Toast.LENGTH_SHORT).show();
            }
        });

//        progressDialog.dismiss();
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ref.removeEventListener();
//    }
}