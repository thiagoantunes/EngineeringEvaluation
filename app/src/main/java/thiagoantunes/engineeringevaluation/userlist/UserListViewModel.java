package thiagoantunes.engineeringevaluation.userlist;

import android.app.Application;

import com.google.common.base.Strings;

import java.util.List;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import thiagoantunes.engineeringevaluation.EngineeringEvaluationApp;
import thiagoantunes.engineeringevaluation.data.User;
import thiagoantunes.engineeringevaluation.data.source.UserRepository;

public class UserListViewModel extends AndroidViewModel {

    private LiveData<List<User>> mObservableUsers;

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
    LiveData<List<User>> getUsers() {
        return mObservableUsers;
    }

    void searchUsers(String query){
        if(Strings.isNullOrEmpty(query)){
            mObservableUsers = mUserRepository.getUsers();
        }
        mObservableUsers = mUserRepository.searchUsers(query);
    }

}