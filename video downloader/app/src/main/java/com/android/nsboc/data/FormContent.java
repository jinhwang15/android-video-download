package com.android.nsboc.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 */
public class FormContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<FormItem> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, FormItem> ITEM_MAP = new HashMap<>();

    static {
        // Add 2 form titles.
        addItem(new FormItem("1", "Unlicensed individual"));
        addItem(new FormItem("2", "Salon establishment"));
    }

    private static void addItem(FormItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class FormItem {
        public String id;
        public String title;

        public FormItem(String id, String title) {
            this.id = id;
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
