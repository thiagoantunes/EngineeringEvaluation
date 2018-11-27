package thiagoantunes.engineeringevaluation.data.source.local;


import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import androidx.room.TypeConverters;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import android.content.Context;

import thiagoantunes.engineeringevaluation.data.User;
import thiagoantunes.engineeringevaluation.data.UserFts;
import thiagoantunes.engineeringevaluation.data.converter.DateConverter;
import thiagoantunes.engineeringevaluation.util.AppExecutors;


/**
 * The Room Database that contains the Task table.
 */
@Database(entities = {User.class, UserFts.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    @VisibleForTesting
    private static final String DATABASE_NAME = "app.db";

    public abstract UserDao userDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context) {
        if (null == sInstance) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext());
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME).build();
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }


}
