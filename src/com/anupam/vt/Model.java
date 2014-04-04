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
 *         github::https://github.com/agangotia
 */


/**
 * Class: Model
 * Holds the model object in memory
 * Reads the model text file, and creates the model object 
 * */
public class Model {

	private int numberOfStates;// # of states

	private float[] pie;// Initial state probabilities

	private float[][] a;// Transition probabilities

	private int numberOfOutputSymbols;// # of output symbols
	private char[] outputAlphabets;// Output alphabet (M values here. They can
									// be
	// discrete numbers or strings for the observations)
	private float[][] b;// Output distributions (This will contain N*M values, M
						// values

	// for probability mass function for

	// each state, one by one.)

	/**
	 * Parameterized constructor, accepting file name to read the input values
	 * into the model object
	 * */
	Model(String fileNameTraining) {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileNameTraining));
			// Reading Line 1::numberOfStates
			{
				String line = br.readLine();
				StringTokenizer st = new StringTokenizer(line);

				while (st.hasMoreElements()) {
					numberOfStates = Integer
							.parseInt((String) st.nextElement());
				}

			}

			pie = new float[numberOfStates];
			// Reading Line 2::Initial state probabilities
			{
				String line = br.readLine();
				StringTokenizer st = new StringTokenizer(line);
				int i = 0;
				while (st.hasMoreElements()) {
					pie[i++] = Float.parseFloat((String) st.nextElement());
				}

			}

			a = new float[numberOfStates][numberOfStates];

			// Reading Line 3::Transition probabilities
			{
				String line = br.readLine();
				StringTokenizer st = new StringTokenizer(line);
				int i = 0;
				int j = 0;
				while (st.hasMoreElements()) {
					if (j == numberOfStates) {
						j = 0;
						i++;
					}
					if (i == numberOfStates) {
						break;
					}
					a[i][j++] = Float.parseFloat((String) st.nextElement());
				}

			}

			// Reading Line 4::numberOfOutputSymbols
			{
				String line = br.readLine();
				StringTokenizer st = new StringTokenizer(line);

				while (st.hasMoreElements()) {
					numberOfOutputSymbols = Integer.parseInt((String) st
							.nextElement());
				}

			}
			outputAlphabets = new char[numberOfOutputSymbols];

			// Reading Line 5::Output alphabet (M values here. They can be
			// discrete numbers or strings for the observations)
			{
				String line = br.readLine();
				StringTokenizer st = new StringTokenizer(line);
				int i = 0;
				while (st.hasMoreElements()) {
					outputAlphabets[i++] = ((String) st.nextElement())
							.charAt(0);
				}

			}

			b = new float[numberOfStates][numberOfOutputSymbols];

			// Reading Line 3::Transition probabilities
			{
				String line = br.readLine();
				StringTokenizer st = new StringTokenizer(line);
				int i = 0;
				int j = 0;
				while (st.hasMoreElements()) {
					if (j == numberOfOutputSymbols) {
						j = 0;
						i++;
					}
					if (i == numberOfStates) {
						break;
					}
					b[i][j++] = Float.parseFloat((String) st.nextElement());
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
	 * Function:printModel() 
	 * Prints the model state,
	 * Used in debug mode
	 * */
	public void printModel() {

		if (pie == null) {
			System.out.println("***Initial State Probabilities not set**");
			return;
		}
		if (a == null) {
			System.out.println("***Transition Probabilities not set**");
			return;
		}
		if (outputAlphabets == null) {
			System.out.println("***Output Alphabets not set**");
			return;
		}
		if (b == null) {
			System.out.println("***Observation Probabilities***");
			return;
		}
		System.out.println("***MODEL**");
		System.out.println("***numberOfStates**" + numberOfStates);
		System.out.println("***Initial State Probabilities**");
		for (int i = 0; i < numberOfStates; i++) {
			System.out.println(" " + pie[i]);
		}
		System.out.println("***Transition Probabilities**");
		for (int i = 0; i < numberOfStates; i++) {
			for (int j = 0; j < numberOfStates; j++)
				System.out.print("\t" + a[i][j]);
			System.out.println("");
		}

		System.out
				.println("***numberOfOutputSymbols**" + numberOfOutputSymbols);
		System.out.println("***Output Alphabets**");
		for (int i = 0; i < numberOfOutputSymbols; i++) {
			System.out.println(" " + outputAlphabets[i]);
		}
		System.out.println("***Observation Probabilities**");
		for (int i = 0; i < numberOfStates; i++) {
			for (int j = 0; j < numberOfOutputSymbols; j++)
				System.out.print("\t" + b[i][j]);
			System.out.println("");
		}
	}

	/**
	 * Function:getObservationProbability() returns the observation probability
	 * of the given observation at given state
	 * */
	public float getObservationProbability(int state, char output) {
		float returnProb = 0.0f;
		int indexOutput = 0;
		// first get the index of output in model,
		for (int i = 0; i < numberOfOutputSymbols; i++) {
			if (output == outputAlphabets[i]) {
				indexOutput = i;
				break;
			}

		}
		return b[state][indexOutput];
	}

	/**
	 * Function:getInitialProbability() returns the initial probability of the
	 * given state, i.e. at t=1
	 * */
	public float getInitialProbability(int state) {
		return pie[state];
	}

	/**
	 * Function:getTransitionProbability() returns the Transition probability of
	 * transition from S1 to S2 int stateInitial=Starting point of transition
	 * int stateFinal=final point of transition
	 * */
	public float getTransitionProbability(int stateInitial, int stateFinal) {
		return a[stateInitial][stateFinal];
	}

	/**
	 * Function:getNumberOfStates() returns the number of states in model
	 * */
	public int getNumberOfStates() {
		return numberOfStates;
	}

}
