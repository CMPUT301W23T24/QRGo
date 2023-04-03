package com.example.qrgo;

import android.os.Bundle;
import android.os.Handler;
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
import com.google.android.gms.tasks.OnFailureListener;
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
        newUser.setUserName(userName.toLowerCase());
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPhoneNum(phoneNum);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference cr = db.collection("user");

        ArrayList<String> usernames = new ArrayList<String>();
        final Boolean[] result = {false};
        if (userName.length() > 0) {
            cr.whereEqualTo("username", userName)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot snapshot : task.getResult()) {
                                    if (snapshot.getId().equals(String.valueOf(newUser.getDeviceID()))) {
                                        Log.d(TAG, "yes");
                                    } else {
                                        Log.d(TAG, String.valueOf(snapshot.getString("username")));
                                        Log.d(TAG, String.valueOf(snapshot.getId()));
                                        Log.d(TAG, String.valueOf(newUser.getDeviceID()));
                                        usernames.add(snapshot.getString("username"));
                                    }
                                }

                                if (usernames.size() > 0) {
                                    Log.d(TAG, "1");
                                    result[0] = false;
                                } else {
                                    Log.d(TAG, String.valueOf(usernames.size()));
                                    Log.d(TAG, "2");
                                    result[0] = true;
                                }
                            }
                        }
                    });
        }
        else{
            newUser.updateDb();
            Toast.makeText(getApplicationContext(), "Username required!", Toast.LENGTH_SHORT).show();
            new EditProfileFragment(newUser).show(getSupportFragmentManager(), "Edit Profile");
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "works");
                if (result[0].equals(false)) {
                    newUser.updateDb();
                    updateTextView(newUser);
                    Toast.makeText(getApplicationContext(), "Your username wasn't unique/ incorrect, please try again!", Toast.LENGTH_SHORT).show();
                    new EditProfileFragment(newUser).show(getSupportFragmentManager(), "Edit Profile");
                } else {
                    newUser.updateDb();
                    updateTextView(newUser);
                }
            }
        }, 2000);
    }
    /**
     * Once pressed, prompts the user to either enter a correct username or ignore it depending on the uniqueness
     * @param newUser
     * @param userName
     * @param name
     * @param email
     * @param phoneNum
     */
    @Override
    public void onCancelPressed(User newUser, String userName, String name, String email, Integer phoneNum) {
        newUser.setUserName(userName.toLowerCase());
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPhoneNum(phoneNum);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference cr = db.collection("user");

        ArrayList<String> usernames = new ArrayList<String>();
        final Boolean[] result = {false};
        if (userName.length() > 0) {
            cr.whereEqualTo("username", userName)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot snapshot : task.getResult()) {
                                    if (snapshot.getId().equals(String.valueOf(newUser.getDeviceID()))) {
                                        Log.d(TAG, "yes");
                                    } else {
                                        Log.d(TAG, String.valueOf(snapshot.getString("username")));
                                        Log.d(TAG, String.valueOf(snapshot.getId()));
                                        Log.d(TAG, String.valueOf(newUser.getDeviceID()));
                                        usernames.add(snapshot.getString("username"));
                                    }
                                }

                                if (usernames.size() > 0) {
                                    Log.d(TAG, "1");
                                    result[0] = false;
                                } else {
                                    Log.d(TAG, String.valueOf(usernames.size()));
                                    Log.d(TAG, "2");
                                    result[0] = true;
                                }
                            }
                        }
                    });
        }
        else{
            newUser.updateDb();
            Toast.makeText(getApplicationContext(), "Username required!", Toast.LENGTH_SHORT).show();
            result[0]=true;
            new EditProfileFragment(newUser).show(getSupportFragmentManager(), "Edit Profile");
        }

        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (result[0].equals(false)) {
                    newUser.updateDb();
                    updateTextView(newUser);
                    Toast.makeText(getApplicationContext(), "Please provide a unique username!", Toast.LENGTH_SHORT).show();
                    new EditProfileFragment(newUser).show(getSupportFragmentManager(), "Edit Profile");
                } else {
                    newUser.updateDb();
                    updateTextView(newUser);
                }
            }
        },2000);


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
