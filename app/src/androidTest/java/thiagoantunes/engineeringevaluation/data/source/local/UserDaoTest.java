package thiagoantunes.engineeringevaluation.data.source.local;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import thiagoantunes.engineeringevaluation.data.User;
import thiagoantunes.engineeringevaluation.util.LiveDataTestUtil;


import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test the implementation of {@link UserDao}
 */
@RunWith(AndroidJUnit4.class)
public class UserDaoTest {

    private static final User USER_ENTITY = new User(0,UUID.randomUUID().toString(), "João Silva", "3134333333",
            "Centro", "Belo Horizonte", new Date());

    private static final User USER_ENTITY2 = new User(0,UUID.randomUUID().toString(), "Maria Silva", "31945443322",
            "Savassi", "Contagem", new Date() );

    private static final List<User> USERS;

    static {
        USERS = Arrays.asList(USER_ENTITY, USER_ENTITY2);
    }

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase mDatabase;

    private UserDao mUserDao;

    @Before
    public void initDb() {
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class).build();

        mUserDao = mDatabase.userDao();
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
        int userId1 = (int) mUserDao.saveUser(USER_ENTITY);
        mUserDao.saveUser(USER_ENTITY2);

        User user = LiveDataTestUtil.getValue(mUserDao.getUserById(userId1));

        assertUser(user, USER_ENTITY);
    }

    @Test
    public void insertUserReplacesOnConflict() throws InterruptedException {
        //Given that an user is inserted
        int userID = (int )mUserDao.saveUser(USER_ENTITY);

        // When an user with the same id is inserted
        User newUser= new User(userID,UUID.randomUUID().toString(),"Teste", "313141412", "Bairro Teste", "Belo Horizonte", new Date());
        mUserDao.saveUser(newUser);
        // When getting the user by id from the database
        User loaded = LiveDataTestUtil.getValue(mUserDao.getUserById(userID));

        // The loaded data contains the expected values
        assertUser(loaded, newUser);
    }

    @Test
    public void getUsers_retrieveSavedUsers() throws InterruptedException {
        int userId1 = (int) mUserDao.saveUser(USER_ENTITY);
        int userId2 = (int) mUserDao.saveUser(USER_ENTITY2);

        List<User> users = LiveDataTestUtil.getValue(mDatabase.userDao().loadAllUsers());

        assertNotNull(users);
        Assert.assertTrue(users.size() >= 2);

        boolean newUser1IdFound = false;
        boolean newUser2IdFound = false;
        for (User user : users) {
            if (userId1 == user.getId()) {
                newUser1IdFound = true;
            }
            if (userId2 == user.getId()) {
                newUser2IdFound = true;
            }
        }
        Assert.assertTrue(newUser1IdFound);
        Assert.assertTrue(newUser2IdFound);
    }

    private void assertUser(User user, User user2) {
        assertThat(user, notNullValue());
        assertThat(user.getName(), is(user2.getName()));
        assertThat(user.getPhone(), is(user2.getPhone()));
        assertThat(user.getNeighborhood(), is(user2.getNeighborhood()));
        assertThat(user.getCity(), is(user2.getCity()));
        assertThat(user.getDateOfBirth(), is(user2.getDateOfBirth()));
    }
}