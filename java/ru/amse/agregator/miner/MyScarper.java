package ru.amse.agregator.miner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;

public class MyScarper {

	private final String path = "src/ru/amse/agregator/miner/resources/";
	private String linksFile;
	private String configFile;
		
	FileReader linksReader;
	BufferedReader linksBuffer;
	int i;

	
	public MyScarper(String inLinks, String inConfig){
		
		linksFile = inLinks;
		configFile = inConfig;
		i = 0;
	}

	public void minerStart() throws IOException{
		
		String tmp;
		linksReader = new FileReader(path + linksFile);
		linksBuffer = new BufferedReader(linksReader);
		
		while((tmp = linksBuffer.readLine()) != null){
		
			System.out.print(i++);
			
			ScraperConfiguration config = new ScraperConfiguration(path + configFile);
			MyCityListener listener = new MyCityListener();
			Scraper scraper = new Scraper(config, path);
			
			scraper.addVariableToContext("linkToScrap", tmp);
			scraper.addRuntimeListener(listener);
			//scraper.getHttpClientManager().setHttpProxy("192.168.5.250", 3128);
			
			scraper.execute();
			
		}	
	}

	
}
