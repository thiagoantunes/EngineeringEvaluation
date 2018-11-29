package thiagoantunes.engineeringevaluation.data.source.remote.firebase;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import thiagoantunes.engineeringevaluation.data.User;

public class FirebaseDeserializers {

    public static class UserListDeserializer implements androidx.arch.core.util.Function<DataSnapshot, List<User>> {
        @Override
        public List<User> apply(DataSnapshot dataSnapshot) {
            List<User> list = new ArrayList<>();
            for (DataSnapshot child: dataSnapshot.getChildren()) {
                list.add(child.getValue(User.class));
            }
            return list;
        }
    }

    public static class UserDeserializer implements androidx.arch.core.util.Function<DataSnapshot, User> {
        @Override
        public User apply(DataSnapshot dataSnapshot) {
            return dataSnapshot.getValue(User.class);
        }
    }

}
