package learn.solar.ui;

import learn.solar.domain.PanelResult;
import learn.solar.models.Material;
import learn.solar.models.Panel;

import java.util.List;

public class PanelView {

    Console console;

    public PanelView(Console console) {
        this.console = console;
    }

    public MainMenu displayMainMenu() {

        System.out.println("Main Menu");
        System.out.println("=".repeat("Main Menu".length()));

        System.out.println("0. Exit");
        System.out.println("1. Find Panels by Section");
        System.out.println("2. Add a Panel");
        System.out.println("3. Update a Panel");
        System.out.println("4. Remove a Panel");
        System.out.println("Select [0-4]: ");

        int userInput = console.getInt("Please enter your selection: ", 0, 4);

        switch (userInput) {
            case 0:
                return MainMenu.EXIT;
            case 1:
                return MainMenu.FIND_PANELS_BY_SECTION;
            case 2:
                return MainMenu.ADD_A_PANEL;
            case 3:
                return MainMenu.UPDATE_A_PANEL;
            case 4:
                return MainMenu.REMOVE_A_PANEL;
        }
        return displayMainMenu();
    }

    public Material displayMaterials(){
        System.out.println("Pick a Material");

        System.out.println("0. MCS");
        System.out.println("1. MS");
        System.out.println("2. AS");
        System.out.println("3. CT");
        System.out.println("4. CIGS");

        int userInput = console.getInt("Please enter your selection: ", 0, 4);

        switch (userInput) {
            case 0:
                return Material.MULTICRYSTALLINE_SILICON;
            case 1:
                return Material.MONOCRYSTALLINE_SILICON;
            case 2:
                return Material.AMORPHOUS_SILICON;
            case 3:
                return Material.CADMIUM_TELLURIDE;
            case 4:
                return Material.COPPER_INDIUM_GALLIUM_SELENIDE;
        }
        return null;
    }
    private Material editMaterials(Material originalMaterial) {
        System.out.println("Pick a Material");

        System.out.println("0. MCS");
        System.out.println("1. MS");
        System.out.println("2. AS");
        System.out.println("3. CT");
        System.out.println("4. CIGS");

        int existingMenu = - 1;

        switch (originalMaterial){
            case MULTICRYSTALLINE_SILICON:
                existingMenu = 0;
                break;
            case MONOCRYSTALLINE_SILICON:
                existingMenu = 1;
                break;
            case AMORPHOUS_SILICON:
                existingMenu = 2;
                break;
            case CADMIUM_TELLURIDE:
                existingMenu = 3;
                break;
            case COPPER_INDIUM_GALLIUM_SELENIDE:
                existingMenu = 4;
                break;
        }

        int userInput = console.editInt("Please enter your selection", 0 , 4, existingMenu);

        switch (userInput) {
            case 0:
                return Material.MULTICRYSTALLINE_SILICON;
            case 1:
                return Material.MONOCRYSTALLINE_SILICON;
            case 2:
                return Material.AMORPHOUS_SILICON;
            case 3:
                return Material.CADMIUM_TELLURIDE;
            case 4:
                return Material.COPPER_INDIUM_GALLIUM_SELENIDE;
        }
        return null;
    }

    public void displayPanels(List<Panel> panels){
        //1. Print header
        System.out.println("Panels in Section");
        System.out.println("=".repeat("Panels in Section".length()));
        //2. Loop through list of Panels
        //3. Print each Panel
        for (Panel toPrint : panels){
            System.out.printf("%3s | %18s | %10s | %3s | %3s | %5s | %4s%n ",
                    toPrint.getPanelId(),
                    toPrint.getMaterialType(),
                    toPrint.getSection(),
                    toPrint.getRow(),
                    toPrint.getColumn(),
                    toPrint.getYearInstalled(),
                    toPrint.isTracking());
        }
    }
    public Panel findPanelBySection() {
        System.out.println("Find Panels by Section");
        System.out.println("=".repeat("Find Panels by Section".length()));

        Panel finding = new Panel();
        String section = console.getRequiredString("Section Name: ");
        finding.setSection(section);
        return finding;
    }

    public Panel addPanel(){
        System.out.println("Add a Panel");
        System.out.println("=".repeat("Add a Panel".length()));

        Panel adding = new Panel();

        String section = console.getRequiredString("Section Name: ");
        int row = console.getInt("Enter Row:  ", 1, 250);
        int col = console.getInt("Enter Column: ", 1, 250);
        Material material = displayMaterials();
        int yearInstalled = console.getInt("Enter Year Installed: ", 1, 2022);
        boolean isTracking = console.getBoolean("Tracking [y/n]: ");

        adding.setSection(section);
        adding.setRow(row);
        adding.setColumn(col);
        adding.setMaterialType(material);
        adding.setYearInstalled(yearInstalled);

        return adding;
    }

    public Panel updatePanel (Panel uPanel){

        System.out.println("Update a Panel");
        System.out.println("=".repeat("Update a Panel".length()));
        System.out.println("Press [Enter] to keep existing value ");

        String updatedSection = console.editRequiredString("Enter Section Name",
                uPanel.getSection());

        int updatedRow = console.editInt("Enter Row", 1, 250, uPanel.getRow());

        int updatedCol = console.editInt("Enter Col", 1, 250, uPanel.getColumn());

        Material material = editMaterials(uPanel.getMaterialType());

        int yearInstalled = console.editInt("Enter Year Installed", 1, 2022, uPanel.getYearInstalled());

        boolean isTracking = console.editBoolean("Tracking? [y/n]" , uPanel.isTracking());

        uPanel.setSection(updatedSection);
        uPanel.setRow(updatedRow);
        uPanel.setColumn(updatedCol);
        uPanel.setMaterialType(material);
        uPanel.setYearInstalled(yearInstalled);
        return uPanel;
    }

    public void displayErrors(List<String> errorMessages) {
    }
    public void displayMessage(String message) {
        System.out.println(message);
    }

    public String getSectionName() {
        return console.getRequiredString("Section Name: ");
    }

    public Panel selectSectionPanel(String sectionName, List<Panel> fetchedPanels) {
        System.out.println(sectionName);
        for( int i = 0; i < fetchedPanels.size(); i++){
            Panel toDisplay = fetchedPanels.get(i);

            System.out.println(( (i+1) +": [" + toDisplay.getRow() + ", " + toDisplay.getColumn() + "]"));
        }

        int selectedIndex = console.getInt("Please choose Panel: ", 1, fetchedPanels.size()) - 1;

        return fetchedPanels.get(selectedIndex);
    }

    public void displayUpdate(Panel payload) {
        System.out.println(payload.getSection() + "-" + payload.getRow() + "-" + payload.getColumn() + " successfully updated!");
    }

    public void displayDeleted(Panel payload) {
        System.out.println(payload.getSection() + "-" + payload.getRow() + "-" + payload.getColumn() + " successfully removed!");
    }

    public Panel selectPanelToDelete(List<Panel> fetchedPanels) {
        displayPanels(fetchedPanels);
        Panel result = null;
        if(fetchedPanels.size() > 0){
            int panelId = console.readInt("Choose a Panel Id");
            for (Panel p :fetchedPanels){
                if(p.getPanelId() == panelId){
                    result = p;
                    break;
                }
            }
        }
        return result;
    }

    public void deleteHeader() {
        System.out.println("Remove a Panel: ");
        System.out.println("=".repeat("Remove a Panel".length()));
    }
}