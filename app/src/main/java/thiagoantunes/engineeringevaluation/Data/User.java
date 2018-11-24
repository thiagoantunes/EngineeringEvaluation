package thiagoantunes.engineeringevaluation.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.annotation.NonNull;

import com.google.common.base.Objects;

import java.util.Date;


import thiagoantunes.engineeringevaluation.Data.converter.DateConverter;

//@Fts4
@Entity(tableName = "users",
        foreignKeys = {
                @ForeignKey(entity = City.class,
                        parentColumns = "id",
                        childColumns = "cityId",
                        onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "cityId")
        })
public final class User {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private final int mId;


    @ColumnInfo(name = "cityId")
    private final int mCityId;

    @NonNull
    @ColumnInfo(name = "name")
    private final String mName;

    @NonNull
    @ColumnInfo(name = "phone")
    private final String mPhone;

    @NonNull
    @ColumnInfo(name = "neighborhood")
    private final String mNeighborhood;

    @NonNull
    @ColumnInfo(name = "date_of_birth")
    @TypeConverters({DateConverter.class})
    private final Date mDateOfBirth;


    public User(int mId, @NonNull String mName,
                @NonNull String mPhone, @NonNull String mNeighborhood,
                int mCityId, @NonNull Date mDateOfBirth) {
        this.mId = mId;
        this.mName = mName;
        this.mPhone = mPhone;
        this.mNeighborhood = mNeighborhood;
        this.mCityId = mCityId;
        this.mDateOfBirth = mDateOfBirth;
    }

    @Ignore
    public User(User user) {
        this.mId = user.getId();
        this.mName = user.getName();
        this.mPhone = user.getPhone();
        this.mNeighborhood = user.getNeighborhood();
        this.mCityId = user.getCityId();
        this.mDateOfBirth = user.getDateOfBirth();
    }

    public int getId() {
        return mId;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @NonNull
    public String getPhone() {
        return mPhone;
    }

    @NonNull
    public String getNeighborhood() {
        return mNeighborhood;
    }

    public int getCityId() {
        return mCityId;
    }

    @NonNull
    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equal(mId, user.mId) &&
                Objects.equal(mName, user.mName) &&
                Objects.equal(mPhone, user.mPhone) &&
                Objects.equal(mNeighborhood, user.mNeighborhood) &&
                Objects.equal(mCityId, user.mCityId) &&
                Objects.equal(mDateOfBirth, user.mDateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mName, mPhone);
    }

    @Override
    public String toString() {
        return mName;
    }

}
