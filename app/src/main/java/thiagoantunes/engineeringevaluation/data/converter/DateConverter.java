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

    public static Date toDate(String stringDate){
        Date date;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy",
                java.util.Locale.getDefault());
        try {
            date = format.parse(stringDate);
            System.out.println(date);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}