package com.example.spring.basic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
classes= ApplicationContextTestResourceNameType.class)
// Inject Type, Name, Qualifier
//Unlike the @Resource annotation, which resolves dependencies by name first; the default behavior of the @Inject annotation resolves dependencies by type.
public class BaiscInjectTest {
	
	@Test
	public void givenResourceAnnotation_WhenOnField_ThenDependencyValid() {
	}
}
