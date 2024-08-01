package learn.solar.ui;

import learn.solar.data.DataAccessException;
import learn.solar.domain.PanelResult;
import learn.solar.domain.PanelService;
import learn.solar.models.Panel;

import java.util.List;

public class PanelController {
//    - add fields for service and view with corresponding constructor
//    - add a `run` method

    private final PanelService service;
    private final PanelView view;

    public PanelController(PanelService service, PanelView view) {
        this.service = service;
        this.view = view;
    }

    public void run() throws DataAccessException {
        System.out.println("Welcome to Solar Farm");
        System.out.println("=".repeat("Welcome to Solar Farm".length()));

        setup();
    }

    private void setup() throws DataAccessException {
        //implement menu
        boolean exit = false;
        while (!exit) {
            MainMenu userInput = view.displayMainMenu();
            switch (userInput) {
                case EXIT:
                    exit = true;
                    System.out.println("Goodbye");
                    break;
                case FIND_PANELS_BY_SECTION:
                    viewBySection();
                    break;
                case ADD_A_PANEL:
                    addPanel();
                    break;
                case UPDATE_A_PANEL:
                    updatePanel();
                    break;
                case REMOVE_A_PANEL:
                    deletePanel();
                    break;
            }
        }
    }
    private void viewBySection() throws DataAccessException {
//    - implement the "view by section" feature
//        1. collect section name from the view
        String sectionName = view.getSectionName();
//        2. use the name to fetch panels from the service
        List<Panel> fetchedPanels = service.getPanelBySections(sectionName);
//        3. use the view to display panels
        if ((fetchedPanels != null) && (fetchedPanels.size() > 0)) {
            view.displayPanels(fetchedPanels);
        } else {
            view.displayMessage("Couldn't find panels in section " + sectionName);
        }
    }

    private void addPanel() throws DataAccessException {
//    - implement the "add panel" feature
//        1. collect a complete and new panel from the view
        Panel newPanel = view.addPanel();
//        2. use the service to add the panel and grab its result
        PanelResult result = service.addPanel(newPanel);
//        3. display the result in the view
        if (result.isSuccess()) {
            view.displayMessage("Panel " + result.getPayload().getPanelId() + " created.");
        } else {
            view.displayErrors(result.getErrorMessages());
        }
    }
    private void updatePanel() throws DataAccessException {
//        1. collect section name from the view
        String sectionName = view.getSectionName();
//        2. choose panel to update
        List<Panel> fetchedPanels = service.getPanelBySections(sectionName);
        if(fetchedPanels.size() == 0){
            view.displayMessage("No Panels in Section");
            return;
        }
        //3. choose a panel to update
        Panel selectedPanel = view.selectSectionPanel(sectionName, fetchedPanels);
        //4. if panel is empty
        if (selectedPanel == null) {
            view.displayMessage("Panel not found");
            return;
        }
        //5.let user update panel
        Panel updated = view.updatePanel(selectedPanel);
        //6. save and display results
        PanelResult result = service.updatedPanel(updated);
        if (result.isSuccess()) {
            view.displayUpdate(result.getPayload());
        } else {
            view.displayErrors(result.getErrorMessages());
        }
    }
    private void deletePanel() throws DataAccessException {
        view.deleteHeader();

        String sectionName = view.getSectionName();
        List<Panel> panels = service.getPanelBySections(sectionName);
        //choosing panel to delete
        Panel toDelete = view.selectSectionPanel(sectionName, panels);
        //if it's empty
        if (toDelete == null) {
            view.displayMessage("Panel not found");
            return;
        }
        // save and display results
        PanelResult result = service.deleteById(toDelete.getPanelId());
        if (result.isSuccess()) {
            view.displayDeleted(toDelete);
        } else {
            view.displayErrors(result.getErrorMessages());
        }


    }
}


