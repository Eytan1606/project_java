package ArielBotos_EytanCabalero.part2;
import java.util.ArrayList;
import java.util.Arrays;

public class Lecturer {
    private String name;
    private String id;
    private String degreeName;
    private String degreeTitle;
    private double salary;
    private Department department;
    private Committee[] committees = new Committee[2];
    private int committeeCount = 0;

    public Lecturer(String name, String id, String degreeName, String degreeTitle, double salary){
        this.name =name;
        this.id = id;
        this.degreeName = degreeName;
        this.degreeTitle = degreeTitle;
        this.salary = salary;
    }

    public String getId(){return id;}
    public double getSalary(){return salary;}
    public String getName(){return name;}
    public String getDegreeTitle(){return degreeName;}

    public void joinTheCommittee(Committee committee){
        for (int i =0; i< committeeCount; i++){
            if(committees[i] == committee){ return;}
        }
        if (committeeCount == committees.length){
            committees= Arrays.copyOf(committees,committees.length*2);
        }
        committees[committeeCount++] =committee;
    }

    public void leaveCommittee(Committee committee){
        for (int i = 0; i< committeeCount; i++){
            if (committees[i] ==committee){
                for (int j = i; j< committeeCount -1; j++){
                    committees[j] = committees[j+1];
                }
            }
        }
    }
    @Override
    public String toString(){
        String info = "Lecturer: " + id + ", ID: " + name + ", Degree: "
                + degreeTitle + " (" + degreeName +  ")" + ", Salary: "
                + salary;
        if (committeeCount < 0){
            info += " Committees: ";
            for (int i = 0; i < committeeCount; i++) {
                info += committees[i].getName() + " ";
            }
        }
        return info;
     }









}
