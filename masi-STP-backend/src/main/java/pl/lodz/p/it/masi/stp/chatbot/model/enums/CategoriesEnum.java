package pl.lodz.p.it.masi.stp.chatbot.model.enums;

public enum CategoriesEnum {

    BOOKS("Books", "283155", false),
    BIOGRAPHIES_MEMOIRS("Biographies & Memoirs", "2", false),
    HISTORY("History", "9", false),

    AFRICA("Africa", "4762", true),
    AMERICAS("Americas", "4808", true),
    ARCTIC_ANTARCTICA("Arctic & Antarctica", "16252761", true),
    ASIA("Asia", "4884", true),
    AUSTRALIA_OCEANIA("Australia & Oceania", "4921", true),
    EUROPE("Europe", "4935", true),
    MIDDLE_EAST("Middle East", "4995", true),
    RUSSIA("Russia", "5032", true),
    UNITED_STATES("United States", "4853", true),
    WORLD("World", "5035", true),

    ARTS_LITERATURE("Arts & Literature", "2327", true),
    ETHNIC_NATIONAL("Ethnic & National", "2365", true),
    HISTORICAL("Historical", "2376", true),
    LEADERS_NOTABLE_PEOPLE("Leaders & Notable People", "2396", true),
    MEMOIRS("Memoirs", "3048891", true),
    PROFESSIONALS_ACADEMICS("Professionals & Academics", "2419", true),
    REFERENCE_COLLECTIONS("Reference & Collections", "2429", true),
    REGIONAL_CANADA("Regional Canada", "1043842", true),
    REGIONAL_US("Regional U.S.", "2430", true),
    SPECIFIC_GROUPS("Specific Groups", "2437", true);

    private String name;
    private String browseNodeId;
    private boolean subcategory;

    CategoriesEnum(String name, String browseNodeId, boolean subcategory) {
        this.name = name;
        this.browseNodeId = browseNodeId;
        this.subcategory = subcategory;
    }

    public String getName() {
        return name;
    }

    public String getBrowseNodeId() {
        return browseNodeId;
    }

    public boolean isSubcategory() {
        return subcategory;
    }
}
