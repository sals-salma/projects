package learn.solar.data;

import learn.solar.models.Material;
import learn.solar.models.Panel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

//TEST FOR:
//CREATE: DONE
//READ:
//UPDATE:
//DELETE:

// Arrange
// Act
// Assert


class PanelFileRepositoryTest {

    PanelFileRepository repository = new PanelFileRepository("data/panels-test.csv");

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get("data", "panels-seed.csv");
        Path testPath = Paths.get("data", "panels-test.csv");
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldAddNewPanel() throws DataAccessException {

        Panel toCreate = new Panel();
        toCreate.setPanelId(4);
        toCreate.setMaterialType(Material.MONOCRYSTALLINE_SILICON);
        toCreate.setSection("Test Section");
        toCreate.setRow(1);
        toCreate.setColumn(1);
        toCreate.setYearInstalled(2023);

        Panel actual = repository.add(toCreate);
        assertNotNull(actual);
        assertEquals(4, actual.getPanelId());
    }

    @Test
    void shouldNotAddNullPanel() {
        Panel toCreate = null;
        try {
            Panel returnedPanel = repository.add(toCreate);
            fail();
        } catch (DataAccessException ex) {
        }
    }

    @Test
    void shouldFindBySection(){
        Panel section = repository.findById(2);
        String actual = section.getSection();

        assertNotNull(section);
        assertEquals("Area B", actual);

    }

    @Test
    void shouldUpdatePanel() throws DataAccessException{
        Panel panel = new Panel();
        panel.setPanelId(1);
        panel.setMaterialType(Material.AMORPHOUS_SILICON);
        panel.setSection("Test Section");
        panel.setRow(1);
        panel.setColumn(1);
        panel.setYearInstalled(2011);

        boolean success = repository.update(panel);
        assertTrue(success);

        Panel actual = repository.findById(1);
        assertNotNull(actual);
        assertEquals(panel.getMaterialType(), actual.getMaterialType());
        assertEquals(panel.getSection(), actual.getSection());
        assertEquals(panel.getRow(), actual.getRow());
        assertEquals(panel.getColumn(), actual.getColumn());
        assertEquals(panel.getYearInstalled(), actual.getYearInstalled());
    }

    @Test
    void shouldNotUpdatePanel() throws DataAccessException{
        Panel doesNotExist = new Panel();
        doesNotExist.setPanelId(123456);

        boolean actual = repository.update(doesNotExist);
        assertFalse(actual);
    }

    @Test
    void shouldDeleteExistingPanel() throws DataAccessException {
        boolean actual = repository.delete(1);
        assertTrue(actual);

        Panel panel = repository.findById(1);
        assertNull(panel);
    }

    @Test
    void shouldNotDeletePanel () throws DataAccessException {
        boolean actual = repository.delete(123456);
        assertFalse(actual);
    }


}