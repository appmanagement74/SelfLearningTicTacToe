
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.parser.ParseException;
public class User extends Player{
	
	private Scanner sc;


	User(Grid grid, String symbol, Scanner sc, WinRules wr,String path){
		super(grid,symbol,wr,path);
		this.sc = sc;
	}
	
	@Override
	public void play(String previousGameHash, String currentGameHash){
			getValidInput();
			turns_taken++;
	}
	
	public void checkIfUserInputTaken(int row, int col) {
		
		if(grid.getGrid()[row][col].equals(grid.getDefaultValue())) {
			
			Grid future_grid = grid.getGridCopy();
			future_grid.setSingleGridValue(row,col,symbol);
			future_grid.subtract1FromOpenPositionsAmt();
			
			SavingStates savingStates = new SavingStates(grid, wr, path);
			
			try {
				savingStates.addChildrenToCurrentFile(grid,future_grid);
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			grid.setSingleGridValue(row,col,symbol);
			grid.subtract1FromOpenPositionsAmt();
			

		}
		else {
			System.out.println("Position Taken. Try again!");
			getValidInput();
		}

	}
	
	public void getValidInput() {
		
		
		int col = getValidColumnInput();
		int row = getValidRowInput();
		
		
		checkIfUserInputTaken(row, col);
	}
	
	public int getValidColumnInput() {
		
		System.out.print("Enter Column: (A,B,C):");
		
		//Check validity of first choice
		boolean choice1isValid = false;
		char choice1 = ' ';
		while(!choice1isValid) {
			choice1 = sc.next().charAt(0);
			int asciiChoice1 = (int) choice1;
			boolean isabc = asciiChoice1 >= 97 && asciiChoice1 <= 99;
			boolean isABC = asciiChoice1 >= 65 && asciiChoice1 <= 67;
			
			if(isabc || isABC){
				choice1isValid = true;
			}
			else {
				System.out.print("Enter valid column (A,B,C): ");
			}
		}
		
		int index = 0;
		
		switch(Character.toLowerCase(choice1)) {
			
			case 'a':
				index = 0;
				break;
			case 'b':
				index = 1;
				break;
			case 'c':
				index = 2;
				break;
			default:
				System.out.println("Error in Column input");
		}
		return index;
		
	}
	
	public int getValidRowInput() {
		
		int choice2 = 0;

		try {
			System.out.print("Enter Row(1,2,3): ");
			
			//Check validity of second choice	
			boolean choice2isValid = false;
			
			while(!choice2isValid) {
				
				choice2 = sc.nextInt();
				int asciiChoice2 = (int) choice2;
				boolean is123 = asciiChoice2 >= 1 && asciiChoice2 <= 3;
				
				if(is123){
					choice2isValid = true;
				}
				else {
					System.out.print("Enter valid row (1,2,3): ");
				}
			}
		}
	
		catch(Exception e) {
			System.out.println("Input Error");
			sc.next();
			return getValidRowInput();
		}
		
		int index = choice2 -1;
		
		return index;
	}
	
	
}
	

