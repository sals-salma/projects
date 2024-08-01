package learn.solar.data;

import learn.solar.models.Material;
import learn.solar.models.Panel;

import java.util.ArrayList;
import java.util.List;

public class PanelRepoDouble implements PanelRepo {
    //having a hard time with double and service test
    //skipping for now

    //My understanding on Test Doubles (implements interface repo):
    //Purpose: We don't want to communicate with our file and mess w/ it
    //1. Stores data
    //2. Uses data to decide if my crud works
    List<Panel> allPanels = new ArrayList<>();

    public PanelRepoDouble() {
        Panel p1 = new Panel();
        p1.setPanelId(10);
        p1.setMaterialType(Material.MONOCRYSTALLINE_SILICON);
        p1.setSection("Section A");
        p1.setRow(10);
        p1.setColumn(10);
        p1.setYearInstalled(2004);
        allPanels.add(p1);

        Panel p2 = new Panel();
        p2.setPanelId(11);
        p2.setMaterialType(Material.CADMIUM_TELLURIDE);
        p2.setSection("Section B");
        p2.setRow(11);
        p2.setColumn(11);
        allPanels.add(p2);

        Panel p3 = new Panel();
        p3.setPanelId(12);
        p3.setMaterialType(Material.MULTICRYSTALLINE_SILICON);
        p3.setSection("Section C");
        p3.setRow(12);
        p3.setColumn(12);
        allPanels.add(p3);
    }

    @Override
    public Panel add(Panel toCreate) throws DataAccessException {
        if (toCreate == null) {
            throw new DataAccessException("Trying to add a null Panel.");
        }
        int maxId = 0;
        for (Panel currentPanel : allPanels) {
            if (currentPanel.getPanelId() > maxId) {
                maxId = currentPanel.getPanelId();
            }
        }
        int newId = maxId + 1;
        toCreate.setPanelId(newId);
        allPanels.add(new Panel(toCreate));
        return toCreate;
    }

    @Override
    public List<Panel> findAll(){
        List<Panel> toReturn = new ArrayList<>();
        for (Panel toCopy : allPanels){
            toReturn.add(new Panel(toCopy));
        }
        return toReturn;
    }

    @Override
    public List<Panel> findBySection(String section) throws DataAccessException {
        List<Panel> toReturn = new ArrayList<>();
        for (Panel toCheck : allPanels){
            if(toCheck.getSection().equals(section)){
                toReturn.add(new Panel(toCheck));
                break;
            }
        }
        return toReturn;
    }

    @Override
    public boolean update(Panel updatedPanel) throws DataAccessException {

        if (updatedPanel == null){
            throw new DataAccessException("Updating with null panel");
        }
        List<Panel> uPanel = new ArrayList<>();
        for (Panel toCheck : allPanels){
            if( toCheck.getPanelId() != updatedPanel.getPanelId() ){
                uPanel.add( toCheck );
            } else {
                uPanel.add( new Panel(updatedPanel));
            }

        }
        return false;
    }

    @Override
    public boolean delete(int panelId) throws DataAccessException {
        for( int i = 0; i < allPanels.size(); i++ ){
            Panel toCheck = allPanels.get(i);

            if( toCheck.getPanelId() == panelId ){
                allPanels.remove( i );
                return true;
            }
        }
        return false;
    }

}
