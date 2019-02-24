package designpatternproject.adapters;

import designpatternproject.database.model.Duration;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DurationAdapter {
       
    private DurationAdapter() { }
    
    public static ObservableList<Duration> getDurations(int diff, String referenceKey, int year) {
        ObservableList<Duration> durations = FXCollections.observableArrayList();
        int newYear = year;
        for(int i = 0; i < diff; i++) {
            Duration duration = new Duration(referenceKey, newYear, 0);
            durations.add(duration);
            newYear += 1;
        }
        return durations;
    }
    
}
