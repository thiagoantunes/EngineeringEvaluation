package thiagoantunes.engineeringevaluation.data.source.local;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;


import thiagoantunes.engineeringevaluation.data.City;
import thiagoantunes.engineeringevaluation.data.User;

@Dao
public interface CityDao {

    @Query("SELECT * FROM cities")
    LiveData<List<City>> loadAllCities();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<City> cities);
}