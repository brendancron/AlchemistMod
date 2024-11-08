package com.cron.alchemistmod.util;

public class InsertSpacesIntoString {
    public static String insertSpacesIntoString(String input) {
        // Use a regular expression to match uppercase letters and add a space before them
        return input.replaceAll("([a-z])([A-Z])", "$1 $2");
    }
}
