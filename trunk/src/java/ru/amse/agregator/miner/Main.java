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
		String s;
		
		while((s = br.readLine()) != null){
			//links, configuration
			myScrap = new MyScarper(s.substring(0, s.indexOf(';')) , s.substring(s.indexOf(';')+1) ); 
			myScrap.minerStart();
			
		}
		fr.close();
	}
}
	
	