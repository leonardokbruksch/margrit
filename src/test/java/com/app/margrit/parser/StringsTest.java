package com.app.margrit.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringsTest {

    public static void main(String[] args) {
        String test = "public sendEmail(username : String) : boolean";

        String result = test.substring(test.indexOf("(") + 1, test.indexOf(")"));

        String yesNiggaa = result;
    }
}
