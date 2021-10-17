package no.kristiania.person;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDao {

    private Person person;
    private DataSource dataSource;

    public PersonDao(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
    }

    public void save(Person person) throws SQLException {
        try (Connection connection = dataSource.getConnection()){

           try (PreparedStatement statement = connection.prepareStatement(
                   "insert into people (first_name, last_name) values(?,?)"
           )){
                statement.setString(1,person.getFirstName());
                statement.setString(2, person.getLastName());
                statement.executeUpdate();
           }
        }
        this.person = person;
    }

    public Person retrieve(Long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * people where id = ?"
            )) {
                statement.setLong(1,id);

                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();

                    Person person = new Person();
                    person.setFirstName(resultSet.getString("first_name"));
                    person.setLastName(resultSet.getString("last_name"));
                    return person;
                }
            }
        }
    }
}
