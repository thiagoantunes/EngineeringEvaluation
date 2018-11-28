package thiagoantunes.engineeringevaluation.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.annotation.NonNull;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import java.util.Date;


import thiagoantunes.engineeringevaluation.data.converter.DateConverter;

@Entity(tableName = "users")
public final class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private final int mId;

    @NonNull
    @ColumnInfo(name = "city")
    private final String mCity;

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
                @NonNull String mCity, @NonNull Date mDateOfBirth) {
        this.mId = mId;
        this.mName = mName;
        this.mPhone = mPhone;
        this.mNeighborhood = mNeighborhood;
        this.mCity = mCity;
        this.mDateOfBirth = mDateOfBirth;
    }

    @Ignore
    public User(User user) {
        this.mId = user.getId();
        this.mName = user.getName();
        this.mPhone = user.getPhone();
        this.mNeighborhood = user.getNeighborhood();
        this.mCity = user.getCity();
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

    @NonNull
    public String getCity() {
        return mCity;
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
                Objects.equal(mCity, user.mCity) &&
                Objects.equal(mDateOfBirth, user.mDateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mName, mPhone);
    }

    @Override
    public String toString() {
        return "Name: " + mName;
    }

}
