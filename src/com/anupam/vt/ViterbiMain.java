package com.anupam.vt;

import java.util.ArrayList;

/**
 * @author Anupam Gangotia
 * Profile::http://en.gravatar.com/gangotia
 * github::https://github.com/agangotia
 */

/**
 * Assumption:
 * The states Names are not provided in model text file,
 * so this program assume them to start from integer value 1, till the value specified as "# of states"
 * e.g. if # of states=3
 * then states are {1,2,3}
 * */

/**
 * HMM and Viterbi decoding Implements the Viterbi decoding algorithm to find
 * the most likely state sequence for a given observation sequence
 * */
public class ViterbiMain {
	/**
	 * If isDebug=true, program will print support SOP's elso not.
	 */
	public static boolean isDebug = false;

	/**
	 * Main Function: Execution Starts here. Input: Program takes 2 command line
	 * arguments: (1) a model parameters file, (2) a test file
	 * 
	 * Model Parameter File model parameters are stored in the file according to
	 * the following format: 
	 * # of states (let’s say N) 
	 * Initial state probabilities (N values here) 
	 * Transition probabilities (This will contain N*N values in the transition matrix. The values are row-based.) 
	 * # of output symbols (let’s say M) 
	 * Output alphabet (M values here. They can be discrete numbers or strings for the observations) 
	 * Output distributions (This will contain N*M values, M values for probability mass function for each state, one by one.)
	 * 
	 * TEST File: Each line corresponds to one sequence (space separated between
	 * observation symbols)
	 * 
	 * Output: Prints on console following :
	 * the most likely state sequence for each of the observation sequences in the test file
	 * */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		if (args.length != 2) {// if the user has not provided the 2
			// arguments, then exit and display message
			System.out.println(args.length);
			System.out.println("Please provide 2 arguments");
			System.out.println("Correct Syntax is");
			System.out
					.println("java -cp ./src com.anupam.vt.ViterbiMain data//model.txt data//test.dat");
			return;
		}

		/*
		 * Lets read the values of command line parameters.
		 */

		String fileNameModel = args[0];// Training File Name
		String fileNameTest = args[1];// Test File Name

		Model objModel = new Model(fileNameModel);
		if (isDebug)
			objModel.printModel();

		Test objTest = new Test(fileNameTest);
		if (isDebug)
			objTest.printTestObservations();

		ArrayList<ArrayList<Character>> observationSeqList = objTest
				.getObservationSeq();
		int i = 1;
		/*
		 * Now for each of the Observations, Lets find the most likely state sequence according to VITERBI
		 */
		for (ArrayList<Character> observationSeq : observationSeqList) {
			
			/*
			 * Viterbi Object Initialization
			 * acc to model read, and the target observation sequence
			 */
			Viterbi objViterbi = new Viterbi(objModel, observationSeq);
			Viterbi.isDebug = isDebug;
			
			/*
			 * Let's do the actual work
			 */
			objViterbi.runViterbi();
			
			System.out.println("States Sequence for Observation :" + (i++));
			/*
			 * Let's use BackTrack to print the most likely states
			 */
			objViterbi.printState();
		}
	}

}
