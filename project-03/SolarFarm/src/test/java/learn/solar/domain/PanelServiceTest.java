package learn.solar.domain;

import learn.solar.data.DataAccessException;
import learn.solar.data.PanelRepoDouble;
import learn.solar.models.Material;
import learn.solar.models.Panel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PanelServiceTest {

    PanelService service;

    @BeforeEach
    public void setup() {
        PanelRepoDouble panelDouble = new PanelRepoDouble();
        service = new PanelService(panelDouble);
    }

    @Test
    void shouldAddValidPanel() throws DataAccessException {
        //since we implemented .equals() and .hashCode
        // we can compare our objects like this (expected, payload)
        Panel toAdd = new Panel();
        toAdd.setMaterialType(Material.CADMIUM_TELLURIDE);
        toAdd.setSection("Section C");
        toAdd.setRow(13);
        toAdd.setColumn(13);
        toAdd.setYearInstalled(2000);

        PanelResult result = service.addPanel(toAdd);
        assertTrue(result.isSuccess());

        Panel expected = new Panel();
        expected.setPanelId(13);
        expected.setMaterialType(Material.CADMIUM_TELLURIDE);
        expected.setSection("Section C");
        expected.setRow(13);
        expected.setColumn(13);
        expected.setYearInstalled(2000);
    }

    @Test
    void shouldNotAddPanelWithNullMaterial() throws DataAccessException {
        Panel toAdd = new Panel();
        toAdd.setMaterialType(null);
        toAdd.setSection("Section C");
        toAdd.setRow(13);
        toAdd.setColumn(13);
        toAdd.setYearInstalled(2000);

        PanelResult result = service.addPanel(toAdd);
        assertFalse(result.isSuccess());

        Panel expected = new Panel();
        expected.setPanelId(13);
        expected.setMaterialType(Material.CADMIUM_TELLURIDE);
        expected.setSection("Section C");
        expected.setRow(13);
        expected.setColumn(13);
        expected.setYearInstalled(2000);
    }

    @Test
    void shouldNotAddPanelWithNullSection() throws DataAccessException {
        Panel toAdd = new Panel();
        toAdd.setMaterialType(Material.CADMIUM_TELLURIDE);
        toAdd.setSection(null);
        toAdd.setRow(13);
        toAdd.setColumn(13);
        toAdd.setYearInstalled(2000);

        PanelResult result = service.addPanel(toAdd);
        assertFalse(result.isSuccess());

        Panel expected = new Panel();
        expected.setPanelId(13);
        expected.setMaterialType(Material.CADMIUM_TELLURIDE);
        expected.setSection("Section C");
        expected.setRow(13);
        expected.setColumn(13);
        expected.setYearInstalled(2000);
    }
    @Test
    void shouldNotAddPanelWithNonPositiveRow() throws DataAccessException {
        Panel toAdd = new Panel();
        toAdd.setMaterialType(Material.CADMIUM_TELLURIDE);
        toAdd.setSection("Section C");
        toAdd.setRow(-5);
        toAdd.setColumn(13);
        toAdd.setYearInstalled(2000);

        PanelResult result = service.addPanel(toAdd);
        assertFalse(result.isSuccess());

        Panel expected = new Panel();
        expected.setPanelId(13);
        expected.setMaterialType(Material.CADMIUM_TELLURIDE);
        expected.setSection("Section C");
        expected.setRow(13);
        expected.setColumn(13);
        expected.setYearInstalled(2000);

    }
    @Test
    void shouldNotAddPanelWithRowLargerThan250() throws DataAccessException {
        Panel toAdd = new Panel();
        toAdd.setMaterialType(Material.CADMIUM_TELLURIDE);
        toAdd.setSection("Section C");
        toAdd.setRow(251);
        toAdd.setColumn(13);
        toAdd.setYearInstalled(2000);

        PanelResult result = service.addPanel(toAdd);
        assertFalse(result.isSuccess());

        Panel expected = new Panel();
        expected.setPanelId(13);
        expected.setMaterialType(Material.CADMIUM_TELLURIDE);
        expected.setSection("Section C");
        expected.setRow(13);
        expected.setColumn(13);
        expected.setYearInstalled(2000);

    }
    @Test
    void shouldNotAddPanelWithNonPositiveCol() throws DataAccessException {
        Panel toAdd = new Panel();
        toAdd.setMaterialType(Material.CADMIUM_TELLURIDE);
        toAdd.setSection("Section C");
        toAdd.setRow(13);
        toAdd.setColumn(-2);
        toAdd.setYearInstalled(2000);

        PanelResult result = service.addPanel(toAdd);
        assertFalse(result.isSuccess());

        Panel expected = new Panel();
        expected.setPanelId(13);
        expected.setMaterialType(Material.CADMIUM_TELLURIDE);
        expected.setSection("Section C");
        expected.setRow(13);
        expected.setColumn(13);
        expected.setYearInstalled(2000);

    }
    @Test
    void shouldNotAddPanelWithColumnLargerThan250() throws DataAccessException {
        Panel toAdd = new Panel();
        toAdd.setMaterialType(Material.CADMIUM_TELLURIDE);
        toAdd.setSection("Section C");
        toAdd.setRow(13);
        toAdd.setColumn(252);
        toAdd.setYearInstalled(2000);

        PanelResult result = service.addPanel(toAdd);
        assertFalse(result.isSuccess());

        Panel expected = new Panel();
        expected.setPanelId(13);
        expected.setMaterialType(Material.CADMIUM_TELLURIDE);
        expected.setSection("Section C");
        expected.setRow(13);
        expected.setColumn(13);
        expected.setYearInstalled(2000);

    }

    @Test
    void shouldNotAddPanelWithFutureYear() throws DataAccessException {
        Panel toAdd = new Panel();
        toAdd.setMaterialType(Material.CADMIUM_TELLURIDE);
        toAdd.setSection("Section C");
        toAdd.setRow(13);
        toAdd.setColumn(13);
        toAdd.setYearInstalled(2026);

        PanelResult result = service.addPanel(toAdd);
        assertFalse(result.isSuccess());

        Panel expected = new Panel();
        expected.setPanelId(13);
        expected.setMaterialType(Material.CADMIUM_TELLURIDE);
        expected.setSection("Section C");
        expected.setRow(13);
        expected.setColumn(13);
        expected.setYearInstalled(2000);
    }

    @Test
    void shouldDeletePanelWithValidId() throws DataAccessException {
        Panel toDelete = new Panel();
        toDelete.setPanelId(12);

        PanelResult result = service.deleteById(12);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotDeletePanelWithInvalidId() throws DataAccessException {
        Panel nDelete = new Panel();
        nDelete.setPanelId(20);

        PanelResult result = service.deleteById(20);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldGetPanelsByValidSection() throws DataAccessException {
        List<Panel> result = service.getPanelBySections("Section A");
        Panel p = result.get(0);
        //asserts
        assertEquals(10, p.getPanelId());
        assertEquals(Material.MONOCRYSTALLINE_SILICON, p.getMaterialType());
        assertEquals("Section A", p.getSection());
        assertEquals(10, p.getRow());
        assertEquals(10, p.getColumn());
        assertEquals(2004, p.getYearInstalled());
    }

    @Test
    void shouldNotGetPanelsByInvalidSection() throws DataAccessException {
        List<Panel> result = service.getPanelBySections("Section Z");
        assertEquals(0, result.size());

    }

    @Test
    void shouldUpdateValidPanelWithoutMoving() throws DataAccessException {
//        Panel p1 = new Panel();
//        p1.setPanelId(10);
//        p1.setSection("Section A");
//        p1.setRow(10);
//        p1.setColumn(10);
//        allPanels.add(p1);

        //not moving so keep row, col, section same
        Panel toUpdate = new Panel();
        toUpdate.setPanelId(10);
        toUpdate.setMaterialType(Material.AMORPHOUS_SILICON);
        toUpdate.setYearInstalled(2002);

        toUpdate.setSection("Section A");
        toUpdate.setRow(10);
        toUpdate.setColumn(10);

        PanelResult result = service.updatedPanel(toUpdate);
        assertTrue(result.isSuccess());
        assertEquals(10, result.getPayload().getPanelId());
        assertEquals(Material.AMORPHOUS_SILICON, result.getPayload().getMaterialType());
        assertEquals(2002, result.getPayload().getYearInstalled());
    }

    @Test
    void shouldUpdateValidPanelWithMove() throws DataAccessException {
//        p1.setPanelId(10);
//        p1.setSection("Section A");
//        p1.setRow(10);
//        p1.setColumn(10);
//        allPanels.add(p1);

        //not moving so keep row, col, section same
        Panel toUpdate = new Panel();
        toUpdate.setPanelId(10);
        toUpdate.setMaterialType(Material.AMORPHOUS_SILICON);
        toUpdate.setYearInstalled(2002);

        toUpdate.setSection("Section E");
        toUpdate.setRow(100);
        toUpdate.setColumn(100);

        PanelResult result = service.updatedPanel(toUpdate);
        assertTrue(result.isSuccess());
        assertEquals(10, result.getPayload().getPanelId());
        assertEquals(Material.AMORPHOUS_SILICON, result.getPayload().getMaterialType());
        assertEquals(2002, result.getPayload().getYearInstalled());

        assertEquals("Section E", result.getPayload().getSection());
        assertEquals(100, result.getPayload().getRow());
        assertEquals(100, result.getPayload().getRow());
    }

    @Test
    void shouldNotUpdateNullPanel() throws DataAccessException {
        Panel uPanel = null;
        PanelResult uResult = service.updatedPanel(null);

        assertFalse(uResult.isSuccess());
        assertNull(uResult.getPayload());
        assertEquals( "Panel should not be null.", uResult.getErrorMessages().get(0));

    }

    @Test
    void shouldNotUpdatePanelWithNullSection() throws DataAccessException {
        Panel uPanel = new Panel();
        uPanel.setPanelId(10);
        uPanel.setSection(null);
        uPanel.setMaterialType(Material.AMORPHOUS_SILICON);
        uPanel.setRow(23);
        uPanel.setColumn(26);
        uPanel.setYearInstalled(2020);

        PanelResult updateResult = service.updatedPanel(uPanel);

        assertFalse( updateResult.isSuccess() );
        assertNull( updateResult.getPayload());
        assertEquals( "Section cannot be null", updateResult.getErrorMessages().get(0));
    }

    @Test
    void shouldNotUpdatePanelWithNullMaterial() throws DataAccessException {
        Panel uPanel = new Panel();
        uPanel.setPanelId(10);
        uPanel.setSection("Section B");
        uPanel.setMaterialType(null);
        uPanel.setRow(23);
        uPanel.setColumn(26);
        uPanel.setYearInstalled(2020);

        PanelResult updateResult = service.updatedPanel(uPanel);

        assertFalse( updateResult.isSuccess() );
        assertNull( updateResult.getPayload());
        assertEquals( "Material cannot be null", updateResult.getErrorMessages().get(0));
    }

    @Test
    void shouldNotUpdatePanelWithNonPositiveRow() throws DataAccessException {
        Panel uPanel = new Panel();
        uPanel.setPanelId(10);
        uPanel.setSection("Section B");
        uPanel.setMaterialType(Material.AMORPHOUS_SILICON);
        uPanel.setRow(-23);
        uPanel.setColumn(26);
        uPanel.setYearInstalled(2020);

        PanelResult updateResult = service.updatedPanel(uPanel);

        assertFalse( updateResult.isSuccess() );
        assertNull( updateResult.getPayload());
        assertEquals( "Row must be between 1 and 250", updateResult.getErrorMessages().get(0));
    }

    @Test
    void shouldNotUpdatePanelWithRowLargerThan250() throws DataAccessException {
        Panel uPanel = new Panel();
        uPanel.setPanelId(10);
        uPanel.setSection("Section B");
        uPanel.setMaterialType(Material.AMORPHOUS_SILICON);
        uPanel.setRow(2333);
        uPanel.setColumn(26);
        uPanel.setYearInstalled(2020);

        PanelResult updateResult = service.updatedPanel(uPanel);

        assertFalse( updateResult.isSuccess() );
        assertNull( updateResult.getPayload());
        assertEquals( "Row must be between 1 and 250", updateResult.getErrorMessages().get(0));

    }

    @Test
    void shouldNotUpdatePanelWithNonPositiveCol() throws DataAccessException {
        Panel uPanel = new Panel();
        uPanel.setPanelId(10);
        uPanel.setSection("Section B");
        uPanel.setMaterialType(Material.AMORPHOUS_SILICON);
        uPanel.setRow(23);
        uPanel.setColumn(-26);
        uPanel.setYearInstalled(2020);

        PanelResult updateResult = service.updatedPanel(uPanel);

        assertFalse( updateResult.isSuccess() );
        assertNull( updateResult.getPayload());
        assertEquals( "Column must be between 1 and 250", updateResult.getErrorMessages().get(0));
    }

    @Test
    void shouldNotUpdatePanelWithColLargerThan250() throws DataAccessException {
        Panel uPanel = new Panel();
        uPanel.setPanelId(10);
        uPanel.setSection("Section B");
        uPanel.setMaterialType(Material.AMORPHOUS_SILICON);
        uPanel.setRow(23);
        uPanel.setColumn(266);
        uPanel.setYearInstalled(2020);

        PanelResult updateResult = service.updatedPanel(uPanel);

        assertFalse( updateResult.isSuccess() );
        assertNull( updateResult.getPayload());
        assertEquals( "Column must be between 1 and 250", updateResult.getErrorMessages().get(0));

    }

    @Test
    void shouldNotUpdatePanelWithFutureYear() throws DataAccessException {
        Panel uPanel = new Panel();
        uPanel.setPanelId(10);
        uPanel.setSection("Section B");
        uPanel.setMaterialType(Material.AMORPHOUS_SILICON);
        uPanel.setRow(23);
        uPanel.setColumn(26);
        uPanel.setYearInstalled(2029);

        PanelResult updateResult = service.updatedPanel(uPanel);

        assertFalse( updateResult.isSuccess());
        assertNull( updateResult.getPayload());
        assertEquals( "Year cannot be in the future", updateResult.getErrorMessages().get(0));
    }

}


