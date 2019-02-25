import java.util.Date;

public class Student {
    //the student model before adapter is used with the database
    public String name,field;
    public Date year;

    public Student(String name, String field, Date year) {
        this.name = name;
        this.field = field;
        this.year = year;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
