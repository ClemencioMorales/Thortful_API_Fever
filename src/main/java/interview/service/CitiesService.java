package interview.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CitiesService {

	public List<String> retrieveCities() {
		final List<String> cities = new ArrayList<String>();

		cities.add("London");
		cities.add("Madrid");
		cities.add("Rome");
		cities.add("Paris");
		cities.add("Beijing");
		cities.add("Bogota");
		cities.add("Washington");

		return cities;
	}
}
