package com.mobiquity.technicaltest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobiquity.technicaltest.service.ATMLocatorService;
import com.mobiquity.technicaltest.service.model.Atm;

@RestController
@RequestMapping("/atms")
public class ATMLocatorController
{
	@Autowired
	ATMLocatorService atmLocatorService;
	
	@GetMapping("/all")
	public List<Atm> getAllATMsList()
	{
		return atmLocatorService.getAllATMsList(null);
	}
	
	@GetMapping("/city/{city}")
	public List<Atm> getATMsListByCity(@PathVariable String city)
	{
		return atmLocatorService.getAllATMsList(city);
	}
	
	@GetMapping("/countByCity")
	public Map<String, Long> getATMsCountByCity()
	{
		return atmLocatorService.getATMsCountByCity(true);
	}
	
	@GetMapping("/countByCityDesc")
	public Map<String, Long> getATMsCountByCityDesc()
	{
		return atmLocatorService.getATMsCountByCity(false);
	}

}