ViterbiDecoding
===============

Implements the Viterbi decoding algorithm to find the most likely state sequence for a given observation sequence

Input: Program takes 2 command line
arguments: (1) a model parameters file, (2) a test file

Model Parameter File model parameters are stored in the file according to
the following format: 
Num of states (let’s say N) 
Initial state probabilities (N values here) 
Transition probabilities (This will contain N*N values in the transition matrix. The values are row-based.) 
Num of output symbols (let’s say M) 
Output alphabet (M values here. They can be discrete numbers or strings for the observations) 
Output distributions (This will contain N*M values, M values for probability mass function for each state, one by one.)

TEST File: Each line corresponds to one sequence (space separated between
observation symbols)
Output: Prints on console following :
the most likely state sequence for each of the observation sequences in the test file
