package thiagoantunes.engineeringevaluation.data.source;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import thiagoantunes.engineeringevaluation.data.User;
import thiagoantunes.engineeringevaluation.data.source.local.AppDatabase;
import thiagoantunes.engineeringevaluation.data.source.remote.firebase.FirebaseUserService;
import thiagoantunes.engineeringevaluation.util.AppExecutors;

import static com.google.common.base.Preconditions.checkNotNull;

public class UserRepository  implements UserDataSource {

    private static volatile UserRepository INSTANCE;

    private final AppDatabase mDatabase;

    private final FirebaseUserService mFirebase;

    private AppExecutors mAppExecutors;

    // Prevent direct instantiation.
    private UserRepository(FirebaseUserService firebase, AppDatabase database, @NonNull AppExecutors appExecutors) {
        mFirebase = firebase;
        mDatabase = database;
        mAppExecutors = appExecutors;
    }

    public static UserRepository getInstance(FirebaseUserService firebase, AppDatabase database, @NonNull AppExecutors appExecutors) {
        if (INSTANCE == null) {
            synchronized (UserRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserRepository(firebase, database,appExecutors);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<User>> getUsers() {
        //return mFirebase.getUsers();
        return mDatabase.userDao().loadAllUsers();
    }

    @Override
    public LiveData<User> getUser(String userId) {
        //return mFirebase.getUser(userId);
        return mDatabase.userDao().getUserByKey(userId);
    }

    @Override
    public void saveUser(@NonNull User user) {
        checkNotNull(user);
        Runnable saveRunnable = () -> mDatabase.userDao().saveUser(user);
        mAppExecutors.diskIO().execute(saveRunnable);
        //Runnable saveRunnable = () -> mFirebase.saveUser(user);
        //mAppExecutors.networkIO().execute(saveRunnable);

    }

    @Override
    public LiveData<List<User>> searchUsers(@NonNull String query) {
        //return mFirebase.searchUsers(query);
        return mDatabase.userDao().searchAllUsers("*" + query + "*");
    }

    @VisibleForTesting
    static void clearInstance() {
        INSTANCE = null;
    }
}
