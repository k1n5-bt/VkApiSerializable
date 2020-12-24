import java.io.Serializable;

class Human implements Serializable {

    public String firstName;
    public String lastName;
    public String sex;
    public String city;
    public String education;

    Human(String fName, String lName, String sex, String city, String education){

        firstName=fName;
        lastName=lName;
        this.sex=sex;
        this.city=city;
        this.education=education;
    }
}
