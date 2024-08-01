package learn.foraging.data;

import learn.foraging.models.Forager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ForagerFileRepositoryTest {

    ForagerFileRepository toTest = new ForagerFileRepository("./data/foragers-test.csv");

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get("data", "foragers-seed.csv");
        Path testPath = Paths.get("data", "foragers-test.csv");
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() {
        ForagerFileRepository repo = new ForagerFileRepository("./data/foragers.csv");
        List<Forager> all = repo.findAll();
        assertEquals(1014, all.size());
    }

    @Test
    void shouldAddNewForager () throws DataException{

        Forager toAdd = new Forager();
//        toAdd.setId(java.util.UUID.randomUUID().toString());
        toAdd.setFirstName("Salma");
        toAdd.setLastName("Salah");
        toAdd.setState("MN");

        Forager actual = toTest.add(toAdd);
        assertNotNull(actual);
//        assertEquals(java.util.UUID.randomUUID().toString(), actual.getId());
        assertEquals("Salma", actual.getFirstName());
        assertEquals("Salah", actual.getLastName());
        assertEquals("MN", actual.getState());
    }

    @Test
    void shouldNotAddNullFirstName() throws DataException {

        Forager toAdd = new Forager();
        toAdd.setFirstName(null);
        toAdd.setLastName("Salah");
        toAdd.setState("MN");

        Forager actual = toTest.add(toAdd);

        assertEquals(null , actual.getFirstName());
        assertEquals("Salah", actual.getLastName());
        assertEquals("MN", actual.getState());

    }
    @Test
    void shouldNotAddNullLastName() throws DataException {
        Forager toAdd = new Forager();
        toAdd.setFirstName("Salma");
        toAdd.setLastName(null);
        toAdd.setState("MN");

        Forager actual = toTest.add(toAdd);

        assertEquals("Salma" , actual.getFirstName());
        assertEquals(null, actual.getLastName());
        assertEquals("MN", actual.getState());
    }
    @Test
    void shouldNotAddNullState() throws DataException {
        Forager toAdd = new Forager();
        toAdd.setFirstName("Salma");
        toAdd.setLastName("Salah");
        toAdd.setState(null);

        Forager actual = toTest.add(toAdd);

        assertEquals("Salma" , actual.getFirstName());
        assertEquals("Salah", actual.getLastName());
        assertEquals(null, actual.getState());

    }
    @Test
    void shouldNotAddNullForager(){
        Forager toAdd = null;
        try {
            Forager returnedForager = toTest.add(toAdd);
            fail();
        } catch (DataException ex){
        }
    }
}