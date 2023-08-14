public class Course {
    public String code;
    public int capacity;
    public SLinkedList<Student>[] studentTable;
    public int size;
    public SLinkedList<Student> waitlist;


    public Course(String code) {
        this.code = code;
        this.studentTable = new SLinkedList[10];
        this.size = 0;
        this.waitlist = new SLinkedList<Student>();
        this.capacity = 10;
    }

    public Course(String code, int capacity) {
        this.code = code;
        this.studentTable = new SLinkedList[capacity];
        this.size = 0;
        this.waitlist = new SLinkedList<>();
        this.capacity = capacity;
    }

    public void changeArrayLength(int m) {

        // make an array of linked lists
        SLinkedList<Student>[] output = new SLinkedList[m];

        // Create linked lists for each slot in the array
        for (int i = 0; i < m; i++){
            output[i] = new SLinkedList<Student>();
        }

        // move all students in linked list
        for (int i =0; i< studentTable.length; i++){
            if (studentTable[i] != null) {
                for (int j = 0; j < studentTable[i].size(); j++) {  // chech that i is not null
                    output[studentTable[i].get(j).id % m].addFirst(studentTable[i].get(j));
                }
            }
            else {
              studentTable[i] = null;
            }
        }

        //Reference this new array
        this.studentTable = output;

        // Change capacity
        this.capacity = m;
    }

    public boolean put(Student s) {
        //return false if student already registered in course
        if (s.isRegisteredOrWaitlisted(code)){
            return false;
        }

        // return false if student already has 3 courses
        if (s.courseCodes.size() == 3){
            return false;
        }

        // if course not at max capacity then we can add the student
        if (getCourseSize() < capacity){
            if (studentTable[s.id%capacity] == null){
                studentTable[s.id%capacity] = new SLinkedList<Student>();
            }
            studentTable[s.id%capacity].addFirst(s);
            s.addCourse(code);
            size +=1;
            return true;
        }

        // add the student to wait-list

        if (getCourseSize() == capacity){
            if (waitlist.size() == capacity/2){
                changeArrayLength((capacity + capacity/2));

                //move the students from waitlist and remove them
                while(waitlist.size() > 0){
                    if (studentTable[waitlist.getFirst().id%capacity] == null){
                        studentTable[waitlist.getFirst().id%capacity] = new SLinkedList<Student>();
                    }
                    studentTable[waitlist.getFirst().id%capacity].addFirst(waitlist.getFirst());
                    waitlist.removeFirst();
                    size +=1;
                }

                waitlist.addLast(s);
            }
            else{
                waitlist.addLast(s);
            }

            s.addCourse(code);
            return true;
        }

        return false;
    }

    public Student get(int id) {

        if (studentTable[id%capacity] == null){
            return null;
        }

        for (int i=0;i<studentTable[id%capacity].size();i++){
            if (studentTable[id%capacity].get(i).id == id){
                return studentTable[id%capacity].get(i);
            }
        }

        for (int i=0;i<waitlist.size();i++){
            if (waitlist.get(i).id == id){
                return waitlist.get(i);
            }
        }

        return null;

    }

    public Student remove(int id) {
        // check studnet table slot if in remove student add ppl from wait list
        // if not check wait list (make sure to check it's not empty)

        if (studentTable[id%capacity] != null){
            for (int i=0;i<studentTable[id%capacity].size();i++){

                if (studentTable[id%capacity].get(i).id == id){
                    Student student = studentTable[id%capacity].get(i);
                    studentTable[id%capacity].remove(student);

                    if (waitlist.size() != 0) {
                        studentTable[waitlist.getFirst().id % capacity].addFirst(waitlist.getFirst());
                        waitlist.removeFirst();
                    }

                    else{
                        size -= 1;
                    }
                    student.dropCourse(code);
                    return student;
                }
            }
        }


        for (int i = 0; i < waitlist.size(); i++) {
            if (waitlist.get(i).id == id) {
                Student student = waitlist.get(i);
                waitlist.remove(student);
                student.dropCourse(code);
                return student;
            } else {
                return null;
            }

        }
        return null;
    }

    public int getCourseSize() {
        return size;
    }


    public int[] getRegisteredIDs() {
        int[] registeredIDs = new int[capacity];

        int k = 0;

        for (int i =0; i<capacity; i++){
            for (int j=0; j < studentTable[i].size(); j++){
                registeredIDs[k] = studentTable[i].get(j).id;
                k++;
            }
        }

        return registeredIDs;
    }

    public Student[] getRegisteredStudents() {
        Student[] registeredStudents = new Student[capacity];

        int k = 0;

        for (int i =0; i<capacity; i++){
            for (int j=0; j < studentTable[i].size(); j++){
                registeredStudents[k] = studentTable[i].get(j);
                k++;
            }
        }

        return registeredStudents;
    }

    public int[] getWaitlistedIDs() {
        int[] IDs = new int[waitlist.size()];

        for (int i=0; i < waitlist.size();i++){
            IDs[i] = waitlist.get(i).id;
        }
        return IDs;
    }

    public Student[] getWaitlistedStudents() {

        Student[] waitListedStudents = new Student[waitlist.size()];

        for (int i=0; i < waitlist.size();i++){
            waitListedStudents[i] = waitlist.get(i);
        }
        return waitListedStudents;
    }

    public String toString() {
        String s = "Course: "+ this.code +"\n";
        s += "--------\n";
        for (int i = 0; i < this.studentTable.length; i++) {
            s += "|"+i+"     |\n";
            s += "|  ------> ";
            SLinkedList<Student> list = this.studentTable[i];
            if (list != null) {
                for (int j = 0; j < list.size(); j++) {
                    Student student = list.get(j);
                    s +=  student.id + ": "+ student.name +" --> ";
                }
            }
            s += "\n--------\n\n";
        }

        return s;
    }
}

