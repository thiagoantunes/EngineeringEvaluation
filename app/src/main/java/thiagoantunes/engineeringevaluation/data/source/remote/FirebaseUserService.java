package thiagoantunes.engineeringevaluation.data.source.remote;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import thiagoantunes.engineeringevaluation.data.User;

public class FirebaseUserService {

    private static final DatabaseReference USERS_REF =
            FirebaseDatabase.getInstance().getReference("/users");

    public LiveData<List<User>> getUsers() {
        return Transformations.map(new FirebaseQueryLiveData(USERS_REF),
                new FirebaseDeserializers.UserListDeserializer());
    }

    public LiveData<User> getUser(String userId) {
        return Transformations.map(new FirebaseQueryLiveData(USERS_REF.child(userId))
                , new FirebaseDeserializers.UserDeserializer());

    }

}
