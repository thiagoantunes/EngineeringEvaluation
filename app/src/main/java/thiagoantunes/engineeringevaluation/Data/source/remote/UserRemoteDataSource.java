package thiagoantunes.engineeringevaluation.Data.source.remote;

import java.util.List;

import androidx.annotation.NonNull;

import androidx.lifecycle.LiveData;
import thiagoantunes.engineeringevaluation.Data.User;
import thiagoantunes.engineeringevaluation.Data.source.UserDataSource;

public class UserRemoteDataSource implements UserDataSource {


    @Override
    public LiveData<List<User>> getUsers() {
        return null;
    }

    @Override
    public LiveData<User> getUser(@NonNull int userId) {
        return null;
    }

    @Override
    public void saveUser(@NonNull User user) {

    }

    @Override
    public LiveData<List<User>> searchUsers(@NonNull String query) {
        return null;
    }
}
