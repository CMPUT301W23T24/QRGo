package com.example.qrgo;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ViewOtherProfile  extends AppCompatActivity {
    Button back;


    TextView userName;
    TextView name;
    TextView email;
    TextView phoneNum;

    String deviceId;

    /**
     * Creates the view of the userProfile
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_other_profile);

        back= findViewById(R.id.BackButton);

        userName= findViewById(R.id.InputUserName);
        name= findViewById(R.id.InputNameView);

        deviceId = getIntent().getStringExtra("deviceId");

        if(deviceId != null) {
            User user1 = new User(deviceId);
            user1.getValuesFromDb(deviceId, new User.OnUserLoadedListener() {
                /**
                 * gets the values from the DB
                 *
                 * @param user gets the information of the user
                 */
                @Override
                public void onUserLoaded(User user) {
                    userName.setText(user1.getUserName());
                    name.setText(user1.getName());
                }
            });
        }



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


}
