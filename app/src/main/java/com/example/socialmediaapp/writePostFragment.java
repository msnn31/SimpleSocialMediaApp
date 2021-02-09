package com.example.socialmediaapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.socialmediaapp.ModelData.PostModel;
import com.example.socialmediaapp.ModelData.ProfileDataModel;
import com.example.socialmediaapp.RecyclerAdpter.RecylcerViewAdapterHome;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class writePostFragment extends Fragment {

    //    EditText etpostMessage;
//    Button postBtn;
//    DatabaseReference databaseReference;
//    String ID;

    EditText message;
    String userid;
    FragmentTransaction transaction;
    private FirebaseAuth mAuth;
    Date currentTime;
    SimpleDateFormat formatter;

    public long postCounter = 0;

//    Declaring String variable ( In which we are storing firebase server URL ).
//    public static final String Firebase_Server_URL = "https://fir-example-c5e8c.firebaseio.com/";

    // Declaring String variables to store name & phone number get from EditText.
    String messageHolder;

//    FirebaseDatabase firebase;

    DatabaseReference databaseReference;
    int MessagePostCounter = 1;

    // Root Database Name for Firebase Database.
    public static final String Database_Path = "socialmedia";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_write_post, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);
        transaction = getActivity().getSupportFragmentManager().beginTransaction();
        message = view.findViewById(R.id.message);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userid = currentUser.getUid();
        setHasOptionsMenu(true);

        databaseReference.child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    postCounter = snapshot.getChildrenCount();

                }
                else {
                    postCounter = 0;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    private void uploadPost() {



//                String message = etpostMessage.getText().toString().trim();
//                if (!message.isEmpty()) {
//
//                    postModel pmodel = new postModel(message);
//                    databaseReference.child(ID).setValue(pmodel);
//                    Toast.makeText(writePost.this, "Post Updated", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(writePost.this, "Please write a Message", Toast.LENGTH_SHORT).show();
//                }


        GetDataFromEditText();

        currentTime = Calendar.getInstance().getTime();

        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        PostModel postModel = new PostModel(postCounter, userid,"none","none",messageHolder,0, formatter.format(currentTime));

        // Getting the ID from firebase database.
        String PostRecordIDFromServer = databaseReference.push().getKey();

        // Adding the both name and number values using student details class object using ID.
//                databaseReference.child("MessagePost").child(PostRecordIDFromServer).setValue(postDetails);
//                databaseReference.child("Posts"+MainActivity.counter).setValue(postModel);
        databaseReference.child("Posts").child(PostRecordIDFromServer).setValue(postModel);
//                databaseReference.setValue(postDetails);

        // Showing Toast message after successfully data submit.
        Toast.makeText(getContext(),"Post Updated.", Toast.LENGTH_LONG).show();

        transaction.replace(R.id.frameLayout, new homeFragment());
        transaction.commit();


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.post_menu, menu);
//        getContext().getMenuInflater().inflate(R.menu.profile_menu, menu);
//        return true;

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.submitpost:
                uploadPost();
                return true;

            case R.id.cancelpost:
                transaction.replace(R.id.frameLayout, new homeFragment());
                transaction.commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void GetDataFromEditText(){

        messageHolder = message.getText().toString().trim();
    }
}