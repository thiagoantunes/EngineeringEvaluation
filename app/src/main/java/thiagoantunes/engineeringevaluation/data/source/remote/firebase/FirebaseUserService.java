package thiagoantunes.engineeringevaluation.data.source.remote.firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import thiagoantunes.engineeringevaluation.data.User;
import thiagoantunes.engineeringevaluation.data.source.UserDataSource;

public class FirebaseUserService implements UserDataSource {

    private static volatile FirebaseUserService INSTANCE;

    private static final DatabaseReference USERS_REF =
            FirebaseDatabase.getInstance().getReference("/users");

    private final MediatorLiveData<List<User>> mObservableUsers;

    private final MediatorLiveData<User> mObservableUser;

    // Prevent direct instantiation.
    private FirebaseUserService() {
        mObservableUsers = new MediatorLiveData<>();
        mObservableUser = new MediatorLiveData<>();
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

    @Override
    public LiveData<List<User>> getUsers() {
        // Set up the MediatorLiveData to convert DataSnapshot objects into HotStock objects
        mObservableUsers.addSource(
                new FirebaseQueryLiveData(USERS_REF),
                dataSnapshot -> FirebaseDeserializers.UserListDeserializer(mObservableUsers, dataSnapshot, null));
        return mObservableUsers;
    }

    @Override
    public LiveData<User> getUser(String userId) {
        mObservableUser.addSource(
                new FirebaseQueryLiveData(USERS_REF.child(userId)),
                dataSnapshot -> FirebaseDeserializers.UserDeserializer(mObservableUser, dataSnapshot));
        return mObservableUser;

    }

    @Override
    public void saveUser(@NonNull User user) {
        USERS_REF.child(user.getKey()).setValue(user);
    }

    @Override
    public LiveData<List<User>> searchUsers(@NonNull String ftsQuery) {
        mObservableUsers.addSource(
                new FirebaseQueryLiveData(USERS_REF),
                dataSnapshot -> FirebaseDeserializers.UserListDeserializer(mObservableUsers, dataSnapshot, ftsQuery));
        return mObservableUsers;
    }

}
