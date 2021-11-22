import java.util.Arrays;

public class SudokuSolver {

	static int[][] board = {
			{0,2,0,0,0,0,0,0,0},
			{0,0,0,6,0,0,0,0,3},
			{0,7,4,0,8,0,0,0,0},
			{0,0,0,0,0,3,0,0,2},
			{0,8,0,0,4,0,0,1,0},
			{6,0,0,5,0,0,0,0,0},
			{0,0,0,0,1,0,7,8,0},
			{5,0,0,0,0,9,0,0,0},
			{0,0,0,0,0,0,0,4,0},
	};
	
	public static void main(String[] args) {
		
		boolean[][] notGiven = new boolean[9][9];
		
		//Determines what numbers we can change
		for(int i = 0; i < 9; i++)
			for(int j = 0; j < 9; j++)
				if(board[i][j] == 0)
					notGiven[i][j] = true;
		
		int currentRow = 0, currentColumn = 0;
		boolean solved = false;
		boolean backtracking = false;
		boolean changed = false; //If the nothing worked for the number, we need to set it to zero and move backwards
		
		while(!solved) {
			if(currentColumn == -1) {
				System.out.println("No Solution");
				return;
			}
			changed = false;
			if(!notGiven[currentRow][currentColumn]) //if the number is given, skip it
				if(!backtracking)
					currentColumn++;
				else
					currentColumn--;
			else {
				for(int i = board[currentRow][currentColumn] + 1; i <= 9; i++) {
					if(isValid(i, currentRow, currentColumn)) {
						board[currentRow][currentColumn] = i;
						changed = true;
						break;
					}
				}
				
				if(!changed) { //if no numbers worked, set it to zero and go back
					board[currentRow][currentColumn] = 0;
					backtracking = true;
					currentColumn--;
				}
				else {
					backtracking = false;
					currentColumn++;
				}
			}

			if(currentColumn > 8 && currentRow >= 8)
				solved = true;
			else if(currentColumn > 8) {
				currentRow++;
				currentColumn = 0;
			}
			else if(currentColumn < 0 && currentRow > 0) {
				currentRow--;
				currentColumn = 8;
			}
		}

		for (int[] row : board) {
		    System.out.println(Arrays.toString(row));
		}
	}
	
	public static boolean isValid(int attemptingNumber, int row, int column) {
		//Checking the row for the number
		for(int i = 0; i < 9; i++)
			if(board[i][column] == attemptingNumber && i != row)
				return false;
		
		//checking the column
		for(int i = 0; i < 9; i++)
			if(board[row][i] == attemptingNumber && i != column)
				return false;
		
		//Checking squares of the box the number is in
		int boxRow = row / 3;
		int boxColumn = column / 3;
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++) 
				if(board[boxRow*3 + i][boxColumn*3 + j] == attemptingNumber && boxRow + i != row && boxColumn + j != column)
					return false;
		
		return true;
	}

}
