

public class Challenge8 {

    public static void main(String[] args) {
        Tutor tutor = new Tutor();
        Student student = new Student(tutor);
        tutor.setStudent(student);

        Thread tutorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                tutor.studyTime();
            }
        });

        Thread studentThread = new Thread(new Runnable() {
            @Override
            public void run() {
                student.handInAssignment();
            }
        });

        tutorThread.start();
        studentThread.start();
    }
}

class Tutor {
    private Student student;

    public void setStudent(Student student) {
        this.student = student;
    }

    public void studyTime() {
        synchronized (this){
            System.out.println("Tutor has arrived");

            try {
                // wait for student to arrive
                this.wait();
            }
            catch (InterruptedException e) {
            }

            synchronized (student){
                student.startStudy();
                System.out.println("Tutor is studying with student");
            }
        }
    }

    public void getProgressReport() {
        System.out.println("Tutor gave progress report");
    }

}

class Student {

    private Tutor tutor;

    Student(Tutor tutor) {
        this.tutor = tutor;
    }

    public void startStudy() {
        System.out.println("Student is studying");
    }

    public void handInAssignment() {
        synchronized (tutor){
            tutor.getProgressReport();
            synchronized (this){
                System.out.println("Student handed in assignment");
                tutor.notifyAll();
            }
        }
    }
}