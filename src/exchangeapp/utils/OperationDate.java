package exchangeapp.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OperationDate {
    public static String getDate (){
        LocalDateTime dateObj = LocalDateTime.now();
        DateTimeFormatter dateFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        return dateObj.format(dateFormatObj);
    }
}
