package learn.foraging.domain;

import learn.foraging.data.DataException;
import learn.foraging.data.ForagerRepositoryDouble;
import learn.foraging.models.Forager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForagerServiceTest {

    ForagerService service;

    @BeforeEach
    public void setUp(){
        ForagerRepositoryDouble foragerDouble = new ForagerRepositoryDouble();
        service = new ForagerService(foragerDouble);
    }

    @Test
    void shouldAddValidForager() throws DataException {
        Forager toAdd = new Forager();
        toAdd.setId("0e4707f4-407e-4ec9-9665-baca0aabe88k");
        toAdd.setFirstName("Killua");
        toAdd.setLastName("Zoldyck");
        toAdd.setState("Kukuroo Mountain");

        Result<Forager> result = service.add(toAdd);
        assertTrue(result.isSuccess());

        Forager expected = new Forager();
        expected.setId("0e4707f4-407e-4ec9-9665-baca0aabe88k");
        expected.setFirstName("Killua");
        expected.setLastName("Zoldyck");
        expected.setState("Kukuroo Mountain");
    }

    @Test
    void shouldNotAddForagerWithNullFirstName() throws DataException {
        Forager toAdd = new Forager();
        toAdd.setId("0e4707f4-407e-4ec9-9665-baca0aabe88k");
        toAdd.setFirstName(null);
        toAdd.setLastName("Zoldyck");
        toAdd.setState("Kukuroo Mountain");

        Result<Forager> result = service.add(toAdd);
        assertFalse(result.isSuccess());

        Forager expected = new Forager();
        expected.setId("0e4707f4-407e-4ec9-9665-baca0aabe88k");
        expected.setFirstName("Killua");
        expected.setLastName("Zoldyck");
        expected.setState("Kukuroo Mountain");

    }
    @Test
    void shouldNotAddForagerWithNullLastName() throws DataException {
        Forager toAdd = new Forager();
        toAdd.setId("0e4707f4-407e-4ec9-9665-baca0aabe88k");
        toAdd.setFirstName("Killua");
        toAdd.setLastName(null);
        toAdd.setState("Kukuroo Mountain");

        Result<Forager> result = service.add(toAdd);
        assertFalse(result.isSuccess());

        Forager expected = new Forager();
        expected.setId("0e4707f4-407e-4ec9-9665-baca0aabe88k");
        expected.setFirstName("Killua");
        expected.setLastName("Zoldyck");
        expected.setState("Kukuroo Mountain");
    }
    @Test
    void shouldNotAddForagerWithNullState() throws DataException {
        Forager toAdd = new Forager();
        toAdd.setId("0e4707f4-407e-4ec9-9665-baca0aabe88k");
        toAdd.setFirstName("Killua");
        toAdd.setLastName("Zoldyck");
        toAdd.setState(null);

        Result<Forager> result = service.add(toAdd);
        assertFalse(result.isSuccess());

        Forager expected = new Forager();
        expected.setId("0e4707f4-407e-4ec9-9665-baca0aabe88k");
        expected.setFirstName("Killua");
        expected.setLastName("Zoldyck");
        expected.setState("Kukuroo Mountain");
    }
}