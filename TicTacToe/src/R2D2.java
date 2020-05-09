import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class R2D2 extends Player{
	
	String previousGameHash;
	String currentGameHash;
	
	R2D2(Grid grid, WinRules wr, String symbol, String path){
		super(grid,symbol,wr,path);
	
	}

	public void play(String previousGameHash,String currentGameHash) {
		
		turns_taken++;
		this.previousGameHash = previousGameHash;
		this.currentGameHash = currentGameHash;
		
		AnalyzeBoard();
	}
	
	
	public void AnalyzeBoard() {
		
		
		//1.check one State(hashCode) ahead 
		//2. Check if the future State file already exist
		//2a. If future State doesn't exist, add future State Hash to Current State file under possibilities array
		//     and move current State to next State
		//2b. If future State exist, check whether it'll lead to a win
		//3. If future State, doesn't lead to win, check another future State
		//4. Move to future state
		
		int rand_avail_pos = getRandomAvailablePosition();

		Grid future_grid = grid.getGridCopy();

		
		int decision = calculateBestPosition();
		
		if(decision <= 0) {
			decision = rand_avail_pos;
			//System.out.println("***************************************Random**");
		}
		else {
			//System.out.println("***************************************Calculated**");
		}
		
//		if(symbol.equals("X")) {
//			decision = rand_avail_pos;
//		}
		
		//System.out.println("DESICION?????????????????????   " + decision);
		
		
		buildTree(decision,future_grid);
		
		updateValueonGrid(grid, decision);

		//set future status to current children
	
	}
	
	public int calculateBestPosition() {
		
		SavingStates ss = new SavingStates(grid, wr, path, -1);

		int best = 0;
		try {
			best = ss.getBestChild(symbol);
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return best;
	}
	
	public void buildTree(int rand_avail_pos,Grid future_grid) {
		
		updateValueonGrid(future_grid, rand_avail_pos);
		
		SavingStates savingStates = new SavingStates(grid, wr, path, rand_avail_pos);
		
		boolean futureStateExists = savingStates.checkIfStateFileExists(future_grid);
		
		try {
			savingStates.addChildrenToCurrentFile(grid,future_grid);
			//savingStates.setStatusOfChildToFutureResult(grid,future_grid);
	        
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		 if(futureStateExists) {
			
			//update children results  to next node result field or else it be set to undetermined 
			 
			try {
				savingStates.updateChildrenResults(grid,future_grid);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//use previous data to either allow the random value update or generate another random value.
			//2b. If future State exist, check whether it'll lead to a win
			//3. If future State, doesn't lead to win, check another future State
			//4. Move to future state
			
			//if result is undetermined
				//check children and if if any children are L set result to L, then T and then W. L>T>W
			//If results is L, choose a different position
			
		}
	}
	
	public void updateValueonGrid(Grid grid, int rand_avail_pos) {
		
		int row = -1;
		int col = -1;
		
		switch(rand_avail_pos) {
			
			case 1:
				row = 0;
				col = 0;
				break;
			case 2:
				row = 0;
				col = 1;
				break;
			case 3:
				row = 0;
				col = 2;
				break;
			case 4:
				row = 1;
				col = 0;
				break;
			case 5:
				row = 1;
				col = 1;
				break;
			case 6:
				row = 1;
				col = 2;
				break;
			case 7:
				row = 2;
				col = 0;
				break;
			case 8:
				row = 2;
				col = 1;
				break;
			case 9:
				row = 2;
				col = 2;
				break;
			default:
				System.out.println("Error converting position available to row and column");
		}
		
		grid.setSingleGridValue(row, col, symbol);
		grid. subtract1FromOpenPositionsAmt();
	}
	
	public int getRandomAvailablePosition() {
		
		Integer[] availables = grid.getAvailablePositions();
		
		Random rand = new Random();
		int random_index = rand.nextInt(availables.length);//0->(n-1)
		
		//System.out.println("random:" + availables[random_index]);
		
		return availables[random_index];
	}
}
