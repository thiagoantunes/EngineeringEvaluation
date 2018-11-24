package thiagoantunes.engineeringevaluation.Data;


import java.util.ArrayList;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "cities")
public class City {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private final int mId;

    @NonNull
    @ColumnInfo(name = "name")
    private final String mName;

    public City(@NonNull int mId, @NonNull String mName) {
        this.mId = mId;
        this.mName = mName;
    }

    @NonNull
    public int getId() {
        return mId;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    public static List<City> PopulateData() {
        return new ArrayList<City>() {{
            new City(1, "Belo Horizonte");
            new City(2, "Contagem");
            new City(3, "Betim");
        }};
    }
}
