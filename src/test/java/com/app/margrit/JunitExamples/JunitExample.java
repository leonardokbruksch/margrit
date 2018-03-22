package com.app.margrit.JunitExamples;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JunitExample {

    @Test
    public void testingBooleans(){
        String test = "11.6";

        double doubletest = Double.parseDouble(test);

        double dm = 12.6D;

        Assert.assertEquals(11.6, doubletest, 0);
    }
}
