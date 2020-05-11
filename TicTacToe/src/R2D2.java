import java.io.IOException;
import java.util.Random;

import org.json.simple.parser.ParseException;


public class R2D2 extends Player{
	
	String previousGameHash;
	String currentGameHash;
	
	/**  
	 * Constructor for Bot Class
	 * @param Tic-Tac-Toe board,game rules instance, Symbol of player (X or 0),
	 *  path of where the JSON nodes/states will be saved
	 * @return none
	 */
	R2D2(Grid grid, WinRules wr, String symbol, String path){
		super(grid,symbol,wr,path);
	
	}

	/**  
	 * Increments the turn counter and initializes attributes with previous and current state SHA256 hash. 
	 * @param String path for directory where 
	 * JSON files(tree nodes/game states) will be stored.
	 * @return none
	 */
	@Override
	public void play(String previousGameHash,String currentGameHash) {
		
		turns_taken++;
		this.previousGameHash = previousGameHash;
		this.currentGameHash = currentGameHash;
		
		AnalyzeBoard();
	}
	
	/**  
	 * Generates random position from Tic-Tac-Toe board. Checks previous saved game states and replaces 
	 * randomly selected position if a better decision/choice exists. It then makes the decision, saves the new 
	 * 
	 * @param String path for directory where 
	 * JSON files(tree nodes/game states) will be stored.
	 * @return none
	 */
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
		}
		
//		//Makes symbol selected always choose a random position
//		if(symbol.equals("X") || symbol.equals("O")) {
//			decision = rand_avail_pos;
//		}
		
		buildTree(decision,future_grid);
		
		updateValueonGrid(grid, decision, symbol);
	}
	
	/**  
	 * Checks previous data (JSON files/tree nodes) and return the best position with 
	 * the greatest probability of success if it exists
	 * @param none
	 * @return Tic-Tac-Toe position with greatest probability of success
	 */
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
	
	/**  
	 * Updates a copy of the current Tic-Tac-Toe game with the decision made which results in a "Future" 
	 * Tic-Tac-Toe game board. It then check if the "Future" state JSON file exists by checking the files in
	 * the path provided. The SHA256 Hash code representing the "Future" grid is then added as a child object
	 * to the current state JSON file with default win, tie, and lose probabilities. If the "Future" state Hash Code
	 * is found, as the name of one of the files, then the win, tie and lose probabilities saved in the "Future" JSON file 
	 * are updated on the children of the current state JSON file that point to the "Future" file.
	 * 
	 * @param rand_avail_pos(can be randomly generated or calculated position), 
	 * future_grida(copy of current Tic-Tac-Toe board before getting updated)
	 *
	 * @return none
	 */
	public void buildTree(int rand_avail_pos,Grid future_grid) {
		
		updateValueonGrid(future_grid, rand_avail_pos, symbol);
				
		SavingStates savingStates = new SavingStates(grid, wr, path, rand_avail_pos);
		
		boolean futureStateExists = savingStates.checkIfStateFileExists(future_grid);
		
		try {
			savingStates.addChildrenToCurrentFile(grid,future_grid);
	        
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	
		 if(futureStateExists) {
			
			//update children probabilities  to next node probabilities or else they will  be set to default values
			 
			try {
				savingStates.updateChildrenResults(grid,future_grid);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**  
	 * Converts Tic-Tac-Toe board position integer (1-9) to row and column values. It
	 * then updates the 2D array, representing the board, with the new move and subtracts 1
	 * from the attribute holding the amount of available positions.
	 * 
	 * @param grid(current Tic-Tac-Toe board), rand_avail_pos(calculated or randomly generated available
	 * position), player_symbol (Symbol of current player)
	 * @return none
	 */
	public void updateValueonGrid(Grid grid, int rand_avail_pos, String player_symbol) {
		
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
		
		grid.setSingleGridValue(row, col, player_symbol);
		grid. subtract1FromOpenPositionsAmt();
	}
	
	/**  
	 * Generates a random value based on available Tic-Tac_tie board positions
	 * @param none
	 * @return randomly generated Tic-Tac-Toe position based on available positions
	 */
	public int getRandomAvailablePosition() {
		
		Integer[] availables = grid.getAvailablePositions();
		
		Random rand = new Random();
		int random_index = rand.nextInt(availables.length);//0->(n-1)
				
		return availables[random_index];
	}
}
