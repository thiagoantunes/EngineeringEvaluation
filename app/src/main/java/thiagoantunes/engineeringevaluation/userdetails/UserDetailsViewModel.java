package thiagoantunes.engineeringevaluation.userdetails;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import thiagoantunes.engineeringevaluation.EngineeringEvaluationApp;
import thiagoantunes.engineeringevaluation.data.User;
import thiagoantunes.engineeringevaluation.data.source.UserRepository;
import thiagoantunes.engineeringevaluation.dtos.UserDto;

public class UserDetailsViewModel extends AndroidViewModel {

    private LiveData<User> mObservableUser;

    private UserRepository mUserRepository;

    public ObservableField<User> user = new ObservableField<>();

    public ObservableField<UserDto> userDto = new ObservableField<>();


    public UserDetailsViewModel(@NonNull Application application) {
        super(application);
        mUserRepository =  ((EngineeringEvaluationApp) application).getRepository();
    }

    public LiveData<User> getObservableUser() {
        return mObservableUser;
    }

    public void setUser(User user) {
        this.userDto.set(new UserDto(user, getApplication()));
        this.user.set(user);
    }

    public void start(int userId) {
        if (userId != 0) {
            mObservableUser = mUserRepository.getUser(userId);
        }
    }
}
