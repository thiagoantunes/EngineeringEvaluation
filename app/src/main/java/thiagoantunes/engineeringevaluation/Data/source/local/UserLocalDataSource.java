package thiagoantunes.engineeringevaluation.Data.source.local;


import java.util.List;

import androidx.annotation.NonNull;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import thiagoantunes.engineeringevaluation.Data.User;
import thiagoantunes.engineeringevaluation.Data.source.UserDataSource;
import thiagoantunes.engineeringevaluation.Util.AppExecutors;

import static com.google.common.base.Preconditions.checkNotNull;

public class UserLocalDataSource  implements UserDataSource {

    private static volatile UserLocalDataSource INSTANCE;

    private final AppDatabase mDatabase;

    private AppExecutors mAppExecutors;

    // Prevent direct instantiation.
    private UserLocalDataSource(final AppDatabase database, @NonNull AppExecutors appExecutors) {
        mAppExecutors = appExecutors;
        mDatabase = database;
    }

    public static UserLocalDataSource getInstance(final AppDatabase database, @NonNull AppExecutors appExecutors) {
        if (INSTANCE == null) {
            synchronized (UserLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserLocalDataSource(database, appExecutors);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<User>> getUsers() {
        return mDatabase.userDao().loadAllUsers();
    }

    @Override
    public LiveData<User> getUser(@NonNull int userId) {
        return mDatabase.userDao().getUserById(userId);

    }

    @Override
    public void saveUser(@NonNull User user) {
        checkNotNull(user);
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mDatabase.userDao().saveUser(user);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public LiveData<List<User>> searchUsers(@NonNull String query) {
        return mDatabase.userDao().searchAllUsers(query);
    }

    @VisibleForTesting
    static void clearInstance() {
        INSTANCE = null;
    }
}
