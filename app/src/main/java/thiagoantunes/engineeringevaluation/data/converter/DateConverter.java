package thiagoantunes.engineeringevaluation.data.converter;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    /** Returns null if the string is a invalid date */
    public static Date convertAndValidateDateOfbirth(String stringDate){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy",
                java.util.Locale.getDefault());
        try {
            Date date = format.parse(stringDate);
            Date today = new Date();
            if(date.getTime() > today.getTime()){
                return null;
            }
            return date;
        } catch (ParseException e) {
            return null;
        }
    }
}