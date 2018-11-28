package thiagoantunes.engineeringevaluation.dtos;


import android.app.Application;
import android.telephony.PhoneNumberUtils;

import java.text.DateFormat;
import java.util.Locale;

import thiagoantunes.engineeringevaluation.data.User;

public class UserDto {

    private int mId;

    private String mName;

    private String mPhone;

    private String mNeighborhood;

    private String mCity;

    private String mDate;

    public UserDto(int mId, String mName,  String mPhone, String mNeighborhood, String mCity, String mDate) {
        this.mId = mId;
        this.mName = mName;
        this.mPhone = mPhone;
        this.mNeighborhood = mNeighborhood;
        this.mCity = mCity;
        this.mDate = mDate;
    }

    public UserDto(User user) {
        this.mId = user.getId();
        this.mName = user.getName();
        this.mPhone = PhoneNumberUtils.formatNumber(user.getPhone(), Locale.getDefault().getCountry());
        this.mNeighborhood = user.getNeighborhood();
        this.mCity = user.getCity();
        this.mDate = DateFormat.getDateInstance().format(user.getDateOfBirth());
    }

    public int getId () { return mId; };

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

    public String getDate() { return mDate; }
}

