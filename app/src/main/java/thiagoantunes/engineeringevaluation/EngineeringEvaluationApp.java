package thiagoantunes.engineeringevaluation;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

import thiagoantunes.engineeringevaluation.data.source.UserRepository;
import thiagoantunes.engineeringevaluation.data.source.local.AppDatabase;
import thiagoantunes.engineeringevaluation.data.source.remote.firebase.FirebaseUserService;
import thiagoantunes.engineeringevaluation.util.AppExecutors;

/**
 * Android Application class. Used for accessing singletons.
 */
public class EngineeringEvaluationApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mAppExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public FirebaseUserService getFirebase() {
        return FirebaseUserService.getInstance();
    }

    public UserRepository getRepository() {
        return UserRepository.getInstance(getFirebase(), getDatabase(), mAppExecutors);
    }
}
