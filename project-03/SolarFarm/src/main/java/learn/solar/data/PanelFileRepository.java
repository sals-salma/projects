package learn.solar.data;

import learn.solar.models.Material;
import learn.solar.models.Panel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PanelFileRepository implements PanelRepo {

//Create the `PanelFileRepository` class.
//   All methods should catch IOExceptions and throw the data layer's `DataException` custom exception.

    static final String DELIMITER = "//";
    String path;

    public PanelFileRepository(String path) {
        this.path = path;
    }

    public List<Panel> findAll() {
        List<Panel> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            //declaring variable to read line
            //skipping header
            reader.readLine();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                Panel converted = convertLineToPanel(line);
                result.add(converted);
            }
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private Panel convertLineToPanel(String line) {
        String[] fields = line.split(DELIMITER);

        Panel toMake = new Panel();
        toMake.setPanelId(Integer.parseInt(fields[0]));
        toMake.setMaterialType(Material.valueOf(fields[1]));
        toMake.setSection(fields[2]);
        toMake.setRow(Integer.parseInt(fields[3]));
        toMake.setColumn(Integer.parseInt(fields[4]));
        toMake.setYearInstalled(Integer.parseInt(fields[5]));
        toMake.setTracking(Boolean.parseBoolean(fields[6]));
        return toMake;
    }

    @Override
    public Panel add(Panel toCreate) throws DataAccessException {
        //Bad Input; TEST COMPLETED
        if (toCreate == null) {
            throw new DataAccessException("Trying to add a null Panel.");
        }
        //Good Input; TEST COMPLETED
        List<Panel> allPanels = findAll();
        int maxId = 0;
        for (Panel currentPanel : allPanels) {
            if (currentPanel.getPanelId() > maxId) {
                maxId = currentPanel.getPanelId();
            }
        }
        // newId is one larger so add 1
        int newId = maxId + 1;
        toCreate.setPanelId(newId);
        allPanels.add(toCreate);
        writeAll(allPanels);
        return toCreate;

    }

    @Override
    public List<Panel> findBySection(String section) {
        ArrayList<Panel> result = new ArrayList<>();
        //loop through everything
        for (Panel toFindSection : findAll()) {
            if (toFindSection.getSection().equals(section)) {
                result.add(toFindSection);
            }
        }
        return result;
    }
    @Override
    public boolean update(Panel updatedPanel) throws DataAccessException {
        //go through everything
        List<Panel> allPanels = findAll();
        for (int i = 0; i < allPanels.size(); i++) {
            if (allPanels.get(i).getPanelId() == updatedPanel.getPanelId()) {
                // set existing panel to updated pant
                allPanels.set(i, updatedPanel);
                // write to allPanels (use writeAll since we're editing)
                writeAll(allPanels);
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean delete(int panelId) throws DataAccessException {
        List<Panel> allPanels = findAll();
        for (int i = 0; i < allPanels.size(); i++) {
            if (allPanels.get(i).getPanelId() == panelId) {
                allPanels.remove(i);
                writeAll(allPanels);
                return true;
            }
        }
        return false;
    }

    private void writeAll(List<Panel> toWrite) throws DataAccessException {
        try (PrintWriter writer = new PrintWriter(path)) {
            //writing header row
            writer.println("Panel_Id" + DELIMITER + "Material" + DELIMITER + "Section" +
                    DELIMITER + "Row" + DELIMITER + "Column" + DELIMITER + "Year_Installed" + DELIMITER + "Tracking");
            for (Panel toConvert : toWrite) {
                String line = convertPanelToLine(toConvert);
                writer.println(line);
            }
        } catch (IOException ex) {
            DataAccessException wrappedException = new DataAccessException("Couldn't write all Panels", ex);
            throw wrappedException;
        }

    }

    private String convertPanelToLine(Panel toConvert) {
        return toConvert.getPanelId() + DELIMITER + toConvert.getMaterialType() + DELIMITER
                + toConvert.getSection() + DELIMITER + toConvert.getRow() + DELIMITER
                + toConvert.getColumn() + DELIMITER + toConvert.getYearInstalled() + DELIMITER + toConvert.isTracking();
    }

    public Panel findById(int panelId) {
        List<Panel> all = findAll();
        for (Panel p : all) {
            if (p.getPanelId() == panelId) {
                return p;
            }
        }
        return null;
    }
}
