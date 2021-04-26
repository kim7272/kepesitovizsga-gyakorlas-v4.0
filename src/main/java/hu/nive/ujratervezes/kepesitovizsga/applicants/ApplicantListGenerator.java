package hu.nive.ujratervezes.kepesitovizsga.applicants;

import org.mariadb.jdbc.MariaDbDataSource;

import javax.sql.DataSource;
import java.util.List;

public interface ApplicantListGenerator {

    public abstract List<Applicant> getListFromDatabase(MariaDbDataSource dataSource);
}
