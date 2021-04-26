package hu.nive.ujratervezes.kepesitovizsga.applicants;

import org.mariadb.jdbc.MariaDbDataSource;

import java.util.*;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListByApplicantsPersonal implements ApplicantListGenerator {

    @Override
    public List<Applicant> getListFromDatabase(MariaDbDataSource dataSource) {
        try (
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select first_name, last_name, gender, phone_number, email from applicants")
        ) {
            List<Applicant> applicants = new ArrayList<>();
            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String gender = rs.getString("gender");
                String phoneNumber = rs.getString("phone_number");
                String email = rs.getString("email");
                applicants.add(new Applicant(firstName, lastName, phoneNumber, email));
            }
            return applicants;
        }
        catch (SQLException se) {
            throw new IllegalStateException("Cannot select applicants", se);
        }
    }
}
