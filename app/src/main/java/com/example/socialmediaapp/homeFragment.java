package com.example.socialmediaapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class homeFragment extends Fragment {

    TextView txt;
    DatabaseReference databaseReference, profileref;
    RecyclerView recyclerView;
    ArrayList<PostModel> arrayList;
    RecylcerViewAdapterHome home;
    public static final String Database_Path = "socialmedia";
    String userid;
    private FirebaseAuth mAuth;
    int counter = 0;
    String user_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        arrayList = new ArrayList<PostModel>();
        mAuth = FirebaseAuth.getInstance();
        databaseReference =  FirebaseDatabase.getInstance().getReference(Database_Path).child("Posts");
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userid = currentUser.getUid();
//        profileref = FirebaseDatabase.getInstance().getReference(Database_Path).child("Profile").orderByChild("user");

        addRecylcer();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        addRecylcer();
    }

    private void addRecylcer() {
//        databaseReference.child("Posts").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    counter = (int) snapshot.getChildrenCount();
//
//                }
//                else {
//                    counter = 0;
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//            progressDialog = new ProgressDialog(getContext());
//            progressDialog.setMessage("Loading Data from Firebase Database");
//            progressDialog.show();

        Query query = databaseReference.orderByChild("postCounter");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot mysnap: snapshot.getChildren()){
                    PostModel model = mysnap.getValue(PostModel.class);
                    arrayList.add(model);
                }
                home = new RecylcerViewAdapterHome(getContext(), arrayList);
                recyclerView.setAdapter(home);
//                home.setOnItemClickListener(new RecylcerViewAdapterHome.OnItemCickListner() {
//                    @Override
//                    public void onItemClick(int position) {
//                        arrayList.get(position).getLikebtn(R.drawable.redheart);
//                        Toast.makeText(getContext(), arrayList.get(position).getPostMessage() +" "
//                                +arrayList.get(position).getImagename(), Toast.LENGTH_SHORT).show();
//                    }
//                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load Data", Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference.keepSynced(true);

        //        progressDialog.dismiss();

//        recyclerView.
    }
}


     /* for text

                        for (DataSnapshot mysnapshot : snapshot.getChildren()) {

                            PostDetails value = mysnapshot.getValue(PostDetails.class);

                            arrayList.add(value);
                        }

                        home = new RecylcerViewAdapterHome(getContext(), arrayList);
                        recyclerView.setAdapter(home);

      */

//                else {
//                    query1= databaseReference.orderByChild("ImagePost");
//                }








//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                nodeCount = (int) snapshot.getChildrenCount();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        addRecylcer();


//        imageRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//
////                    ImageUploadInfo imageUploadInfo = postSnapshot.getValue(ImageUploadInfo.class);
//                    ImageUploadInfo imageUploadInfo = postSnapshot.getValue(ImageUploadInfo.class);
//
//                    imagelist.add(imageUploadInfo);
//                }
//
//                imageAdapter = new HomeRecylcerImageAdapter(getContext(), imagelist);
//
//                recyclerView.setAdapter(imageAdapter);
//
//                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                progressDialog.dismiss();
//            }
//        });

//        imageRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
////                    String m = snapshot.child("").getValue().toString();
////
////                    if (ref.child("").equals(postMessageinfo)) {
//
//
////                    }
//
////                    else if (m ==)
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                progressDialog.dismiss();
//            }
//        });



    /*

    public DatabaseReference getMessageRef() {
    /*
        messageRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    PostDetails postDetails = dataSnapshot.getValue(PostDetails.class);

                    list.add(postDetails);
                }

                adapter = new HomeRecyclerAdapter(getContext(), list);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });

     */
//        messageRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
////                    String m = snapshot.child("").getValue().toString();
////
////                    if (ref.child("").equals(postMessageinfo)) {
//
//                adapter.clear();
//
////                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//
////                    PostDetails postDetails = dataSnapshot.getValue(PostDetails.class);
//
//                    String m = snapshot.child("").getValue().toString();
//                    HashMap<String, Object> hashMap = new HashMap<>();
//
//                    hashMap.put("postMessage", m);
//                    messageRef.setValue(hashMap);
//
//                    adapter = new ArrayAdapter<Object>(getContext(), R.layout.home_recylcer, hashMap);
//
//                recyclerView.setAdapter(adapter);
////                    }
//
////                    else if (m ==)
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                progressDialog.dismiss();
//            }
//        });
//        return messageRef;
//    }



/*

    public DatabaseReference getImageRef() {

        /*
        imageRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    ImageUploadInfo imageUploadInfo = postSnapshot.getValue(ImageUploadInfo.class);

                    imagelist.add(imageUploadInfo);
                }

                imageAdapter = new HomeRecylcerImageAdapter(getContext(), imagelist);

                recyclerView.setAdapter(imageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });

        */
//        imageRef.orderByChild("ImagePost").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
////                    String m = snapshot.child("").getValue().toString();
////
////                    if (ref.child("").equals(postMessageinfo)) {
//
//                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//
//                    ImageUploadInfo imageUploadInfo = postSnapshot.getValue(ImageUploadInfo.class);
//
//                    imagelist.add(imageUploadInfo);
//                }
//
//                imageAdapter = new HomeRecylcerImageAdapter(getContext(), imagelist);
//
//                recyclerView.setAdapter(imageAdapter);
////                    }
//
////                    else if (m ==)
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                progressDialog.dismiss();
//            }
//        });
//        return imageRef;
//    }


//        for (int i =0; i<=nodeCount; i++){
////
////            if (databaseReference.child(postMessageinfo).addValueEventListener((ValueEventListener) messageRef).onDataChange(sna); {
////                   getMessageRef();
////                    break;
////                }
//
////            else if (databaseReference.child(postImageinfo) == null){
////                getMessageRef();
////                break;
////            }
//        }

//        Query query = databaseReference.orderByChild(postMessageinfo);
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                getImageRef();
//                for (DataSnapshot data : snapshot.getChildren()) {
//                    String m = data.getValue(PostDetails.class).getPostMessage();
//                    //If email exists then toast shows else store the data on new key
//                    if (m != null) {
//                        getMessageRef();
//                    }
////                    } else if (){
////                        Toast.makeText(ChatListActivity.this, "E-mail already exists.", Toast.LENGTH_SHORT).show();
////                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                progressDialog.dismiss();
//            }
//        });
/*
        Query query1 = databaseReference.orderByChild(postMessageinfo);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getMessageRef();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });

 */
/* try catch

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
//                    HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.getValue();
//
//                    for (String key : dataMap.keySet()){
//
//                        Object data = dataMap.get(key);
//                        if (data.equals(imud)){
//
//                        try{
//                            HashMap<String, Object> userData = (HashMap<String, Object>) data;

                            ImageUploadInfo imageUploadInfo = new ImageUploadInfo();
                            imagelist.add(imageUploadInfo);
                            imageAdapter = new HomeRecylcerImageAdapter(getContext(), imagelist);
                            recyclerView.setAdapter(imageAdapter);


//                            User mUser = new User((String) userData.get("name"), (int) (long) userData.get("age"));
//                            addTextToView(mUser.getName() + " - " + Integer.toString(mUser.getAge()));

                        }
//                catch (ClassCastException cce) {
//                        }

//                        else if (data.equals(pd)){
//
//// If the object can’t be casted into HashMap, it means that it is of type String.
//
//                            try{
//                                HashMap<String, Object> userData = (HashMap<String, Object>) data;
//                                PostDetails postDetails = new PostDetails((String) userData.get("postMessage"));
////                                list.add(postDetails);
////                                String mString = String.valueOf(dataMap.get(key));
//                                list.add(postDetails);
//                                adapter = new HomeRecyclerAdapter(getContext(), list);
//                                recyclerView.setAdapter(adapter);
//                                break;
//
//                            }catch (ClassCastException cce2){
//
//                            }
//                        }

                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

 */


//        if (!(messageRef.get() == null)) {
//            getMessageRef();
//        }
//
////        else if (!(imageRef.get() ==null))  {
//        if(!(imageRef.get() == null)) {
//            getImageRef();
//        }






//        if (postMessageinfo.contentEquals("MessagePost")){
//            Query query = (Query) rootRef.child(postMessageinfo);
//                if ((rootRef.child("").toString().contentEquals(postMessageinfo))){
//        .toString().contentEquals(postMessageinfo)){
//                    .equalTo(postinfo);
//                    .startAt(postMessageinfo);

//                    String m =  rootRef.child(postMessageinfo).
//        Query queryimage =  rootRef.orderByChild(postImageinfo);
//
//        if (!(Query.equals(querymessage) == null)) {

//            rootRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    databaseReference.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.

                            //                String ID = dataSnapshot.getValue(String.class);




                            //                p.clear();
                            //                for (int i=0; i<=dataSnapshot.getChildrenCount(); i++) {
                            //                    pojo arr = dataSnapshot.child("messageSave").getValue(pojo.class);
                            //                    p.add(arr);
                            //                }
                            //
                            //                adapter = new HomeRecyclerAdapter(getContext(),p);
                            //                recycler.setAdapter(adapter);
                            //                txt.setVisibility(View.GONE);
                            //                recycler.setVisibility(View.VISIBLE);
                            //                progressDialog.dismiss();
                            //                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                            //                Log.d(TAG, "Value is: " + value);


//                        @Override
//                        public void onCancelled(DatabaseError error) {
//                            // Failed to read value
//                            progressDialog.dismiss();
//                        }
//                    });
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(getContext(), "Failed !!!!", Toast.LENGTH_SHORT).show();
//                }
//            } ;
//            querymessage.addValueEventListener(messagevalueEventListener);
//
//        }
//                        else if (Query == queryimage) {
//
//
////            Query queryImage = (Query) rootRef.child(postImageinfo);
////                    .equalTo(postinfo);
////                    .startAt(postImageinfo);
//            ValueEventListener imagevalueEventListener = new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    databaseReference.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                            // Hiding the progress dialog.
//                            progressDialog.dismiss();
//
//                        }
//                    });
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(getContext(), "Failed !!!!", Toast.LENGTH_SHORT).show();
//                }
//            };
//            queryimage.addValueEventListener(imagevalueEventListener);
//        }






//            ValueEventListener.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
////                    if (!snapshot.exists()){
//
//                       /*
//                        For Post messages
//                       */
//
//                        // Read from the database
//
//
////                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//        else if (postMessageinfo.contentEquals("ImagePost")) {

////            query.addListenerForSingleValueEvent(new ValueEventListener() {
////                @Override
////                public void onDataChange(@NonNull DataSnapshot snapshot) {
//////                            if (!snapshot.exists()){
////
////                        /*
////                        For Images
////                        */
////
////                    // Adding Add Value Event Listener to databaseReference.
////
//////                            }
////                }
////
////                @Override
////                public void onCancelled(@NonNull DatabaseError error) {
////
////                }
////            });
//        }
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (!snapshot.exists()){
//
//                       /*
//                        For Post messages
//                       */
//
//                        // Read from the database
//                        databaseReference.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            // This method is called once with the initial value and again
//                            // whenever data at this location is updated.
//
//                            //                String ID = dataSnapshot.getValue(String.class);
//
//                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
//                                PostDetails postDetails = snapshot.getValue(PostDetails.class);
//
//                                list.add(postDetails);
//                            }
//
//                            adapter = new HomeRecyclerAdapter(getContext(), list);
//                            recyclerView.setAdapter(adapter);
//
//
//                            //                p.clear();
//                            //                for (int i=0; i<=dataSnapshot.getChildrenCount(); i++) {
//                            //                    pojo arr = dataSnapshot.child("messageSave").getValue(pojo.class);
//                            //                    p.add(arr);
//                            //                }
//                            //
//                            //                adapter = new HomeRecyclerAdapter(getContext(),p);
//                            //                recycler.setAdapter(adapter);
//                            //                txt.setVisibility(View.GONE);
//                            //                recycler.setVisibility(View.VISIBLE);
//                            //                progressDialog.dismiss();
//                            //                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
//                            //                Log.d(TAG, "Value is: " + value);
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError error) {
//                            // Failed to read value
//                            progressDialog.dismiss();
//                        }
//                    });
//
//                }
//                else {
//
//                        /*
//                        For Images
//                        */
//
//                        // Adding Add Value Event Listener to databaseReference.
//                        databaseReference.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//
//                                    ImageUploadInfo imageUploadInfo = postSnapshot.getValue(ImageUploadInfo.class);
//
//                                    imagelist.add(imageUploadInfo);
//                                }
//
//                                imageAdapter = new HomeRecylcerImageAdapter(getContext(), imagelist);
//
//                                recyclerView.setAdapter(imageAdapter);
//
//
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                                // Hiding the progress dialog.
//                                progressDialog.dismiss();
//
//                            }
//                        });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), "Failed !!!!", Toast.LENGTH_SHORT).show();
//
//            }
//        };

//        query.addChildEventListener((ChildEventListener) valueEventListener);