package com.example.socialmediaapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AddPostFragment extends Fragment {

    AlertDialog alertDialog;
    FragmentTransaction transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_post, container, false);

        alertDialogBox();

        transaction= getActivity().getSupportFragmentManager().beginTransaction();
        return view;
    }

    public void alertDialogBox() {

        String[] actions = {"Write a Post", "Upload a Image", "Cancel"};

        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("New Post");
        dialog.setItems(actions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        transaction.replace(R.id.frameLayout, new writePostFragment());
                        transaction.commit();
                        break;
                    case 1:
                        transaction.replace(R.id.frameLayout, new uploadImageFragment());
                        transaction.commit();
                        break;
                    case 2:
                        dialog.dismiss();
                        transaction.replace(R.id.frameLayout, new homeFragment());
                        transaction.commit();
                        break;
                }
            }
        });

        alertDialog = dialog.create();
        alertDialog.show();
    }

    @Override
    public void onStop() {
        alertDialog.dismiss();
        super.onStop();
    }
}