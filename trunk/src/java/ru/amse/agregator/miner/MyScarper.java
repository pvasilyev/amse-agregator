package ru.amse.agregator.miner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;

public class MyScarper {

	private final String path; //= "ru/amse/agregator/miner/resources/";
	private String configFile;
		
	FileReader linksReader;
	BufferedReader linksBuffer;
	int i;

	
	public MyScarper(String path, String configFile){
		this.path = path;
		this.configFile = configFile;
		i = 0;
	}

	public void minerStart() throws IOException{
		
		System.out.print(i++);										
		ScraperConfiguration config = new ScraperConfiguration(path + configFile);
		MyCityListener listener = new MyCityListener();
		Scraper scraper = new Scraper(config, path);
		scraper.addRuntimeListener(listener);
		//scraper.getHttpClientManager().setHttpProxy("192.168.5.250", 3128);
		scraper.execute();
		
	}
}
