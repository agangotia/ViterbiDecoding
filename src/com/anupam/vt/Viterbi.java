package com.anupam.vt;

import java.util.ArrayList;

/**
 * Class: Viterbi
 * Implements the viterbi algorithm
 * */
public class Viterbi {
	
	private int numberOfStates;// number of states in model
	private int numberOfObservationsInOutput;// # of Observations
	private Model modelRef;//reference model object
	ArrayList<Character> observationSeq;//list of observations
	
	/*
	 * Stores the states dynamic Programming Matrix.
	 */
	float[][] dPMatrix;
	
	/*
	 * Stores the states which gave the max value, to reach the current state.
	 * Used in Backtracking
	 */
	int[][] BackTrackMatrix;
	
	/**
	 * If isDebug=true, program will print support SOP's elso not.
	 */
	public static boolean isDebug;
	
	
	/**
	 * Viterbi Initialization constructor
	 * */
	Viterbi(Model modelRef,ArrayList<Character> observationSeq){
		this.modelRef=modelRef;
		this.observationSeq=observationSeq;
		this.numberOfObservationsInOutput=observationSeq.size();
		this.numberOfStates=modelRef.getNumberOfStates();
		dPMatrix=new float[numberOfStates][numberOfObservationsInOutput];
		BackTrackMatrix=new int[numberOfStates][numberOfObservationsInOutput];
	}
	
	/**
	 * For the provided Observations,
	 * Fills the dPMatrix[][] and BackTrackMatrix[][] as stated in Viterbi Algorithm
	 * */
	public void runViterbi(){
		for(int coloumn=0;coloumn<numberOfObservationsInOutput;coloumn++){
			
			//get the Observation 
			char observation=observationSeq.get(coloumn);
			
			//First fill in the First coloumn
			if(coloumn==0){
				for(int row=0;row<numberOfStates;row++){
					float b=modelRef.getObservationProbability(row, observation);//Observation probability of Observation in following state
					float initProb=modelRef.getInitialProbability(row);//Initial probability for this state
					dPMatrix[row][coloumn]=b*initProb;
					BackTrackMatrix[row][coloumn]=0;
				}	
			}else{
				//Fill in the Last coloumn's
				for(int row=0;row<numberOfStates;row++){
					float b=modelRef.getObservationProbability(row, observation);//Observation probability of Observation in following state
					dPMatrix[row][coloumn]=0.0f;
					//initProb now will come from previous coloumn of DPMatrix
					for(int i=0;i<numberOfStates;i++){
						float initProb=dPMatrix[i][coloumn-1]; //Use of Dynamic Programming , Initial probability for this state is already stored and calculated
						float transitionProb=modelRef.getTransitionProbability(i, row);//Transition from i ->current State
						float Value=initProb*transitionProb*b;
						if(i==0){
							dPMatrix[row][coloumn]=Value;
							BackTrackMatrix[row][coloumn]=i;
						}else if(Value>dPMatrix[row][coloumn]){
							dPMatrix[row][coloumn]=Value;
							BackTrackMatrix[row][coloumn]=i;
						}
					}

				}	
				
			}
			
			
		}
		if(isDebug){
			printDPMatrix();
			printBackTrackMatrix();
		}
		
	}
	
	/**
	 * Function:printDPMatrix() 
	 * Prints the dPMatrix,
	 * Used in debug mode
	 * */
	public void printDPMatrix(){
		System.out.println("***Observation Probabilities**");
		for(int i=0;i<numberOfStates;i++){
			for(int j=0;j<numberOfObservationsInOutput;j++)
			System.out.print("\t"+dPMatrix[i][j]);
			System.out.println("");
		}
	}

	/**
	 * Function:printBackTrackMatrix() 
	 * Prints the BackTrackMatrix,
	 * Used in debug mode
	 * */
	public void printBackTrackMatrix(){
		System.out.println("***BackTrack Result**");
		for(int i=0;i<numberOfStates;i++){
			for(int j=0;j<numberOfObservationsInOutput;j++)
			System.out.print("\t"+BackTrackMatrix[i][j]);
			System.out.println("");
		}
	}
	
	/**
	 * Function:printState() 
	 * Implements Backtracking to print the most likely state sequence
	 * */
	public void printState(){
		ArrayList<Integer> states=new ArrayList<Integer>();
		int stateToMove=-1;
		int coloumnIndex=numberOfObservationsInOutput-1;
		for(int i=0;i<numberOfObservationsInOutput;i++){
			if(i==0){
				int state=getStateInLastObservation();
				states.add(state+1);
				stateToMove=BackTrackMatrix[state][coloumnIndex];	
				coloumnIndex--;
				
			}else{
				states.add(stateToMove+1);
				stateToMove=BackTrackMatrix[stateToMove][coloumnIndex];
				coloumnIndex--;
			}
		}
		
		//lets print 

		for(int i=0;i<states.size();i++){
			System.out.print(" "+states.get((states.size()-1)-i));
		}
		System.out.println();
	}
	
	/**
	 * Function:getStateInLastObservation()
	 * returns the state of last observation, i.e. the state having the max probability
	 * */
	int getStateInLastObservation(){
		//first find the max in last coloumn
				int maxIndex=0;
				float maxVal=dPMatrix[0][numberOfObservationsInOutput-1];
				for(int row=1;row<numberOfStates;row++){
					if(dPMatrix[row][numberOfObservationsInOutput-1]>maxVal){
						maxIndex=row;
						maxVal=dPMatrix[row][numberOfObservationsInOutput-1];
					}
				}
	return maxIndex;
	}
}
