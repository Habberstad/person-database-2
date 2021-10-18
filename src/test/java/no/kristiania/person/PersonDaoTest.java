package no.kristiania.person;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonDaoTest {

    private final PersonDao dao = new PersonDao(PersonDao.createDataSource());

    public PersonDaoTest() throws SQLException {
    }

    @Test
    void shouldRetrieveSavedPersonFromDatabase() throws SQLException {
        Person person = examplePerson();
        dao.save(person);

        assertThat(dao.retrieve(person.getId()))
                .usingRecursiveComparison()
                .isEqualTo(person)
        ;
    }

    @Test
    void shouldListPeopleByLastName() throws SQLException {
        Person matchingPerson = examplePerson();
        matchingPerson.setLastName("Testperson");
        dao.save(matchingPerson);

        Person anotherMatchingPerson = examplePerson();
        anotherMatchingPerson.setLastName(matchingPerson.getLastName());
        dao.save(anotherMatchingPerson);

        Person nonMatching = examplePerson();
        dao.save(nonMatching);

        assertThat(dao.listByLastName(matchingPerson.getLastName()))
                .extracting(Person::getId)
                .contains(matchingPerson.getId(), anotherMatchingPerson.getId())
                .doesNotContain(nonMatching.getId());
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

}
