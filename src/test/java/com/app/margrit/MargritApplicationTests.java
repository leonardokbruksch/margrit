package com.app.margrit;

import com.app.margrit.entities.EntitiesTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MargritApplicationTests {

	@Test
	public void contextLoads() {
		EntitiesTest test = new EntitiesTest();
		test.testRepository();
	}

}
