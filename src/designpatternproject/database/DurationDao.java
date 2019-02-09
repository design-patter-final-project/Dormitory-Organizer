package designpatternproject.database;

import designpatternproject.database.model.Duration;
import java.util.List;

public interface DurationDao {
    
    List<Duration> getAll();
    List<Duration> getDurationByReference(String referenceKey);
    Duration getDurationById(int id);
    int addDuration(Duration duration);
}
