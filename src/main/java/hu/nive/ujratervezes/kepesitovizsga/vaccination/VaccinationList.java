package hu.nive.ujratervezes.kepesitovizsga.vaccination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class VaccinationList {

    private MetaData metaData;
    private Map<LocalTime, Person> vaccinations = new HashMap<>();
    private VaccinationType vaccinationType;
    private LocalTime time;
    private List<Person> persons = new ArrayList<>();
    private List<LocalTime> times = new ArrayList<>();
    private Town town;

    public void readFromFile(BufferedReader br) throws IOException {
        creationOfMetaData(br);
        creationOfTimePersonMap(br);
    }

    private MetaData creationOfMetaData(BufferedReader br) throws IOException {
        String line;
        String[] parts_1 = br.readLine().split(" ");
        String[] parts_2 = br.readLine().split(" ");
        br.readLine();
        br.readLine();
        String postalCode = parts_1[2].substring(0, parts_1[2].length() - 1);
        String townName = parts_1[3];
        LocalDate date = LocalDate.parse(parts_2[1]);
        metaData = new MetaData(postalCode, townName, date);
        return metaData;
    }

    private Map<LocalTime, Person> creationOfTimePersonMap(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(";");
            String name = parts[1];
            int age = Integer.parseInt(parts[3]);
            String email = parts[4];
            String taj = parts[5];
            if (parts.length == 6) {
                vaccinationType = VaccinationType.NONE;
            }
            else if (parts.length == 7) {
                vaccinationType = VaccinationType.valueOf(parts[6]);
            }
            Person person = new Person(name, age, email, taj, vaccinationType);
            time = LocalTime.parse(parts[0]);
            vaccinations.put(time, person);
        }
        return vaccinations;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public Map<LocalTime, Person> getVaccinations() {
        return vaccinations;
    }

    public List<Person> getPersonsMoreThanHundredYearsOld() {
        List<Person> oldPersons = new ArrayList<>();
        for (Map.Entry<LocalTime, Person> entry: vaccinations.entrySet()) {
            if (entry.getValue().getAge() > 100){
                oldPersons.add(entry.getValue());
            }
        }
        Collections.sort(oldPersons);
        return oldPersons;
    }

    public List<Person> getAfternoonPersons() {
        List<Person> afternoonPersons = new ArrayList<>();
        for (Map.Entry<LocalTime, Person> entry: vaccinations.entrySet()) {
            if (entry.getKey().isAfter(LocalTime.of(12, 00))){
                afternoonPersons.add(entry.getValue());
            }
        }
        Collections.sort(afternoonPersons);
        return afternoonPersons;
    }

    public void validateTaj(){
        String invalidTaj = "";
        for (Map.Entry<LocalTime, Person> entry: vaccinations.entrySet()) {
            String taj = entry.getValue().getTAJ();
            int controlAlgorithm_1 = (((taj.charAt(0)) + taj.charAt(2) + taj.charAt(4) + taj.charAt(6)) * 3);
            int controlAlgorithm_2 = ((taj.charAt(1) + taj.charAt(3) + taj.charAt(5) + taj.charAt(7)) * 7);
            int controlSum = controlAlgorithm_1 + controlAlgorithm_2;
            if (controlSum % 10 != Integer.parseInt(String.valueOf(taj.charAt(8)))){
                invalidTaj = invalidTaj + ", " + taj;
            }
        }
            if (!invalidTaj.isEmpty()){
                invalidTaj = invalidTaj.substring(2);
                throw new IllegalArgumentException(invalidTaj);
            }
    }

    public String inviteExactPerson(LocalTime time){
        String name =  "";
        for (Map.Entry<LocalTime, Person> entry: vaccinations.entrySet()) {
            if (entry.getKey().equals(time)){
                name = entry.getValue().getName();
            }
        }
        if (name == ""){
            throw new IllegalArgumentException("Invalid time!");
        }
        return "Kedves" + " " + name + "! Ön következik. Kérem, fáradjon be!";
    }

    public Town getTown(){
        return new Town(metaData.getTownName(), metaData.getPostalCode());
    }

    public LocalDate getDateOfVaccination(){
        return metaData.getDate();
    }

    public Map<VaccinationType, Integer> getVaccinationStatistics(){
        Map<VaccinationType, Integer> statistics = new HashMap<>();
        for (Map.Entry<LocalTime, Person> entry: vaccinations.entrySet()) {
            VaccinationType key = entry.getValue().getVaccinationType();
            if (statistics.containsKey(key)) {
                statistics.put(key, statistics.get(key) + 1);
            }
            else {
                statistics.put(key, 1);
            }
        }
        return statistics;
    }
}




