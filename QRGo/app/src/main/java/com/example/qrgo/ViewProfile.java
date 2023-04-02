package com.example.qrgo;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * Views the profile of the user who has the phone
 */
public class ViewProfile extends AppCompatActivity implements EditProfileFragment.OnFragmentInteractionListener {
    Button back;
    Button forward;
    String deviceId;
    String TAG="test ";

    /**
     * Creates the view of the userProfile
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_view);

        back= findViewById(R.id.backButton);
        forward= findViewById(R.id.editProfileButton);

        deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        User user1= new User(deviceId);
        user1.getValuesFromDb(deviceId, new User.OnUserLoadedListener() {
            /**
             * gets the values from the DB
             * @param user gets the information of the user
             */
            @Override
            public void onUserLoaded(User user) {
                updateTextView(user1);
            }
        });
        forward.setOnClickListener(new View.OnClickListener() {
            /**
             * creates the edit text fragment for the username profiles
             * @param view
             */
            @Override
            public void onClick(View view) {
            new EditProfileFragment(user1).show(getSupportFragmentManager(), "Edit Profile");
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            /**
             * closes the current view
             * @param view
             */
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Once pressed the profile will set up the necessary information
     * @param newUser
     * @param userName
     * @param name
     * @param email
     * @param phoneNum
     */
    @Override
    public void onOkkPressed(User newUser, String userName, String name, String email, Integer phoneNum) {
        newUser.setUserName(userName);
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPhoneNum(phoneNum);

        Boolean result= newUser.checkUniqueness(newUser.getDeviceID());

        if (!result){
            newUser.updateDb();
            Toast.makeText(getApplicationContext(), "Your username wasn't unique, please try again!", Toast.LENGTH_SHORT).show();
            new EditProfileFragment(newUser).show(getSupportFragmentManager(), "Edit Profile");
        }
        else{
            newUser.updateDb();
            updateTextView(newUser);
        }
    }
    /**
     * when called, it updates the textviews on the page
     * @param user
     */
    public void updateTextView(User user){
        TextView number1;
        TextView number2;
        TextView number3;
        TextView number4;
        TextView number5;

        number1= findViewById(R.id.inputId);
        number2= findViewById(R.id.inputUserName);
        number3= findViewById(R.id.inputNameView);
        number4= findViewById(R.id.inputEmailView);
        number5= findViewById(R.id.inputPhoneNum);

        number1.setText(user.getDeviceID());
        number2.setText(user.getUserName());
        number3.setText(user.getName());
        number4.setText(user.getEmail());
        number5.setText(String.valueOf(user.getPhoneNum()));
    }

}
