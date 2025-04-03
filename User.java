public class User {
    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String dob;
    private String gender;
    private String course;
    private long registrationTime;

    public User(String studentId, String firstName, String lastName, String email, String phone,
                String dob, String gender, String course, long registrationTime) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.gender = gender;
        this.course = course;
        this.registrationTime = registrationTime;
    }

    public String getStudentId() { return studentId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getDob() { return dob; }
    public String getGender() { return gender; }
    public String getCourse() { return course; }
    public long getRegistrationTime() { return registrationTime; }

    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setDob(String dob) { this.dob = dob; }
    public void setGender(String gender) { this.gender = gender; }
    public void setCourse(String course) { this.course = course; }
    public void setRegistrationTime(long registrationTime) { this.registrationTime = registrationTime; }

    @Override
    public String toString() {
        return studentId + "," + firstName + "," + lastName + "," + email + "," + phone + "," +
                dob + "," + gender + "," + course + "," + registrationTime;
    }

    public static User fromString(String str) {
        String[] parts = str.split(",");
        return new User(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], Long.parseLong(parts[8]));
    }
}