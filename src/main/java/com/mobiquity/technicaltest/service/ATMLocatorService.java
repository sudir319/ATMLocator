package com.mobiquity.technicaltest.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiquity.technicaltest.service.model.Address;
import com.mobiquity.technicaltest.service.model.Atm;

/**
 * This is the service class wraps the required business logic for the ATM Locator Controller.
 * @author schigurupati
 */
@Service
public class ATMLocatorService 
{
	//Read the External ATMs EndPoint from the application.properties
	@Value("${ATMS_ENDPOINT}")
	String atmsEndPoint;
	
	@Value("${cacheATMsData}")
	boolean cacheATMsData;

	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * Defining this variable at class level can be used to cache the value.
	 * This will reduce the no.of roundtrips to the service endpoint and improves the response time
	 * 
	 * Timeout to clear the cache, can be implemented based on requirement.
	 */
	private List<Atm> atmsList;

	/**
	 * This method is to return all the ATMs information if city is not passed, or ATMs info if the city is passed
	 */
	public List<Atm> getAllATMsList(String city)
	{
		/**
		 * If the request comes for the very first time after the server is up, 
		 * then fetch all the ATMs related content from the Service endpoint
		 * Parse the data into Java object, to simplify the searching for cities later.
		 * 
		 * if the cacheATMsData variable is false in the application.properties 
		 * then it will reach the endpoint for every request.
		 */
		if(atmsList == null || !cacheATMsData)
		{
			String allATMsJsonString = getAllATMs();
			ObjectMapper objectMapper = new ObjectMapper();
			
			Atm[] atmsArr;
			try {
				atmsArr = objectMapper.readValue(allATMsJsonString, Atm[].class);
				atmsList = Arrays.asList(atmsArr);
			} 
			catch (Exception e) {
				return null;
			}
		}
		
		/**
		 * If city is not passed, then return all the ATMs information.
		 * Otherwise, search for the city across all the objects and 
		 * return the appropriate ATMs information back to the user.
		 */
		if(city == null)
		{
			return atmsList;
		}
		else
		{
			//Filter the ATMs whose city is same as the input value.
			List<Atm> atmsListByCity = atmsList.stream()
					.filter(eachAtmDetails -> city.equals(eachAtmDetails.getAddress().getCity()))
					.collect(Collectors.toList());

			return atmsListByCity;
		}
	}
	
	/**
	 * This method is used to find the no. of AMTs existing per city
	 * This is created just to view the data if needed.
	 */
	public Map<String, Long> getATMsCountByCity(boolean ascending)
	{
		getAllATMsList(null);
		
		//This logic is to cound the ATMs per city and store the information in a Map
		Map<String, Long> atmsCountByCity = atmsList.stream()
		.map(eachAtm -> eachAtm.getAddress())
		.collect(Collectors.groupingBy(Address::getCity, Collectors.counting()));
		
		Comparator<Entry<String, Long>> comparingByValue = null;
		
		//If the input value ascending is true, then sort the Map by count value in ascending, otherwise in descending order.
		if(ascending)
		{
			comparingByValue = Map.Entry.comparingByValue();
		}
		else
		{
			comparingByValue = Map.Entry.comparingByValue(Comparator.reverseOrder());
		}
		
		//Below logic is to apply sorting logic on the Map to return a new Map with sorted values.
		atmsCountByCity = atmsCountByCity.entrySet().stream()
				.sorted(comparingByValue)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap :: new));
		
		return atmsCountByCity;
	}
	
	/**
	 * This method is to read all the ATMs information from the Service end points
	 */
	public String getAllATMs()
	{
        ResponseEntity<String> result = restTemplate.getForEntity(atmsEndPoint, String.class);
        String allAtmsData = result.getBody();
        /**
         * The result from the end point is not well-formed, it doesn't start with [
         * in that case, find the first occurrence of it and ignore the characters prior to it
         */
        int indexOfOpenSquareBracket = allAtmsData.indexOf("[");
        if(indexOfOpenSquareBracket > 0)
        {
        	allAtmsData = allAtmsData.substring(indexOfOpenSquareBracket);
        }
        return allAtmsData;
	}
}
