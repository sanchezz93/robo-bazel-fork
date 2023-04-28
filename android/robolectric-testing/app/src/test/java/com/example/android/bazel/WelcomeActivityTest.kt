package com.example.android.bazel

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class WelcomeActivityTest {

//    @get:Rule
//    val composeTestRule = createAndroidComposeRule<WelcomeActivity>()

    @Before
    fun setup() {
        System.setProperty("roborazzi.test.record", "true")
    }

    @Test
    fun clickingLogin_shouldStartLoginActivity() {
        Robolectric.buildActivity(WelcomeActivity::class.java).use { controller ->
            controller.setup() // Moves Activity to RESUMED state
            val activity: Activity = controller.get()
            activity.findViewById<View>(R.id.login).performClick()
            val expectedIntent = Intent(activity, LoginActivity::class.java)
            val actual: Intent = shadowOf(RuntimeEnvironment.application).getNextStartedActivity()
            assertEquals(expectedIntent.getComponent(), actual.getComponent())

            onView(ViewMatchers.isRoot())
                .captureRoboImage(
                    filePath = "this-will-be-deleted/root.png",
                    roborazziOptions = RoborazziOptions(recordOptions = RoborazziOptions.RecordOptions(0.5))
                )

            onView(withId(R.id.login))
                .captureRoboImage(
                    filePath = "this-will-be-deleted/login.png",
                    roborazziOptions = RoborazziOptions(recordOptions = RoborazziOptions.RecordOptions(0.5))
                )

            onView(withId(R.id.login))
                .captureRoboImage(
                    filePath = "this-will-be-deleted/manual_small_view_buttons.png",
                    roborazziOptions = RoborazziOptions(recordOptions = RoborazziOptions.RecordOptions(0.5))
                )
        }
    }
}
