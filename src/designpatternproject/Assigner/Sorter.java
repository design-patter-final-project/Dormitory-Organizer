import java.util.ArrayList;
import java.util.Date;
//this class basically contains the main student assigner/ordering algorithm to be called anywhere
//the view needs the dorm number of the specific students
    /*
    * And considers all the dorms are available with 8 spaces meaning for 8 students.
    * number of available dorms to be assigned is determined by the proctor but should
    * always be greater than (number of students / 8)
    *
    * */

public class Sorter extends Student{

    static Integer NUMBER_OF_AVAILABLE_DORMITORY = 120;
    static Integer NUMBER_OF_STUDENTS_IN_A_DORM = 8;

    private int DormNumber;

    //constructor with a new property to the student object
    public Sorter(String name, String field, Date year, int DormNumber){
        super(name,field,year);
        this.DormNumber = DormNumber;
    }


    ArrayList<Student> students = new ArrayList<Student>();
    public Student[][] dormAssiner(){
        int dormNumber = 0;
        int counter = 0;

        Student ar[][] = new Student[NUMBER_OF_AVAILABLE_DORMITORY][NUMBER_OF_STUDENTS_IN_A_DORM];
        int f = 0;
        for (int i = 0; i < 120; i++){
            for (int j = 0; j < NUMBER_OF_STUDENTS_IN_A_DORM; j ++){
                if ((students.get(i).getField()).equals("Chemical")){
                    ar[i][j] = students.get(i);
                    student.get(i).DormNumber = i;
                }else if((students.get(i).getField()).equals("Software")) {
                    ar[i][j] = students.get(i);
                    student.get(i).DormNumber = i;
                }else if((students.get(i).getField()).equals("Civil")){
                    ar[i][j] = students.get(i);
                    student.get(i).DormNumber = i;
                }else if((students.get(i).getField()).equals("Biomedical")){
                    ar[i][j] = students.get(i);
                    student.get(i).DormNumber = i;
                }else if((students.get(i).getField()).equals("Mechanical")){
                    ar[i][j] = students.get(i);
                    student.get(i).DormNumber = i;
                }else if((students.get(i).getField()).equals("IT")){
                    ar[i][j] = students.get(i);
                    student.get(i).DormNumber = i;
                }else {
                    ar[i][j] = null;
                }
            }
        }
        return ar;
    }

    public int getDormNumber() {
        return DormNumber;
    }

}
