package application.date;

import java.time.LocalTime;
import java.util.Optional;

public class TimeParser {

    public static Optional<LocalTime> parseToLocalTime(String hourAndMinutes) {
            String[] hoursAndMinutesArray = hourAndMinutes.split("[-:;,\\s+]");
            if (hoursAndMinutesArray.length != 2) {
                throw new IllegalArgumentException();
            } else {
                String hour = hoursAndMinutesArray[0];
                String minutes = hoursAndMinutesArray[1];
                try {
                    int hourInt = Integer.parseInt(hour);
                    int minutesInt = Integer.parseInt(minutes);
                    LocalTime time = LocalTime.of(hourInt, minutesInt);
                    return Optional.of(time);
                } catch (NumberFormatException  e) {
                }
            }
        return Optional.empty();
    }
}
