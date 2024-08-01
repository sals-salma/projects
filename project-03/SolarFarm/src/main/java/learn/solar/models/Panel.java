package learn.solar.models;

import java.util.Objects;

public class Panel {

    //What I'm tracking

    private int panelId;
    private Material materialType;
    private String section;
    private int row;
    private int column;
    private int yearInstalled;
    private boolean isTracking;
    public Panel() {
    }
    public Panel (int panelId, Material materialType, String section, int row, int column, int yearInstalled, boolean isTracking){
        this.panelId = panelId;
        this.materialType = materialType;
        this.section = section;
        this.row = row;
        this.column = column;
        this.yearInstalled = yearInstalled;
        this.isTracking = isTracking;
    }

    public Panel(Panel toCopy) {
        this.panelId = toCopy.panelId;
        this.materialType = toCopy.materialType;
        this.section = toCopy.getSection();
        this.row = toCopy.getRow();
        this.column = toCopy.column;
        this.yearInstalled = toCopy.yearInstalled;
        this.isTracking = toCopy.isTracking;
    }


    public int getPanelId() {
        return panelId;
    }

    public void setPanelId(int panelId) {
        this.panelId = panelId;
    }

    public Material getMaterialType() {
        return materialType;
    }

    public void setMaterialType(Material materialType) {
        this.materialType = materialType;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getYearInstalled() {
        return yearInstalled;
    }

    public void setYearInstalled(int yearInstalled) {
        this.yearInstalled = yearInstalled;
    }
    public boolean isTracking() {
        return isTracking;
    }
    public void setTracking(boolean tracking) {
        isTracking = tracking;
    }

//MENTAL NOTE: From CarDealership lesson
    //with .equals() and .hashCode() implemented
    //we could compare Customer objects this way in our asserts
//        Customer expected = new Customer();
//        expected.setId(107);
//        expected.setFullName("Donald Duck");
//        expected.setEmail( "dduck@gmail.com");
    //assertEquals(expected, payload);
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Panel panel = (Panel) o;

        if (panelId != panel.panelId) return false;
        if (materialType == panel.materialType) return false;
        if (!Objects.equals(section, panel.section)) return false;
        if (row != panel.row) return false;
        if (column != panel.column) return false;
        if (yearInstalled != panel.yearInstalled) return false;
        return  (isTracking!= panel.isTracking);

    }

    @Override
    public int hashCode() {
        int result = panelId;
        result = 31 * result + (materialType != null ? materialType.hashCode() : 0);
        result = 31 * result + (section != null ? section.hashCode() : 0);
        result = 31 * result + row;
        result = 31 * result + column;
        result = 31 * result + yearInstalled;
        result = 31 * result + (isTracking ? 1 : 0);
        return result;
    }

}
