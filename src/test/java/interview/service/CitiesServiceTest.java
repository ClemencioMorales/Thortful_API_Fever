package interview.service;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.assertTrue;

/**
 * Simple unit tests for Factorial calculations
 *
 */
public class CitiesServiceTest {
	private CitiesService service;
	
	public CitiesServiceTest() {
		service = new CitiesService();
	}
	
	@Test
	public void testRetrieveCities() {
		List<String> cities = service.retrieveCities();
		checkListContainsTheCities(cities);
	}

	private void checkListContainsTheCities(List<String> cities) {
		assertTrue(cities.contains("London"));
		assertTrue(cities.contains("Madrid"));
		assertTrue(cities.contains("Rome"));
		assertTrue(cities.contains("Paris"));
		assertTrue(cities.contains("Beijing"));
		assertTrue(cities.contains("Bogota"));
		assertTrue(cities.contains("Washington"));
	}
}
