package com.example.socialmediaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        BottomNavigationView nav = findViewById(R.id.nav);
        FrameLayout frameLayout = findViewById(R.id.frameLayout);

        homeFragment homef = new homeFragment();
        AddPostFragment addPostFragment = new AddPostFragment();
        ProfileFragment profileFragment = new ProfileFragment();

        change_fragment(homef);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.home:
                        selectedFragment = homef;
                        break;


                    case R.id.add_post:
                        selectedFragment = addPostFragment;
                        break;

                    case R.id.profile:
                        selectedFragment = profileFragment;
                        break;
                }
                change_fragment(selectedFragment);
                return true;
            }
        });
    }

    public void change_fragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        int id = item.getItemId();
//
//        switch (id) {
//            case R.id.editProfile:
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frameLayout, new EditProfileFragment()).commit();
//                return true;
//            case R.id.logout:
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(this, SignIn.class));
//                Toast.makeText(this, "Logout Successfully..", Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}