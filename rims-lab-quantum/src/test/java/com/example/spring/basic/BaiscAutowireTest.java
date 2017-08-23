package com.example.spring.basic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.example.spring.basic.car.Car;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
classes= ApplicationContextTestResourceNameType.class)
// Type, Qualifier, Name
//Unlike the @Resource annotation, which resolves dependencies by name first; the default behavior of the @Inject annotation resolves dependencies by type.
public class BaiscAutowireTest {
	
	@Autowired
	@Qualifier("myCar1")
	private Car myCar1;
	@Test
	public void givenResourceAnnotation_WhenOnField_ThenDependencyValid() {
		assertNotNull(myCar1);
	}
}
