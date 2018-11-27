package thiagoantunes.engineeringevaluation.data.source;

import androidx.annotation.NonNull;

import java.util.List;

import androidx.lifecycle.LiveData;
import thiagoantunes.engineeringevaluation.data.User;

public interface UserDataSource {

    LiveData<List<User>> getUsers();

    LiveData<User> getUser(int userId);

    void saveUser(@NonNull User user);

    LiveData<List<User>> searchUsers(@NonNull String query);

}
