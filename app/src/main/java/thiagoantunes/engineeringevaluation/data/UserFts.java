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

    public UserFts(String mName,  String mPhone) {
        this.mName = mName;
        this.mPhone = mPhone;
    }

    public String getName() {
        return mName;
    }

    public String getPhone() {
        return mPhone;
    }
}