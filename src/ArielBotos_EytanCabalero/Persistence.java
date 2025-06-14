package ArielBotos_EytanCabalero;

import java.io.*;

// Persistence.java
public class Persistence {
    public static College load(String collegeName) {
        String fname = collegeName.trim() + ".dat";
        File f = new File(fname);
        if (!f.exists()) return null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
            return (College) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void save(College college) {
        String fname = college.getCollegeName().trim() + ".dat";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fname))) {
            out.writeObject(college);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

