package com.example.socialmediaapp;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.socialmediaapp.ModelData.PostModel;
import com.example.socialmediaapp.ModelData.ProfileDataModel;
import com.example.socialmediaapp.RecyclerAdpter.RecylcerViewAdapterHome;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;


public class EditProfileFragment extends Fragment {

    private EditText etFirstName, etLastName, etEmail, etPhone, etUserName, etUserBio;
    private ImageView ProfilePic;
    private Button UpdateBtn, CancelBtn;
    Context context;

    String firstname, lastname, emailid, phoneno, username, userbio;
    DatabaseReference databaseReference, showref;
    public static final String Database_Path = "socialmedia";
    String Storage_Path = "Profile_pic/";
    Uri FilePathUri;
    StorageReference storageReference;
    int Image_Request_Code = 77;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    FragmentTransaction transaction;
    ProfileFragment profileFragment;
    FrameLayout editProFrame;
    String userid;
    ArrayList<ProfileDataModel> arrayList;
    AlertDialog.Builder dialog;
    AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        etUserName = view.findViewById(R.id.etUserName);
        etUserBio = view.findViewById(R.id.etUserBio);
        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        etEmail = view.findViewById(R.id.etEmail);
        etPhone = view.findViewById(R.id.etPhone);
        ProfilePic = view.findViewById(R.id.ProfilePic);
//        UpdateBtn = view.findViewById(R.id.UpdateBtn);
        CancelBtn = view.findViewById(R.id.CancelBtn);

//        etUserName = getActivity().findViewById(R.id.etUserName);
//        etUserBio = getActivity().findViewById(R.id.etUserBio);
//        etFirstName = getActivity().findViewById(R.id.etFirstName);
//        etLastName = getActivity().findViewById(R.id.etLastName);
//        etEmail = getActivity().findViewById(R.id.etEmail);
//        etPhone = getActivity().findViewById(R.id.etPhone);
//        ProfilePic = getActivity().findViewById(R.id.ProfilePic);
        UpdateBtn = view.findViewById(R.id.UpdateBtn);
//        CancelBtn = getActivity().findViewById(R.id.CancelBtn);


        transaction= getActivity().getSupportFragmentManager().beginTransaction();
        profileFragment = new ProfileFragment();
        editProFrame = view.findViewById(R.id.editProFrame);

        storageReference = FirebaseStorage.getInstance().getReference().child("Social_media/");
        progressDialog = new ProgressDialog(getActivity());
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userid = currentUser.getUid();
        arrayList = new ArrayList<ProfileDataModel>();

        showProfileData();

        ProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);
            }
        });

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Updating...");
                progressDialog.show();
                UploadProfileImageFile();

            }
        });
//        .setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressDialog.setMessage("Updating...");
//                progressDialog.show();
//                UploadProfileImageFile();
//
//                String PostRecordIDFromServer = databaseReference.push().getKey();
//            }
//        });

        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.frameLayout, profileFragment);
                transaction.commit();
            }
        });
        
        return view;
    }
    public void UploadProfileImageFile() {

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {

            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            storageReference2nd.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {


                                    GetDataFromEdittext();

                                    final Uri downloadUrl = uri;
                                    // Getting image name from EditText and store into string variable.

                                    if (!(username.isEmpty() && userbio.isEmpty() && firstname.isEmpty()
                                            && lastname.isEmpty() && emailid.isEmpty() && phoneno.isEmpty())) {

//                                        ProfileDataModel pmodel = new ProfileDataModel();
//
//                                        pmodel.setFname(firstname);

                                        ProfileDataModel dataModel = new ProfileDataModel(username, userbio, firstname,
                                                lastname, emailid, phoneno,
                                                downloadUrl.toString());
                                        // Getting image upload ID.
                                        String ImageUploadId = databaseReference.push().getKey();

                                        // Adding image upload id s child element into databaseReference.

                                        databaseReference.child("Profile").child(userid).setValue(dataModel);
                                    }
                                    else {
                                        Toast.makeText(getActivity(), "Please provide all details", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });

                            // Hiding the progressDialog after done uploading.
//                            progressDialog.dismiss();

                            // Showing toast message after done uploading.
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Profile Updated ", Toast.LENGTH_LONG).show();
                            showalertdiaglog();
                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Hiding the progressDialog.
                            progressDialog.dismiss();

                            // Showing exception erro message.
                            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            progressDialog.setTitle("Profile is Updating...");

                        }
                    });
        } else {

            Toast.makeText(getContext(), "Please provide all details", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }

    private void showProfileData() {
/*
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                arrayList.clear();
                for (DataSnapshot mysnap: snapshot.getChildren()){
                    ProfileDataModel profileDataModel = mysnap.getValue(ProfileDataModel.class);

                    etUserName.setText(profileDataModel.getUsername());
                    etUserBio.setText(profileDataModel.getUserBio());
                    etFirstName.setText(profileDataModel.getFname());
                    etLastName.setText(profileDataModel.getLname());
                    etEmail.setText(profileDataModel.getEmail());
                    etPhone.setText(profileDataModel.getPhoneNo());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load Data", Toast.LENGTH_SHORT).show();
            }
        });

 */

        showref = FirebaseDatabase.getInstance().getReference(Database_Path).child("Profile");

        showref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot mysnap: snapshot.getChildren()){
                        ProfileDataModel model = mysnap.getValue(ProfileDataModel.class);
                        etUserName.setText(model.getUsername());
                        etUserBio.setText(model.getUserBio());
                        etFirstName.setText(model.getFname());
                        etLastName.setText(model.getLname());
                        etEmail.setText(model.getEmail());
                        etPhone.setText(model.getPhoneNo());
                        Glide.with(getContext()).load(model.getImageURL()).into(ProfilePic);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProfile() {


                progressDialog.setMessage("Updating...");
                progressDialog.show();
                UploadProfileImageFile();

                String PostRecordIDFromServer = databaseReference.push().getKey();

                // Adding the both name and number values using student details class object using ID.
//                databaseReference.child("MessagePost").child(PostRecordIDFromServer).setValue(postDetails);
//                databaseReference.child("MessagePost").setValue(postDetails);
                

                // Showing Toast message after successfully data submit.
//                Toast.makeText(getContext(),"Profile Updated", Toast.LENGTH_LONG).show();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                ProfilePic.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getActivity().getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    public void GetDataFromEdittext(){

        username = etUserName.getText().toString().trim();
        userbio = etUserBio.getText().toString().trim();
        firstname = etFirstName.getText().toString().trim();
        lastname = etLastName.getText().toString().trim();
        emailid = etEmail.getText().toString().trim();
        phoneno = etPhone.getText().toString().trim();
    }
    
    // Creating UploadImageFileToFirebaseStorage method to upload image on storage.

    public void showalertdiaglog(){

        dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Profile Updated Successfully");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                transaction.replace(R.id.frameLayout, new ProfileFragment());
                transaction.commit();
//                transaction.replace(R.id.frameLayout, profileFragment);
//                transaction.commit();
            }
        });

        alertDialog = dialog.create();
        alertDialog.show();
    }
}