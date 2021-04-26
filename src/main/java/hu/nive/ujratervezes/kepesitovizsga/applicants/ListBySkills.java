package hu.nive.ujratervezes.kepesitovizsga.applicants;

import org.mariadb.jdbc.MariaDbDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListBySkills implements ApplicantListGenerator {

    @Override
    public List<Applicant> getListFromDatabase(MariaDbDataSource dataSource) {
            try (
                    Connection conn = dataSource.getConnection();
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("select first_name, last_name, skill from applicants where length(skill) = 3")
            ) {
                List<Applicant> applicants = new ArrayList<>();
                while (rs.next()) {
                    Applicant applicant = new Applicant(rs.getString("first_name"), rs.getString("last_name"), rs.getString("skill"));
                    applicants.add(applicant);
                }
                return applicants;
            }
            catch (SQLException se) {
                throw new IllegalStateException("Cannot select applicants", se);
            }
        }
    }
