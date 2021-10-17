package no.kristiania.person;

import org.junit.jupiter.api.Test;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonDaoTest {

    @Test
    void shouldRetrieveSavedPersonFromDatabase() throws SQLException {
        PersonDao dao = new PersonDao(createDataSource());

        Person person = examplePerson();
        dao.save(person);

        assertThat(dao.retrieve(person.getId()))
                .usingRecursiveComparison()
                .isEqualTo(person)
        ;
    }


    private Person examplePerson() {
        Person person = new Person();
        person.setFirstName(pickOne("Kristoffer", "Harry", "Skjalg", "Dennis", "Gard", "Zidane", "Mathias", "Adam"));
        person.setLastName(pickOne("Habberstad", "Liam", "Skjalgersen", "Brenna", "Svendsen", "Bono"));
        return person;
    }

    public static String pickOne(String... alternatives){
        return alternatives[new Random().nextInt(alternatives.length)];
    }

    private DataSource createDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/person_db");
        dataSource.setUser("person_dbuser");
        dataSource.setPassword("358Qs#u6^e$KNYH9a7");
        return dataSource;
    }
}
