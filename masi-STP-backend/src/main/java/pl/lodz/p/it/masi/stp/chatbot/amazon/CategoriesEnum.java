package pl.lodz.p.it.masi.stp.chatbot.amazon;

public enum CategoriesEnum {

    BOOKS("Books", "283155"),
    BIOGRAPHIES_MEMOIRS("Biographies & Memoirs", "2"),
    HISTORY("History", "9"),

    AFRICA("Africa", "4762"),
    AMERICAS("Americas", "4808"),
    ARCTIC_ANTARCTICA("Arctic & Antarctica", "16252761"),
    ASIA("Asia", "4884"),
    AUSTRALIA_OCEANIA("Australia & Oceania", "4921"),
    EUROPE("Europe", "4935"),
    MIDDLE_EAST("Middle East", "4995"),
    RUSSIA("Russia", "5032"),
    UNITED_STATES("United States", "4853"),
    WORLD("World", "5035"),

    ARTS_LITERATURE("Arts & Literature", "2327"),
    ETHNIC_NATIONAL("Ethnic & National", "2365"),
    HISTORICAL("Historical", "2376"),
    LEADERS_NOTABLE_PEOPLE("Leaders & Notable People", "2396"),
    MEMOIRS("Memoirs", "3048891"),
    PROFESSIONALS_ACADEMICS("Professionals & Academics", "2419"),
    REFERENCE_COLLECTIONS("Reference & Collections", "2429"),
    REGIONAL_CANADA("Regional Canada", "1043842"),
    REGIONAL_US("Regional U.S.", "2430"),
    SPECIFIC_GROUPS("Specific Groups", "2437");

    private String name;
    private String browseNodeId;

    CategoriesEnum(String name, String browseNodeId) {
        this.name = name;
        this.browseNodeId = browseNodeId;
    }

    public String getName() {
        return name;
    }

    public String getBrowseNodeId() {
        return browseNodeId;
    }
}
