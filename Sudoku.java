package A14;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;


public class Sudoku{
	 private static final int BOARD_SIZE = 9;
	    private static final int SUBSECTION_SIZE = 3;
	    private static final int BOARD_START_INDEX = 0;

	    private static final int NO_VALUE = 0;
	    private static final int MIN_VALUE = 1;
	    private static final int MAX_VALUE = 9;

	    private static int[][] board = {
	      {12, 0, 0, 0, 0, 0, 0, 0, 0},
	      {0, 0, 3, 6, 0, 0, 0, 0, 0},
	      {0, 7, 0, 0, 9, 0, 2, 0, 0},
	      {0, 5, 0, 0, 0, 7, 0, 0, 0},
	      {0, 0, 0, 0, 4, 5, 7, 0, 0},
	      {0, 0, 0, 1, 0, 0, 0, 3, 0},
	      {0, 0, 1, 0, 0, 0, 0, 6, 8},
	      {0, 0, 8, 5, 0, 0, 0, 1, 0},
	      {0, 9, 0, 0, 0, 0, 4, 0, 0}
	    };

	    public static void main(String[] args) {
	    	
//	    	//first use
//	    	
//	    	//original main but checking if the board is valid and solvable and if not printing
//	    	//the correct message
//	    	Sudoku solver = new Sudoku();
//	    	if (solver.isSolvableBoard(board)) {
//		        solver.solve(board);
//		        
//		        //we can use the original printBOard or the formated one we created
//		        
//		        //solver.printBoard();
//		       solver.myprintBoard(board);
//		       
//		       //if the board cannot be solved find out if its because it is invalid or unsolvable
//		       //and print accordingly
//	    	}else { String s = "The given board is "; s += solver.isValidBoard(board) ? 
//	    			"unsolvable." : "invalid."; System.out.println(s);}
//	    	
//	    	//second use
	    	
	    	//the generate sudokus functions takes first how many sudokus to print
	    	//and secondly the amount of empty cells each sudoku must have
	    	//it generates them, checks them and prints them
	    	
	    	Scanner scan = new Scanner(System.in);
	    	Sudoku solver = new Sudoku();
	    	int N = 0, X = 0;
	    	
	    	System.out.println("How may board would you like to be gerenated?");
	    	System.out.print(">");
	    	N = scan.nextInt();
	    	System.out.println("How may emty cells would you like each board to have?");
	    	System.out.print(">");
	    	X = scan.nextInt();
	    	scan.close();
	    	
	    	solver.generateSudokus(N , X);
	    }

	
	    
	    
	    private void printBoard() {
	        for (int row = BOARD_START_INDEX; row < BOARD_SIZE; row++) {
	            for (int column = BOARD_START_INDEX; column < BOARD_SIZE; column++) {
	                System.out.print(board[row][column] + " ");
	            }
	            System.out.println();
	        }
	    }

	    private boolean solve(int[][] board) {
	        for (int row = BOARD_START_INDEX; row < BOARD_SIZE; row++) {
	            for (int column = BOARD_START_INDEX; column < BOARD_SIZE; column++) {
	                if (board[row][column] == NO_VALUE) {
	                    for (int k = MIN_VALUE; k <= MAX_VALUE; k++) {
	                        board[row][column] = k;
	                        if (isValid(board, row, column) && solve(board)) {
	                            return true;
	                        }
	                        board[row][column] = NO_VALUE;
	                    }
	                    return false;
	                }
	            }
	        }
	        return true;
	    }

	    private boolean isValid(int[][] board, int row, int column) {
	        return rowConstraint(board, row) &&
	          columnConstraint(board, column) &&
	          subsectionConstraint(board, row, column);
	    }

	    private boolean subsectionConstraint(int[][] board, int row, int column) {
	        boolean[] constraint = new boolean[BOARD_SIZE];
	        int subsectionRowStart = (row / SUBSECTION_SIZE) * SUBSECTION_SIZE;
	        int subsectionRowEnd = subsectionRowStart + SUBSECTION_SIZE;

	        int subsectionColumnStart = (column / SUBSECTION_SIZE) * SUBSECTION_SIZE;
	        int subsectionColumnEnd = subsectionColumnStart + SUBSECTION_SIZE;

	        for (int r = subsectionRowStart; r < subsectionRowEnd; r++) {
	            for (int c = subsectionColumnStart; c < subsectionColumnEnd; c++) {
	                if (!checkConstraint(board, r, constraint, c)) return false;
	            }
	        }
	        return true;
	    }

	    private boolean columnConstraint(int[][] board, int column) {
	        boolean[] constraint = new boolean[BOARD_SIZE];
	        return IntStream.range(BOARD_START_INDEX, BOARD_SIZE)
	          .allMatch(row -> checkConstraint(board, row, constraint, column));
	    }

	    private boolean rowConstraint(int[][] board, int row) {
	        boolean[] constraint = new boolean[BOARD_SIZE];
	        return IntStream.range(BOARD_START_INDEX, BOARD_SIZE)
	          .allMatch(column -> checkConstraint(board, row, constraint, column));
	    }

	    private boolean checkConstraint(int[][] board, int row, boolean[] constraint, int column) {
	        if (board[row][column] != NO_VALUE) {
	            if (!constraint[board[row][column] - 1]) {
	                constraint[board[row][column] - 1] = true;
	            } else {
	                return false;
	            }
	        }
	        return true;
	    }
	    
	    //checks if a hole board is valid by checking if every cell follows the sudoku rules
	    //utilizing the is Valid method
	    boolean isValidBoard(int[ ][ ] b){
	    	for( int row = BOARD_START_INDEX; row < BOARD_SIZE; row++ ) {
	    		for (int column = BOARD_START_INDEX; column < BOARD_SIZE; column++){
	    			//if the cell is no value its always valid
	    			if ( b[row][column] == NO_VALUE) continue;
	    			if ( !( (b[row][column] >= 1) && b[row][column] <=9 ) || !( isValid(b, row, column) ) )return false;
	    		}
	    	}
	    	return true;
	    }
	    
	    //is solvable if its valid and thte solve function doesnt return false
	    boolean isSolvableBoard(int[][]board) {
	    	return isValidBoard(board) && solve(board);
	    }
	    
	     void randomBord(int [][] board, int emptycell){
	    	//array cells default 0 so if 0 is the no_value value we have to initialize it with 
	    	 //a different value so we can differentiate them 
	    	//from the  "empty cells"
    		if (NO_VALUE == 0){
    			for( int row = 0; row < BOARD_SIZE; row++ ) {
    	    		for (int column = 0; column < BOARD_SIZE; column++){
    	    			board[row][column] = -1;
    	    		}
    			}
    		}
    		
	    	Random randomGenerator = new Random();
	    	
	    	//for num of empty cells we need fill a random possession on the board with no value
	    	while(emptycell > 0){
	    		int randomRow = randomGenerator.nextInt( (BOARD_SIZE - 1) - 0 + 1) + 0; 
	    		int randomColumn = randomGenerator.nextInt( (BOARD_SIZE - 1) - 0 + 1) + 0; //minV = 0 //maxV = board_size - 1
	    		
	    		//if it has already been randomly selected to be an empty cell continue
	    		if (board[randomRow][randomColumn] == NO_VALUE) continue;
	    		
	    		board[randomRow][randomColumn] = NO_VALUE;
	    		emptycell--;
	    	}
	    	
	    	//file the none chosen cells with random numbers within the max min limits
	    	for( int row = 0; row < BOARD_SIZE; row++ ) {
	    		for (int column = 0; column < BOARD_SIZE; column++){
	    			if(board[row][column] == NO_VALUE) continue;
	    			board[row][column] = randomGenerator.nextInt(MAX_VALUE - MIN_VALUE + 1) + MIN_VALUE;
	    		}
			}
	    }
	    
	     //takes a board and
	     //prints it in the sudoku format
	     void myprintBoard(int[][] b) {
	    	for (int row = 0; row < BOARD_SIZE; row++) {
	            for (int column = 0; column < BOARD_SIZE; column++) {
	                System.out.print(b[row][column] + " ");
	                if(((column + 1) % 3) == 0 && ((column + 1) != 9))System.out.print("| ");
	            }
	            System.out.println();
	            if(((row + 1) % 3) == 0)System.out.println();
	        }
	    }
	    
	   //takes two int 2d arrays and deep copies the second to the first one
	   void d2DeepCopy(int[][] c, int[][] or) {
	    	for (int row = 0; row < BOARD_SIZE; row++) {
	            for (int column = 0; column < BOARD_SIZE; column++) {
	            	c[row][column] = or[row][column];
	            }
	        }
	    	return;
	    }
	    
	   //first arg a the amount of arrays to generate and secondly the number of no values 
	   //in each board
	    void generateSudokus(int N, int X){
	    	//start timer
	    	long StartTime = System.currentTimeMillis();
	    	
	    	int [][] temp = new int[BOARD_SIZE][BOARD_SIZE];
	    	
	    	int ValidBoardsCreated = 0;
	    	int InvalidBoardsCreated = 0;
	    	int UnsolvableBoardsCreated = 0;
	    	int boarsPrinted = 1;
	    	
	    	Sudoku solver = new Sudoku();
	    	
	    	
	    	
	    	//runs for every board we need, n times ( will subtract every time we generate one )
	    	while (N != 0){
	    		while( true ) {
	    			//generate a random board with the correct amount of no values
	    			solver.randomBord(board, X);
	    			// check if its random 
	    			//if not valid not n-- and continue
	    			if( !(solver.isValidBoard(board) ) ) {InvalidBoardsCreated++; continue; }
	    			ValidBoardsCreated++;
	    			//if we made a valid board then save it before we solve it so if its 
	    			//solvable we have the unsolved one
	    			solver.d2DeepCopy(temp, board);
	    			//if solvable as well we found one valid and solvable and print it 
	    			//else try again
	    			if( !(solver.solve(board) ) ) {UnsolvableBoardsCreated++; continue; }
	    			break;
	    		}
	    		//print the unsolved and the the solved board
	    		System.out.println("Board #" + boarsPrinted );
	    		solver.myprintBoard(temp);
	    		System.out.println("Solution of board#" + boarsPrinted++);
	    		solver.myprintBoard(board);
	    		
	    		//printed one so reduce n
	    		N--;
	    	}
	    	
	    	//print the details of the poses 
	    	System.out.println("Empty cells per board       :" + X);
	    	System.out.println("Valid boards created        :" + ValidBoardsCreated);
	    	System.out.println("Invalid boards created      :" + InvalidBoardsCreated);
	    	System.out.println("Unsolvable boards created   :" + UnsolvableBoardsCreated);
	    	//stop timer and convert to secs and mins if it took at list a hole min;
	    	long EndTime = System.currentTimeMillis();
	    	float sec = (EndTime - StartTime) / 1000F;
	    	System.out.println("Elapsed time in seconds     :" + sec
	    			+ (sec > 60 ? " (or " + sec/60 + " mins)" : "") );
	    }
}
