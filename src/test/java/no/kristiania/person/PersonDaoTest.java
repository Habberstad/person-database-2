package no.kristiania.person;

public class PersonDaoTest {

    @Test
    void shouldRetrieveSavedPersonFromDatabase() {
        PersonDao dao = new PersonDao(createDataSource());

        Person persons = examplePerson();
        dao.save(person);

        assertThat(dao.retrieve(person.getId()))
            // TODO
        ;
    }
}
