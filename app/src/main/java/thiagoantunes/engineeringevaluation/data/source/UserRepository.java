package thiagoantunes.engineeringevaluation.data.source;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import thiagoantunes.engineeringevaluation.data.User;
import thiagoantunes.engineeringevaluation.data.source.local.AppDatabase;
import thiagoantunes.engineeringevaluation.util.AppExecutors;

import static com.google.common.base.Preconditions.checkNotNull;

public class UserRepository  implements UserDataSource {

    private static volatile UserRepository INSTANCE;

    private final AppDatabase mDatabase;

    private AppExecutors mAppExecutors;

    // Prevent direct instantiation.
    private UserRepository(AppDatabase database, @NonNull AppExecutors appExecutors) {
        mDatabase = database;
        mAppExecutors = appExecutors;
    }

    public static UserRepository getInstance(AppDatabase database, @NonNull AppExecutors appExecutors) {
        if (INSTANCE == null) {
            synchronized (UserRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserRepository(database,appExecutors);
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
    public LiveData<User> getUser(int userId) {
        return mDatabase.userDao().getUserById(userId);

    }

    @Override
    public void saveUser(@NonNull User user) {
        checkNotNull(user);
        Runnable saveRunnable = () -> mDatabase.userDao().saveUser(user);
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
