package ArielBotos_EytanCabalero.part2;

import java.util.Arrays;

public class Department{
    private String name;
    private Lecturer[] lecturers = new Lecturer[2];
    private int StudentsCount;
    private int LecturerCount = 0;

    public Department(int StudentsCount, String name){
        this.StudentsCount = StudentsCount;
        this.name = name;
    }

    public String getname(){
        return name;
    }
    public Lecturer[] getLecturer(){
        return Arrays.copyOf(lecturers , LecturerCount);
    }

    public void AddLecturer(Lecturer lecturer){
        for ( int i = 0; i < LecturerCount; i++){
            if (lecturers[i] ==lecturer) return;
        }
        if (LecturerCount ==lecturers.length){
            lecturers = Arrays.copyOf(lecturers,lecturers.length * 2);
        }
        lecturers[LecturerCount++] = lecturer;
    }
    @Override
    public String toString() {
        String info = "Department: " + name + ", Students: " + StudentsCount;
        if (LecturerCount > 0) {
            info += ", Lecturers: ";
            for (int i = 0; i < LecturerCount; i++) {
                info += lecturers[i].getName() + " ";
            }
        }
        return info;
    }

}
