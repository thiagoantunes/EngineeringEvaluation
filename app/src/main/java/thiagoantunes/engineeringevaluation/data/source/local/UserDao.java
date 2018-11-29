package thiagoantunes.engineeringevaluation.data.source.local;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import thiagoantunes.engineeringevaluation.data.User;

@Dao
public interface UserDao {


    @Query("SELECT * FROM users")
    LiveData<List<User>> loadAllUsers();

    @Query("SELECT * FROM users WHERE id = :userId")
    LiveData<User> getUserById(int userId);

    @Query("SELECT * FROM users WHERE userkey = :userKey")
    LiveData<User> getUserByKey(String userKey);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long saveUser(User user);

    @Query("SELECT users.* FROM users JOIN usersFts ON (users.id = usersFts.rowid) "
            + "WHERE usersFts MATCH :query")
    LiveData<List<User>> searchAllUsers(String query);

}
