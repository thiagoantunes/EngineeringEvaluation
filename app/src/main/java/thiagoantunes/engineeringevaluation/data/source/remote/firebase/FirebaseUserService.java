package thiagoantunes.engineeringevaluation.data.source.remote.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import thiagoantunes.engineeringevaluation.data.User;

public class FirebaseUserService {

    private static volatile FirebaseUserService INSTANCE;

    private static final DatabaseReference USERS_REF =
            FirebaseDatabase.getInstance().getReference("/users");

    // Prevent direct instantiation.
    private FirebaseUserService() {

    }

    public static FirebaseUserService getInstance() {
        if (INSTANCE == null) {
            synchronized (FirebaseUserService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FirebaseUserService();
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<List<User>> getUsers() {
        return Transformations.map(new FirebaseQueryLiveData(USERS_REF),
                new FirebaseDeserializers.UserListDeserializer());
    }

    public LiveData<User> getUser(String userId) {
        return Transformations.map(new FirebaseQueryLiveData(USERS_REF.child(userId))
                , new FirebaseDeserializers.UserDeserializer());

    }

    public void saveUser(@NonNull User user) {
        USERS_REF.child(user.getKey()).setValue(user);
    }

}
