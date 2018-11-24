package thiagoantunes.engineeringevaluation.Data.source.local;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import thiagoantunes.engineeringevaluation.Data.User;
import thiagoantunes.engineeringevaluation.Util.LiveDataTestUtil;


import static org.hamcrest.CoreMatchers.notNullValue;
import static thiagoantunes.engineeringevaluation.Data.source.local.TestData.CITIES;
import static thiagoantunes.engineeringevaluation.Data.source.local.TestData.USERS;
import static thiagoantunes.engineeringevaluation.Data.source.local.TestData.USER_ENTITY;

import static junit.framework.Assert.assertTrue;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test the implementation of {@link UserDao}
 */
@RunWith(AndroidJUnit4.class)
public class UserDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase mDatabase;

    private UserDao mUserDao;

    @Before
    public void initDb() {
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class).build();

        mUserDao = mDatabase.userDao();
        mDatabase.cityDao().insertAll(CITIES);
    }

    @After
    public void closeDb() {
        mDatabase.close();
    }

    @Test
    public void getUsersWhenNoUserInserted() throws InterruptedException {
        List<User> users = LiveDataTestUtil.getValue(mUserDao.loadAllUsers());

        assertTrue(users.isEmpty());
    }

    @Test
    public void getUsersAfterInserted() throws InterruptedException {
        for (User item : USERS) {
            mUserDao.saveUser(item);
        }

        List<User> users = LiveDataTestUtil.getValue(mUserDao.loadAllUsers());

        assertThat(users.size(), is(USERS.size()));
    }

    @Test
    public void getUsersById() throws InterruptedException {
        for (User item : USERS) {
            mUserDao.saveUser(item);
        }

        User user = LiveDataTestUtil.getValue(mUserDao.getUserById(USER_ENTITY.getId()));

        assertUser(user, USER_ENTITY);
    }

    @Test
    public void insertUserReplacesOnConflict() throws InterruptedException {
        //Given that an user is inserted
        mUserDao.saveUser(USER_ENTITY);

        // When an user with the same id is inserted
        User newUser= new User(USER_ENTITY.getId(),"Teste", "313141412", "Bairro Teste", 1, new Date());
        mUserDao.saveUser(newUser);
        // When getting the user by id from the database
        User loaded = LiveDataTestUtil.getValue(mUserDao.getUserById(USER_ENTITY.getId()));

        // The loaded data contains the expected values
        assertUser(loaded, newUser);
    }

    private void assertUser(User user, User user2) {
        assertThat(user, notNullValue());
        assertThat(user.getId(), is(user2.getId()));
        assertThat(user.getName(), is(user2.getName()));
        assertThat(user.getPhone(), is(user2.getPhone()));
        assertThat(user.getNeighborhood(), is(user2.getNeighborhood()));
        assertThat(user.getCityId(), is(user2.getCityId()));
        assertThat(user.getDateOfBirth(), is(user2.getDateOfBirth()));
    }
}