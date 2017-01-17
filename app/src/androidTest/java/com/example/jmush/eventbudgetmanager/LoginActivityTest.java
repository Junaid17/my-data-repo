package com.example.jmush.eventbudgetmanager;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by jmush on 1/4/17.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule=new
            ActivityTestRule<>(LoginActivity.class);

    @Test
    public void onLoginFieldCheck(){
        onView(withId(R.id.email))
                .perform(typeText("j@gmail"), closeSoftKeyboard());

        onView(withId(R.id.password))
                .perform(typeText("j"),closeSoftKeyboard());

        onView(withId(R.id.btnLogin)).perform(click());
    }

}