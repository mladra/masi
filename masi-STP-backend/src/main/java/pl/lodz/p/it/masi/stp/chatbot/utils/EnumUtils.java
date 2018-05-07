package pl.lodz.p.it.masi.stp.chatbot.utils;

import pl.lodz.p.it.masi.stp.chatbot.amazon.CategoriesEnum;

import java.util.HashMap;
import java.util.Map;

public class EnumUtils {

    private static final Map<String, CategoriesEnum> nameIndex = new HashMap<>(CategoriesEnum.values().length);

    static {
        for (CategoriesEnum categoriesEnum : CategoriesEnum.values()) {
            nameIndex.put(categoriesEnum.name(), categoriesEnum);
        }
    }

    public static CategoriesEnum lookupByName(String name) {
        return nameIndex.get(name);
    }

}
