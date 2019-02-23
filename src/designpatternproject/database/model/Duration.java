package designpatternproject.database.model;

public class Duration {
    
    private int id;
    private String referenceKey;
    private int year;
    private int status;
    private String dorm;

    public Duration() {

    }

    public Duration(String referenceKey, int year, int status) {
        this.referenceKey = referenceKey;
        this.year = year;
        this.status = status;
    }

    public Duration(int id, String referenceKey, int year, int status, String dorm) {
        this.id = id;
        this.referenceKey = referenceKey;
        this.year = year;
        this.status = status;
        this.dorm = dorm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReferenceKey() {
        return referenceKey;
    }

    public void setReferenceKey(String referenceKey) {
        this.referenceKey = referenceKey;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDorm() {
        return dorm;
    }

    public void setDorm(String dorm) {
        this.dorm = dorm;
    }
}
