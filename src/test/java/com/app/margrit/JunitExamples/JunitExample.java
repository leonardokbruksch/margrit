package com.app.margrit.JunitExamples;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JunitExample {

    @Test
    public void testingBooleans(){
        boolean test = true;

        Assert.assertEquals(true, test);
    }
}
