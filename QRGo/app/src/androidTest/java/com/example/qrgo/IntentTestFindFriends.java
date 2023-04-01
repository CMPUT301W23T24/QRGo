package com.example.qrgo;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class IntentTestFindFriends {
    private Solo solo;

    @Rule
    public ActivityTestRule<FindFriends> rule =
            new ActivityTestRule<>(FindFriends.class, true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo (InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    @After
    public void finish() throws Exception{
        solo.finishOpenedActivities();
    }
}
