package thiagoantunes.engineeringevaluation.userlist;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import androidx.annotation.Nullable;
import androidx.arch.core.executor.testing.CountingTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.test.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import thiagoantunes.engineeringevaluation.R;
import thiagoantunes.engineeringevaluation.data.source.local.AppDatabase;
import thiagoantunes.engineeringevaluation.util.AppExecutors;
import thiagoantunes.engineeringevaluation.util.EspressoTestUtil;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

public class UserListActivityTest {

    @Rule
    public ActivityTestRule<UserListActivity> mActivityRule = new ActivityTestRule<>(
            UserListActivity.class);

    @Rule
    public CountingTaskExecutorRule mCountingTaskExecutorRule = new CountingTaskExecutorRule();

    public UserListActivityTest() {
        // delete the database
        InstrumentationRegistry.getTargetContext().deleteDatabase(AppDatabase.DATABASE_NAME);
    }

    @Before
    public void disableRecyclerViewAnimations() {
        // Disable RecyclerView animations
        EspressoTestUtil.disableAnimations(mActivityRule);
    }

    @Before
    public void waitForDbCreation() throws Throwable {
        final CountDownLatch latch = new CountDownLatch(1);
        final LiveData<Boolean> databaseCreated = AppDatabase
                .getInstance(InstrumentationRegistry
                        .getTargetContext(), new AppExecutors()).getDatabaseCreated();
        mActivityRule.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                databaseCreated.observeForever(new Observer<Boolean>() {

                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (Boolean.TRUE.equals(aBoolean)) {
                            databaseCreated.removeObserver(this);
                            latch.countDown();
                        }
                    }
                });
            }
        });
        MatcherAssert.assertThat("database should've initialized",
                latch.await(1, TimeUnit.MINUTES), CoreMatchers.is(true));
    }

    @Test
    public void clickOnFirstItem_opensComments() throws Throwable {
        drain();
        // When clicking on the first product
        //onView(withContentDescription(R.string.list_title))
        //        .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        drain();
        // Then the second screen with the comments should appear.
        onView(withContentDescription(R.string.phone))
                .check(matches(isDisplayed()));
        drain();
        // Then the second screen with the comments should appear.
        onView(withContentDescription(R.string.city))
                .check(matches(not(withText(""))));

    }

    private void drain() throws TimeoutException, InterruptedException {
        mCountingTaskExecutorRule.drainTasks(1, TimeUnit.MINUTES);
    }
}