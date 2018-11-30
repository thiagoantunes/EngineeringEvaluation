package thiagoantunes.engineeringevaluation.data.source.remote.firebase;
import com.google.firebase.database.DataSnapshot;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MediatorLiveData;
import thiagoantunes.engineeringevaluation.data.User;

class FirebaseDeserializers {

    public static void UserListDeserializer(MediatorLiveData<List<User>> observable, DataSnapshot dataSnapshot, String ftsQuery) {
        if (dataSnapshot != null) {
            new Thread(() -> {
                List<User> list = new ArrayList<>();
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    User user = child.getValue(User.class);
                    if(ftsQuery == null ||
                            prepareString(user.getName()).contains(prepareString(ftsQuery)) ||
                            prepareString(user.getPhone()).contains(prepareString(ftsQuery)) ||
                            prepareString(user.getNeighborhood()).contains(prepareString(ftsQuery)) ||
                            prepareString(user.getCity()).contains(prepareString(ftsQuery))){
                        list.add(user);
                    }
                }
                observable.postValue(list);
            }).start();
        } else {
            observable.setValue(null);
        }
    }

    public static void UserDeserializer(MediatorLiveData<User> observable, DataSnapshot dataSnapshot) {
        if (dataSnapshot != null) {
            new Thread(() -> {
                observable.postValue(dataSnapshot.getValue(User.class));
            }).start();
        } else {
            observable.setValue(null);
        }
    }

    private static String prepareString(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
    }

}
