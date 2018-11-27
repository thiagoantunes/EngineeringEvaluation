package thiagoantunes.engineeringevaluation.userlist;

import android.app.Application;
import java.util.List;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import thiagoantunes.engineeringevaluation.EngineeringEvaluationApp;
import thiagoantunes.engineeringevaluation.data.User;
import thiagoantunes.engineeringevaluation.data.source.UserRepository;

public class UserListViewModel extends AndroidViewModel {

    private final LiveData<List<User>> mObservableUsers;

    private UserRepository mUserRepository;

    public UserListViewModel(Application application) {
        super(application);

        mUserRepository = ((EngineeringEvaluationApp) application)
                .getRepository();

        mObservableUsers = mUserRepository.getUsers();
    }
    /**
     * Expose the LiveData Users query so the UI can observe it.
     */
    public LiveData<List<User>> getUsers() {
        return mObservableUsers;
    }

    public LiveData<List<User>> searchUsers(String query){
        return mUserRepository.searchUsers(query);
    }

}