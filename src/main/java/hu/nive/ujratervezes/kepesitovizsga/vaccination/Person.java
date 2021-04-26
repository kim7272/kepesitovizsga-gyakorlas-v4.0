package hu.nive.ujratervezes.kepesitovizsga.vaccination;


public class Person implements Comparable<Person> {

    private String name;
    private int age;
    private String email;

    @Override
    public String toString() {
        return name + " " + age + " " + email + " " + TAJ + " " + vaccinationType;
    }

    private String TAJ;
    private VaccinationType vaccinationType;

    public Person(String name, int age, String email, String TAJ, VaccinationType vaccinationType) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.TAJ = TAJ;
        this.vaccinationType = vaccinationType;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getTAJ() {
        return TAJ;
    }

    public VaccinationType getVaccinationType() {
        return vaccinationType;
    }

    @Override
    public int compareTo(Person o) {
      return   o.getAge() - this.getAge();
    }
}
