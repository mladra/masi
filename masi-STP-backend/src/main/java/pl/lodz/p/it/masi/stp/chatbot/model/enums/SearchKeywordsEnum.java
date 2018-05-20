package pl.lodz.p.it.masi.stp.chatbot.model.enums;

public enum SearchKeywordsEnum {

    /* Biographies & Memoirs */
    // Arts & Literature
    PICASSSO("Picasso", CategoriesEnum.ARTS_LITERATURE),
    MICKIEWICZ("Mickiewicz", CategoriesEnum.ARTS_LITERATURE),
    MOZART("Mozart", CategoriesEnum.ARTS_LITERATURE),
    CHOPIN("Chopin", CategoriesEnum.ARTS_LITERATURE),

    // Ethnic & National
    GERMANY("Germany", CategoriesEnum.ETHNIC_NATIONAL),
    JEWS("Jews", CategoriesEnum.ETHNIC_NATIONAL),
    POLAND("Poland", CategoriesEnum.ETHNIC_NATIONAL),
    NIGGERS("Niggers", CategoriesEnum.ETHNIC_NATIONAL),

    // Historical
    HITLER("Hitler", CategoriesEnum.HISTORICAL),
    WORLD_WAR("World War", CategoriesEnum.HISTORICAL),
    HOLOCAUST("Holocaust", CategoriesEnum.HISTORICAL),
    COLD_WAR("Cold War", CategoriesEnum.HISTORICAL),

    // Leaders & Notable People
    MARIE_CURIE("Marie Curie", CategoriesEnum.LEADERS_NOTABLE_PEOPLE),
    OSAMA_BIN_LADEN("Osama bin Laden", CategoriesEnum.LEADERS_NOTABLE_PEOPLE),
    DONALD_TRUMP("Donald Trump", CategoriesEnum.LEADERS_NOTABLE_PEOPLE),
    BARACK_OBAMA("Barack Obama", CategoriesEnum.LEADERS_NOTABLE_PEOPLE),

    // Memoirs
    KENNEDY("Kennedy", CategoriesEnum.MEMOIRS),
    LUTHER_KING("Luther King", CategoriesEnum.MEMOIRS),
    STEPHEN_KING("Stephen King", CategoriesEnum.MEMOIRS),
    TOLKIEN("Tolkien", CategoriesEnum.MEMOIRS),

    // Professionals & Academics
    STEVE_JOBS("Steve Jobs", CategoriesEnum.PROFESSIONALS_ACADEMICS),
    THE_WRIGHT_BROTHERS("The Wright Brothers", CategoriesEnum.PROFESSIONALS_ACADEMICS),
    ELON_MUSK("Elon Musk", CategoriesEnum.PROFESSIONALS_ACADEMICS),
    SHAKESPEARE("Shakespeare", CategoriesEnum.PROFESSIONALS_ACADEMICS),

    // Reference & Collections
    THE_SOULS_OF_BLACK_FOLK("The Souls of Black Folk", CategoriesEnum.REFERENCE_COLLECTIONS),
    THE_DARK_TOWER("The Dark Tower", CategoriesEnum.REFERENCE_COLLECTIONS),
    THE_FELLOWSHIP_OF_THE_RING("The Fellowship of the ring", CategoriesEnum.REFERENCE_COLLECTIONS),
    RELIGION("Religion", CategoriesEnum.REFERENCE_COLLECTIONS),

    // Regional Canada
    CHURCHILL("Churchill", CategoriesEnum.REGIONAL_CANADA),
    WHITE_FANG("White Fang", CategoriesEnum.REGIONAL_CANADA),

    // Regional U.S.
    KILLING_LINCOLN("Killing Lincoln", CategoriesEnum.REGIONAL_US),
    SLAVE("Slave", CategoriesEnum.REGIONAL_US),
    WHITE_CITY("White City", CategoriesEnum.REGIONAL_US),
    TOM_SAWYER("Tom Sawyer", CategoriesEnum.REGIONAL_US),

    // Specific Groups
    ART_OF_WAR("Art of War", CategoriesEnum.SPECIFIC_GROUPS),

    /* History */
    // Africa
    SOLDIERS("Soldiers", CategoriesEnum.AFRICA),
    PIRATES("Pirates", CategoriesEnum.AFRICA),
    LIBYA("Libya", CategoriesEnum.AFRICA),

    // Americas
    BERLIN_OLYMPICS("Berlin Olympics", CategoriesEnum.AMERICAS),
    BILL_O_REILLY("Bill O'Reilly's", CategoriesEnum.AMERICAS),
    TRUMP("Trump", CategoriesEnum.AMERICAS),

    // Arctic & Antarctica
    SHACKLETON("Shackleton", CategoriesEnum.ARCTIC_ANTARCTICA),
    KINGDOM_OF_ICE("Kingdom of Ice", CategoriesEnum.ARCTIC_ANTARCTICA),
    ENDURANCE("Endurance", CategoriesEnum.ARCTIC_ANTARCTICA),

    // Asia
    ANNA_KARENINA("Anna Karenina", CategoriesEnum.ASIA),
    VIETNAM("Vietnam", CategoriesEnum.ASIA),
    HIROSHIMA("Hiroshima", CategoriesEnum.ASIA),
    JAPAN("Japan", CategoriesEnum.ASIA),

    // Australia & Oceania
    INVISIBLE_MAN("Invisible Man", CategoriesEnum.AUSTRALIA_OCEANIA),

    // Europe
    IN_THE_BOAT("In the boat", CategoriesEnum.EUROPE),
    D_DAY("D Day", CategoriesEnum.EUROPE),
    MONTE_CASINO("Monte Casino", CategoriesEnum.EUROPE),

    // FIXME: mladra: Poniższe kody nigdy nie będą użyte prawdopodobnie
    // FIXME: mladra: Zostawiam je, bo może wymyślimy jak obejść limit
    // Middle East
    JULIUS_CAESAR("Julius Caesar", CategoriesEnum.MIDDLE_EAST),
    ISIS("ISIS", CategoriesEnum.MIDDLE_EAST),

    // Russia
    COMMUNIST("Communist", CategoriesEnum.RUSSIA),
    LENIN("Lenin", CategoriesEnum.RUSSIA),
    STALIN("Stalin", CategoriesEnum.RUSSIA),
    PUTIN("Putin", CategoriesEnum.RUSSIA),

    // United States
    WORLD_TRADE_CENTER("World Trade Center", CategoriesEnum.UNITED_STATES),
    CIVIL_WAR("Civil War", CategoriesEnum.UNITED_STATES),
    WAR_IN_VIETNAM("War in Vietnam", CategoriesEnum.UNITED_STATES),

    // World
    WORLD_WAR_II("World War II", CategoriesEnum.WORLD),
    JEWISH("Jewish", CategoriesEnum.WORLD),
    JESUS("Jesus", CategoriesEnum.WORLD),
    NAZI("Nazi", CategoriesEnum.WORLD),

    // FIXME: mladra: Poniższe kategorie są błędnie wpisane
    // FIXME: mladra: W przypadku obejścia limitu poprawić
    // Ancient Civilizations
    HOLY_BIBLE("Holy bible", CategoriesEnum.WORLD),
    ROME("Rome", CategoriesEnum.WORLD),
    THE_ODYSSEY("The Odyssey", CategoriesEnum.WORLD),
    POMPEII("Pompeii", CategoriesEnum.WORLD),
    AZTEC("Aztec", CategoriesEnum.WORLD),

    // Military
    AMERICAN_SNIPER("American Sniper", CategoriesEnum.WORLD),

    // Historical Study & Educational Resources
    MAIN_KAMPF("Mein Kampf", CategoriesEnum.WORLD),
    ILIAD("Iliad", CategoriesEnum.WORLD);

    private String phrase;
    private CategoriesEnum category;

    SearchKeywordsEnum(String phrase, CategoriesEnum category) {
        this.phrase = phrase;
        this.category = category;
    }

    public String getPhrase() {
        return phrase;
    }

    public CategoriesEnum getCategory() {
        return category;
    }
}
