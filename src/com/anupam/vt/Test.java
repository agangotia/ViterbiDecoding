package com.anupam.vt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author Anupam Gangotia
 * Profile::http://en.gravatar.com/gangotia
 * github::https://github.com/agangotia
 */

/**
 * Class: Test
 * Holds the Test observations into memory
 * */
public class Test {
	
	private ArrayList<ArrayList<Character>> observationSeq;//observation sequences from a test file
	
	
	/**
	 * Parameterized constructor, accepting file name to read the input values from
	 * */
	Test(String fileNameTest){
		
		observationSeq=new ArrayList<ArrayList<Character>>();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileNameTest));

			{// lets read coloumns
				String line = br.readLine();
				while (line != null) {
					ArrayList<Character> seq =new ArrayList<Character>();
					StringTokenizer st = new StringTokenizer(line);
					while (st.hasMoreElements()) {
						seq.add(((String) st.nextElement())
								.charAt(0));

					}
					observationSeq.add(seq);
					line = br.readLine();
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Function:printTestObservations() 
	 * Prints the Observations,
	 * Used in debug mode
	 * */
	void printTestObservations(){
		for(ArrayList<Character> seq:observationSeq){
			for(char temp:seq){
				System.out.print(" "+temp);	
			}
			System.out.println();
		}
	}
	
	/**
	 * Function:getObservationSeq() 
	 * returns the observationSeq object
	 * */
	public ArrayList<ArrayList<Character>> getObservationSeq() {
		return observationSeq;
	}

}
