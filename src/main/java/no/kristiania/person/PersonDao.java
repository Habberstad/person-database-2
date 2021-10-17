package no.kristiania.person;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersonDao {

    private Person person;
    private DataSource dataSource;

    public PersonDao(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO people (first_name, last_name) values(?,?)"
            )){
                statement.setString(1, person.getFirstName());
                statement.setString(2, person.getLastName());

                statement.executeUpdate();
            }
        }
        this.person = person;
    }

    public void save(Person person) {
        this.person = person;
    }

    public Object retrieve(Long id) {
        return person;
    }
}
