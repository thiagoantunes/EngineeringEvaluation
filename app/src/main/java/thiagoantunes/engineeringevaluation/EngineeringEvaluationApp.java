package thiagoantunes.engineeringevaluation;

import android.app.Application;

import thiagoantunes.engineeringevaluation.data.source.UserRepository;
import thiagoantunes.engineeringevaluation.data.source.local.AppDatabase;
import thiagoantunes.engineeringevaluation.util.AppExecutors;

/**
 * Android Application class. Used for accessing singletons.
 */
public class EngineeringEvaluationApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public UserRepository getRepository() {
        return UserRepository.getInstance(getDatabase(), mAppExecutors);
    }
}
