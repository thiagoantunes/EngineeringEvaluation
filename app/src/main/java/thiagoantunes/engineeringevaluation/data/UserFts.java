package thiagoantunes.engineeringevaluation.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;

@Entity(tableName = "usersFts")
@Fts4(contentEntity = User.class)
public class UserFts {

    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "phone")
    private String mPhone;

    @ColumnInfo(name = "neighborhood")
    private String mNeighborhood;

    @ColumnInfo(name = "city")
    private String mCity;

    public UserFts(String mName,  String mPhone, String mNeighborhood, String mCity) {
        this.mName = mName;
        this.mPhone = mPhone;
        this.mNeighborhood = mNeighborhood;
        this.mCity = mCity;

    }

    public String getName() {
        return mName;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getNeighborhood() { return mNeighborhood; }

    public String getCity() {
        return mCity;
    }
}