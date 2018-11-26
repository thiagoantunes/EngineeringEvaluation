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


    public UserListViewModel(Application application) {
        super(application);

        mObservableUsers = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableUsers.setValue(null);

        LiveData<List<User>> users = ((EngineeringEvaluationApp) application).getRepository()
                .getUsers();

        // observe the changes of the users from the database and forward them
        mObservableUsers.addSource(users, mObservableUsers::setValue);
    }
    /**
     * Expose the LiveData Users query so the UI can observe it.
     */
    public LiveData<List<User>> getUsers() {
        return mObservableUsers;
    }
}