package thiagoantunes.engineeringevaluation.userlist;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import thiagoantunes.engineeringevaluation.EngineeringEvaluationApp;
import thiagoantunes.engineeringevaluation.data.User;
import thiagoantunes.engineeringevaluation.data.source.UserRepository;

public class UserListViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<User>> mObservableUsers;

    private final UserRepository mUserRepository;


    public UserListViewModel(Application application) {
        super(application);

        mUserRepository = ((EngineeringEvaluationApp) application).getRepository();

        mObservableUsers = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableUsers.setValue(null);

        LiveData<List<User>> users = mUserRepository.getUsers();

        // observe the changes of the products from the database and forward them
        mObservableUsers.addSource(users, mObservableUsers::setValue);
    }

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<List<User>> getUsers() {
        return mObservableUsers;
    }
}