package com.example.qrgo;

import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class IntentTestMain {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

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
