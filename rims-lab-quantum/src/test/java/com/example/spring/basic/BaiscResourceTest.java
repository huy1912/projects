package com.example.spring.basic;

import java.io.File;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
classes= ApplicationContextTestResourceNameType.class)

//Unlike the @Resource annotation, which resolves dependencies by name first; the default behavior of the @Inject annotation resolves dependencies by type.
// a NoUniqueBeanDefinitionException will be thrown.
public class BaiscResourceTest {
	//@Resource(name = "namedFile")
	@Resource(name = "namedFile")
	private File defaultFile;
	
	@Resource
	@Qualifier("defaultFile")
	private File dependency1;
	
	private File dependency2;
	@Resource(name = "namedFile")
	protected void setNamedFile(File file) {
		dependency2 = file;
	}
	
	@Test
	public void givenResourceAnnotation_WhenOnField_ThenDependencyValid() {
		assertNotNull(defaultFile);
		assertEquals("namedFile.txt", defaultFile.getName());
		
		assertNotNull(dependency1);
		assertEquals("defaultFile.txt", dependency1.getName());
		
		assertNotNull(dependency2);
		assertEquals("namedFile.txt", dependency2.getName());
	}
}
