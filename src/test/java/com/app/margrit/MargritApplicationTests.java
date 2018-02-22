package com.app.margrit;

import com.app.margrit.entities.Class;
import com.app.margrit.repositories.ClassRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@EnableJpaRepositories
public class MargritApplicationTests {

	@Autowired
	private ClassRepository classRepository;

	@Test
	public void contextLoads() {

	}

	@Test
	public void testRepository() {
		classRepository.save(new Class("LeoClass"));

		Class leoClass = classRepository.findOne("LeoClass");

		leoClass.toString();
	}

}
