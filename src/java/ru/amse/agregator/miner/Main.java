package ru.amse.agregator.miner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ru.amse.agregator.storage.DataBase;
import ru.amse.agregator.miner.MyScarper;

public class Main {
	
	public static void main(String[] args) throws IOException {
		
		String mainFile;
		MyScarper myScrap;
		
		if(args.length == 1){
			mainFile = args[0].toString();
		}
		else{
			System.out.println("Please specify an input file.");
			return;
		}
			
		DataBase.connectToDirtyBase();
		System.out.println(DataBase.getAllCities().size());
		//DataBase.removeCollection(DataBase.COLLECTION_MAIN);
		//DataBase.printAll();
		//System.in.read();
		
		FileReader fr = new FileReader(mainFile);
		BufferedReader br = new BufferedReader(fr);
		String configFile;
		
		while((configFile = br.readLine()) != null){
			myScrap = new MyScarper(mainFile.substring(0 , mainFile.lastIndexOf('/')+1),configFile);
			myScrap.minerStart();
		}
		fr.close();
	}
}
	
	