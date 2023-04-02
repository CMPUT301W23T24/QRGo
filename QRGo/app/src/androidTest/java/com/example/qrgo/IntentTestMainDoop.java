package com.example.qrgo;

import android.app.Activity;
import android.app.Fragment;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class IntentTestMainDoop {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainDoop> rule =
            new ActivityTestRule<>(MainDoop.class, true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo (InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    @Test
    public void fragment() throws Exception{
        Fragment fragment = rule.getActivity().getFragmentManager().findFragmentByTag("Edit Profile");
    }

    @After
    public void finish() throws Exception{
        solo.finishOpenedActivities();
    }
}
