package pl.edu.zut.trackfitnesstraining

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class MainInstrumentedTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("pl.edu.zut.trackfitnesstraining", appContext.packageName)
//    }

    @Test
    fun testAddWeightCorrectData() {
        onView(withId(R.id.button_addWeight)).perform(click())
        onView(withId(R.id.textWeight)).perform(typeText("88"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.button_addWeight)).check(matches((isDisplayed())))
    }

    @Test
    fun testAddWeightWrongData() {
        onView(withId(R.id.button_addWeight)).perform(click())
        onView(withId(R.id.textWeight)).perform(typeText("888"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textWeight)).perform(typeText("0"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.button_save)).check(matches((isDisplayed())))
    }

    @Test
    fun testAddMeasurementCorrectData() {
        onView(withId(R.id.button_addMeasurment)).perform(click())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textNeck)).perform(typeText("36"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textChest)).perform(typeText("122"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textBiceps)).perform(typeText("37"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textWeist)).perform(typeText("78"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textStomach)).perform(typeText("81"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textHip)).perform(typeText("80"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textTigh)).perform(typeText("54"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textCalv)).perform(typeText("37"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.button_addMeasurment)).check(matches((isDisplayed())))
    }

    @Test
    fun testAddMeasurementWrongData() {
        onView(withId(R.id.button_addMeasurment)).perform(click())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textNeck)).perform(typeText("36"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textChest)).perform(typeText("122"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textBiceps)).perform(typeText("37"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textWeist)).perform(typeText("78"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textStomach)).perform(typeText("81"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textHip)).perform(typeText("80"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textTigh)).perform(typeText("54"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.button_save)).check(matches((isDisplayed())))
    }

    @Test
    fun testUpdateBasicInfoCorrectData() {
        onView(withId(R.id.button_addInfo)).perform(click())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textName)).perform(typeText("Tester"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textAge)).perform(typeText("24"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textHeight)).perform(typeText("188"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textWeight)).perform(typeText("80"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.button_addInfo)).check(matches((isDisplayed())))
    }

    @Test
    fun testUpdateBasicInfoWrongData() {
        onView(withId(R.id.button_addInfo)).perform(click())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textName)).perform(typeText("Tester"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textAge)).perform(typeText("24"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.textHeight)).perform(typeText("188"), closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.button_save)).check(matches((isDisplayed())))
    }

    @Test
    fun testAddWorkoutCorrectData() {
        onView(withId(R.id.button_addWorkout)).perform(click())
        onView(withId(R.id.button_submit)).perform(scrollTo()).perform(click())
        onView(withId(R.id.button_addExercise)).perform(click())
        onView(withId(R.id.button_save)).perform(click())
        onView(withId(R.id.button_submit)).perform(scrollTo()).perform(click())
        onView(withId(R.id.button_addWorkout)).perform(scrollTo()).check(matches((isDisplayed())))
    }

    @Test
    fun testAddWorkoutWrongData() {
        onView(withId(R.id.button_addWorkout)).perform(click())
        onView(withId(R.id.button_submit)).perform(scrollTo()).perform(click())
        onView(withId(R.id.button_submit)).perform(scrollTo()).check(matches((isDisplayed())))
    }
}
