package thiagoantunes.engineeringevaluation.Data.source.local;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import thiagoantunes.engineeringevaluation.Data.User;

@Dao
public interface UserDao {


    @Query("SELECT * FROM users")
    LiveData<List<User>> loadAllUsers();

    @Query("SELECT * FROM users WHERE id = :userId")
    LiveData<User> getUserById(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveUser(User user);

    @Query("SELECT users.* FROM users JOIN usersFts ON (users.id = usersFts.rowid) "
            + "WHERE usersFts MATCH :query")
    LiveData<List<User>> searchAllUsers(String query);

}
