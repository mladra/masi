package pl.lodz.p.it.masi.stp.chatbot.utils;

import pl.lodz.p.it.masi.stp.chatbot.model.enums.CategoriesEnum;
import pl.lodz.p.it.masi.stp.chatbot.model.enums.SearchKeywordsEnum;

import java.util.HashMap;
import java.util.Map;

public class EnumUtils {

    private static final Map<String, CategoriesEnum> categories = new HashMap<>(CategoriesEnum.values().length);
    private static final Map<String, SearchKeywordsEnum> searchKeywords = new HashMap<>(SearchKeywordsEnum.values().length);

    static {
        for (CategoriesEnum categoriesEnum : CategoriesEnum.values()) {
            categories.put(categoriesEnum.name(), categoriesEnum);
        }

        for (SearchKeywordsEnum searchKeywordsEnum : SearchKeywordsEnum.values()) {
            searchKeywords.put(searchKeywordsEnum.name(), searchKeywordsEnum);
        }
    }

    public static Enum<?> lookupByName(String name, Class<? extends Enum<?>> clazz) {
        if (clazz.equals(CategoriesEnum.class)) {
            return categories.get(name);
        } else if (clazz.equals(SearchKeywordsEnum.class)) {
            return searchKeywords.get(name);
        }

        return null;
    }

}
