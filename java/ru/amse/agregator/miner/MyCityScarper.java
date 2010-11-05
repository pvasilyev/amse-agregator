package ru.amse.agregator.miner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;

public class MyCityScarper {

	private final String path = "ru/amse/agregator/miner/resources/";
	private String linksFile;
	private String configFile;
	
	ScraperConfiguration config;
	Scraper scraper;
	MyCityListener listener;
	
	FileReader linksReader;
	BufferedReader linksBuffer;
	int i;

	
	public MyCityScarper(String inLinks, String inConfig){
		
		linksFile = inLinks;
		configFile = inConfig;
		i = 0;
	}

	public void execute() throws IOException{
		
		String tmp;
		linksReader = new FileReader(path + linksFile);
		linksBuffer = new BufferedReader(linksReader);
		
		while((tmp = linksBuffer.readLine()) != null){
		
			System.out.print(i++);
			config = new ScraperConfiguration(path + configFile);
			listener = new MyCityListener();
			scraper = new Scraper(config, path);
			scraper.addVariableToContext("linkToScrap", tmp);
			scraper.addRuntimeListener(listener);
			//scraper.getHttpClientManager().setHttpProxy("192.168.5.250", 3128);
			scraper.execute();
			
		}	
	}

	
}
