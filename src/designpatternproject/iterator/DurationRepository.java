package designpatternproject.iterator;

import designpatternproject.database.model.Duration;
import javafx.beans.property.ListProperty;
import javafx.collections.ObservableList;

public class DurationRepository implements Sector {
    
    private ObservableList<Duration> duration;
    
    public DurationRepository(ObservableList<Duration> duration) {
        this.duration = duration;
    }
    
    @Override
    public Iiterator getIterator() {
        return new DurationIterator();
    }
    
    private class DurationIterator implements Iiterator {
        int index;

        @Override
        public boolean hasNext() {
            if(index < duration.size()) {
                return true;
            } 
            return false;
        }

        @Override
        public Object next() {
            if(this.hasNext()) {
                return duration.get(index++);
            }
            return null;
        }
    }
}
