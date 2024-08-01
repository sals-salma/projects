package learn.solar.domain;

import learn.solar.data.DataAccessException;
import learn.solar.data.PanelRepo;
import learn.solar.models.Panel;

import java.time.LocalDate;
import java.util.List;

public class PanelService {
//    - generate tests for the service
//    - create a `PanelRepositoryTestDouble` class to support service testing, this test class implements the `PanelRepository` interface
//    - implement and test all of the needed service methods

    PanelRepo panelRepository;

    public PanelService(PanelRepo panelRepository) {
        this.panelRepository = panelRepository;
    }

    public PanelResult addPanel(Panel p) throws DataAccessException {
        PanelResult result = validate(p);
        if(!result.isSuccess()){
            return result;
        }
        List<Panel> sectionPanels = panelRepository.findBySection(p.getSection());
        for( Panel toCheck : sectionPanels){
            if(toCheck.getRow() == p.getRow() && toCheck.getColumn() == p.getColumn()){
                result.addErrorMessage("Cannot add Panel to occupied locating");
                return result;
            }
        }
        //can never be panel at added location
        p = panelRepository.add(p);
        result.setPayload(p);
        return result;
    }

    public List<Panel> getPanelBySections(String section) throws DataAccessException {
        //1. not null
        //2. make sure its not blank
        //2. look up panel

        if (section == null || section.isBlank()) {
            return null;
        } else {
            return panelRepository.findBySection(section);
        }
    }

    public PanelResult updatedPanel(Panel uPanel) throws DataAccessException {
        // allowed to have panel at location if it's the one we're editing
        PanelResult result = validate(uPanel);
        if(!result.isSuccess()){
            return result;
        }

        List<Panel> sectionPanels = panelRepository.findBySection(uPanel.getSection());
        for( Panel toCheck : sectionPanels){
            if(toCheck.getRow() == uPanel.getRow() && toCheck.getColumn() == uPanel.getColumn()
                    && toCheck.getPanelId() != uPanel.getPanelId()){
                result.addErrorMessage("Cannot add Panel to occupied locating");
                return result;
            }
        }
        if (result.isSuccess()) {
            panelRepository.update(uPanel);
            result.setPayload(uPanel);
        } else {
            String message = String.format("Panel Id" + uPanel.getPanelId() + "was not found");
            result.addErrorMessage(message);
        }
        return result;
    }

    public PanelResult deleteById(int panelId) throws DataAccessException {
        PanelResult result = new PanelResult();

        if (!panelRepository.delete(panelId)) {
            String message = String.format("Panel ID" + panelId + "was not found.");
            result.addErrorMessage(message);
        }
        return result;
    }


    private PanelResult validate(Panel panel) {
        // we can't make a panel without these things
        PanelResult result = new PanelResult();
        if (panel == null) {
            result.addErrorMessage("Panel should not be null.");
            return result;
        }
        if (panel.getMaterialType() == null) {
            result.addErrorMessage("Material cannot be null");
        }
        if (panel.getSection() == null) {
            result.addErrorMessage("Section cannot be null");
        }
        if (panel.getRow() < 1 || panel.getRow() > 250) {
            result.addErrorMessage("Row must be between 1 and 250");
        }
        if (panel.getColumn() < 1 || panel.getColumn() > 250) {
            result.addErrorMessage("Column must be between 1 and 250");
        }
        if (panel.getYearInstalled() > LocalDate.now().getYear()) {
            result.addErrorMessage("Year cannot be in the future");
        }
        return result;
    }

}
