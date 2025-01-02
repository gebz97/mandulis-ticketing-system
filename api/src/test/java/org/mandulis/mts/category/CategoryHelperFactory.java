package org.mandulis.mts.category;

import java.util.List;

public class CategoryHelperFactory {
    private static Long id1 = 1L;
    private static Long id2 = 2L;
    private static String name1 = "Music";
    private static String name2 = "Comedian";
    private static String description = "Just a description";

    public static Category defaultCategory() {
        return new Category(id1, name1, description, null);
    }

    public static Category defaultCategorySec() {
        return new Category(id2, name2, description, null);
    }

    public static List<Category> listOfCategories() {
        return List.of(
                defaultCategory(),
                new Category(id2, name2, description, null)
        );
    }

}
