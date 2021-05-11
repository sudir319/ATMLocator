package com.mobiquity.ATMLocator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiquity.technicaltest.AtmLocatorApplication;
import com.mobiquity.technicaltest.service.ATMLocatorService;
import com.mobiquity.technicaltest.service.model.Atm;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AtmLocatorApplication.class)
@AutoConfigureMockMvc
public class AtmLocatorApplicationTests {

	@Autowired
	ATMLocatorService service;
	
	@Autowired
	MockMvc mvc;
	
	@Test
	public void testNumberOfATMsFromServiceEndpoint() throws Exception
	{
		//Get all the ATMs from the Service Endpoint.
		MvcResult mvcResult = mvc.perform(get("/atms/all")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andReturn();
		
		String content = mvcResult.getResponse().getContentAsString();
		
		ObjectMapper mapper = new ObjectMapper();
		Atm[] atmsArr = mapper.readValue(content, Atm[].class);
		
		assertEquals(true, atmsArr != null);
	}
	
	@Test
	public void testNumberOfATMsPerCity() throws Exception
	{
		/**
		 * Get the cities and ATM count per city as a Map.
		 * This is used to get the ATMs per city for few cities and evaluate the count of ATMs
		 */
		MvcResult mvcResult = mvc.perform(get("/atms/countByCityDesc")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andReturn();
		
		String content = mvcResult.getResponse().getContentAsString();
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Integer> atmsPerCity = mapper.readValue(content, Map.class);
		assertNotNull(atmsPerCity);
		
		Integer noOfATMsPerCity = null;
		Atm[] atmsArr = null;
		
		List<String> fewCities = new ArrayList<>();
		
		fewCities.add("Amsterdam");
		fewCities.add("Rotterdam");
		fewCities.add("Utrecht");
		fewCities.add("Eindhoven");
		fewCities.add("Groningen");
		fewCities.add("Almere");
		fewCities.add("Breda");
		fewCities.add("Tilburg");
		fewCities.add("Nijmegen");
		fewCities.add("Haarlem");
		
		for(String cityName : fewCities) 
		{
			noOfATMsPerCity = atmsPerCity.get(cityName);
			
			mvcResult = mvc.perform(get("/atms/city/" + cityName)
				      .contentType(MediaType.APPLICATION_JSON))
				      .andExpect(status().isOk())
				      .andExpect(content()
				      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andReturn();
			
			content = mvcResult.getResponse().getContentAsString();
			atmsArr = mapper.readValue(content, Atm[].class);

			if(atmsArr != null)
			{
				assertEquals(cityName, noOfATMsPerCity.intValue(), atmsArr.length);
			}
		}
	}
}