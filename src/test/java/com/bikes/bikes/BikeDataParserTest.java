package com.bikes.bikes;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.bikes.main.AppConfig;
import com.bikes.service.BikeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class BikeDataParserTest {

	@Autowired
	private BikeService bikeService;

	@BeforeClass
	public static void setUp() {
		System.out.println("-----> Start Parsing <-----");
	}

	@Test
	public void testSampleService() {
		assertEquals("class com.bikes.service.BikeServiceImpl", this.bikeService.getClass().toString());
	}

	@Test
	public void testSampleServiceGetAccountDescription() {
		bikeService.getTop20Bikes("https://trekhiringassignments.blob.core.windows.net/interview/bikes.json");
	}

	@AfterClass
	public static void afterTest() {
		System.out.println("-----> End Parsing <-----");
	}
}
