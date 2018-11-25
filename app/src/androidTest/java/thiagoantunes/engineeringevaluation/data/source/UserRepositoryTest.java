package thiagoantunes.engineeringevaluation.data.source;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;
import thiagoantunes.engineeringevaluation.data.User;
import thiagoantunes.engineeringevaluation.data.source.local.AppDatabase;
import thiagoantunes.engineeringevaluation.util.LiveDataTestUtil;
import thiagoantunes.engineeringevaluation.util.SingleExecutors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static thiagoantunes.engineeringevaluation.data.source.TestData.CITIES;
import static thiagoantunes.engineeringevaluation.data.source.TestData.USER_ENTITY;
import static thiagoantunes.engineeringevaluation.data.source.TestData.USER_ENTITY2;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private UserRepository mLocalDataSource;

    private AppDatabase mDatabase;

    @Before
    public void setup() {
        // using an in-memory database for testing, since it doesn't survive killing the process
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class)
                .build();
        mDatabase.cityDao().insertAll(CITIES);

        // Make sure that we're not keeping a reference to the wrong instance.
        UserRepository.clearInstance();
        mLocalDataSource = UserRepository.getInstance(mDatabase, new SingleExecutors());

    }

    @After
    public void cleanUp() {
        mDatabase.close();
        UserRepository.clearInstance();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(mLocalDataSource);
    }

    @Test
    public void saveUser_retrievesUser() throws InterruptedException {
        // When saved into the persistent repository
        mLocalDataSource.saveUser(USER_ENTITY);

        // Then the user can be retrieved from the persistent repository
        User user = LiveDataTestUtil.getValue(mLocalDataSource.getUser(USER_ENTITY.getId()));
        assertThat(user, is(USER_ENTITY));
    }

    @Test
    public void getUsers_retrieveSavedUsers() throws InterruptedException {
        mLocalDataSource.saveUser(USER_ENTITY);
        mLocalDataSource.saveUser(USER_ENTITY2);

        LiveData<List<User>> users1 = mLocalDataSource.getUsers();
        List<User> users = LiveDataTestUtil.getValue(mLocalDataSource.getUsers());

        assertNotNull(users);
        assertTrue(users.size() >= 2);

        boolean newUser1IdFound = false;
        boolean newUser2IdFound = false;
        for (User user : users) {
            if (USER_ENTITY.getId() == user.getId()) {
                newUser1IdFound = true;
            }
            if (USER_ENTITY2.getId() == user.getId()) {
                newUser2IdFound = true;
            }
        }
        assertTrue(newUser1IdFound);
        assertTrue(newUser2IdFound);
    }
}