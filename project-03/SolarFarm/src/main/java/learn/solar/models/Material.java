package learn.solar.models;

public enum Material {
    // my 5 materials
    MULTICRYSTALLINE_SILICON("MCS"),
    MONOCRYSTALLINE_SILICON("MS"),
    AMORPHOUS_SILICON("AS"),
    CADMIUM_TELLURIDE("CT"),
    COPPER_INDIUM_GALLIUM_SELENIDE("CIGS");

    private String displayMaterials;

    Material(String displayMaterials){
        this.displayMaterials = displayMaterials;
    }

    public String getDisplayMaterials() {
        return displayMaterials;
    }

}
