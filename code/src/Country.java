import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


public class Country 
{
	//private Set<String> countries;
	private Map<String, Set<double[]>> cityGeocodes;
	
	public Country ()
	{
		cityGeocodes = new TreeMap<String, Set<double[]>>();
		Scanner s;
		try {
			s = new Scanner(new FileInputStream("geocode.txt"));
		while (s.hasNext())
		{
			String country = s.next();
			//System.out.println(country);
			//countries.add(country);
			String city = s.next();
			double latitude = s.nextDouble();
			double longitude = s.nextDouble();
			double[] coordinates = {latitude, longitude};
			if (cityGeocodes.keySet().contains(country))
			{
				Set<double[]> cities = cityGeocodes.get(country);
				cities.add(coordinates);
				cityGeocodes.put(country, cities);
			}
			else
			{
				Set<double[]> cities = new HashSet<double[]>();
				cities.add(coordinates);
				cityGeocodes.put(country, cities);
			}
		}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Set<String> getCountries()
	{
		return cityGeocodes.keySet();
	}
	
	public Set<double[]> getCities(String country)
	{
		if (cityGeocodes.keySet().contains(country))
		{
			return cityGeocodes.get(country);
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		Country c = new Country();
		for(String s : c.getCountries())
		{
			Set<double[]> cities = c.getCities(s);
			for (double[] d : cities)
				System.out.println(s + " : " + d[0]+ "   " + d[1]);
		}
	}
}
