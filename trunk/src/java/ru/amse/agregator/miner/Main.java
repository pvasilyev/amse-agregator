package ru.amse.agregator.miner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.web.*;

import ru.amse.agregator.storage.DataBase;


public class Main {
	
	public static final String path = "ru/amse/agregator/miner/resources/";
	public static String linksFile;
	public static String configFile;
	
	private static int links;
	
	public static void main(String[] args) throws IOException {
	
		String mainFile;
		
		if(args.length == 1){
			mainFile = args[0].toString();
		}
		else{
			System.out.println("Please specify an input file.");
			return;
		}
		
		DataBase.connectToDirtyBase();		
		//DataBase.removeCollection(DataBase.COLLECTION_MAIN);
		System.out.println(DataBase.getAllCities().size());
		System.in.read();
		
		FileReader fr = new FileReader(mainFile);
		BufferedReader br = new BufferedReader(fr);
		String s;
		
		//while((s = br.readLine()) != null){
			s = br.readLine();
			linksFile = s.substring(0, s.indexOf(';'));
			configFile = s.substring(s.indexOf(';')+1);
			ScraperConfiguration config = new ScraperConfiguration(path + configFile);
			Scraper scraper;
			MyListener listener = new MyListener(countLinksInFile(path+linksFile));
			
			//links = countLinksInFile(linksFile);
			
			scraper = new Scraper(config, path);
			scraper.addVariableToContext("inputFile", linksFile);
			scraper.addVariableToContext("filter", "0-50");
			scraper.addRuntimeListener(listener);
			scraper.getHttpClientManager().setHttpProxy("192.168.0.2", 3128);
			
			scraper.execute();
			
			
			//System.out.println("Processing links from: " + linksFile);
			//System.out.println("Using config file: " + configFile + "\n");
			
			System.out.println("\n--- Links from: " + linksFile  + " mine complete! ---\n");
		//}
		
		fr.close();
	}

	

	
	private static int countLinksInFile(String fileName) throws IOException{
		
		int linksCount = 0;
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		while(br.readLine() != null){
			linksCount++;
		}
		fr.close();
		return linksCount;
	}
}
	
	
