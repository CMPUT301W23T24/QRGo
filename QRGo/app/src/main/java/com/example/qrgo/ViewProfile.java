package com.example.qrgo;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ViewProfile extends AppCompatActivity implements EditProfileFragment.OnFragmentInteractionListener {
    Button back;

    Button forward;

    TextView dId;
    TextView userName;
    TextView name;
    TextView email;
    TextView phoneNum;

    String deviceId;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_view);

        back= findViewById(R.id.backButton);
        forward= findViewById(R.id.editProfileButton);

        dId= findViewById(R.id.inputId);
        userName= findViewById(R.id.inputUserName);
        name= findViewById(R.id.inputNameView);
        email= findViewById(R.id.inputEmailView);
        phoneNum= findViewById(R.id.inputPhoneNum);

        deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        User user1= new User(deviceId);
        user1.getValuesFromDb(deviceId, new User.OnUserLoadedListener() {
            @Override
            public void onUserLoaded(User user) {
                dId.setText(user1.getDeviceID());
                userName.setText(user1.getUserName());
                name.setText(user1.getName());
                email.setText(user1.getEmail());
                phoneNum.setText(String.valueOf(user1.getPhoneNum()));
            }
        });
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            new EditProfileFragment(user1).show(getSupportFragmentManager(), "Edit Profile");
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    public void onOkkPressed(User newUser, String userName, String name, String email, Integer phoneNum) {
        newUser.setUserName(userName);
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPhoneNum(phoneNum);
        newUser.updateDb();
    }
}
