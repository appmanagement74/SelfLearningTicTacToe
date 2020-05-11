import java.io.IOException;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

public class GamePlay {
		
	private Grid grid;
	private Scanner sc;
	private WinRules wr;
	private String previousGameHash;
	private String currentGameHash;
	private String path;
	private String x = "X";
	private String o = "O";

	
	/**  
	 * Constructor for GamePlay Class
	 * @param String path for directory where 
	 * JSON files(tree nodes/game states) will be stored.
	 * @return none
	 */
	GamePlay(String path){
		
		grid = new Grid();
		sc = new Scanner(System.in);
		wr = new WinRules(grid);
		this.path = path;
		
	}
	
	/**  
	 * Bot VS Player method used to Start the Tic-Tact-Toe game with the Bot(X) 
	 * followed by the User(O)
	 * @param none
	 * @return the winner of the game in a String
	 */
	public String R2D2vsPlayer() {
		

			Player r2d2 =  new R2D2(grid,wr, x,path);
			Player user_2 = new User(grid,o,sc,wr,path);

			
			String check_grid = "";
			
			saveAndPrintGrid();

			while(grid.getOpenPositionsAmt() > 0) {
				
				
				((R2D2) r2d2).play(previousGameHash,currentGameHash);
				saveAndPrintGrid();
				
				check_grid = wr.checkGrid();
				if(!check_grid.equals("")) {
					System.out.println(check_grid);
				}
				if(wr.getGameHasEnded()) {
					return check_grid;
				}
				
				((User) user_2).play(previousGameHash,currentGameHash);
				saveAndPrintGrid();
				
				check_grid = wr.checkGrid();
				if(!check_grid.equals("")) {
					System.out.println(check_grid);
				}
				if(wr.getGameHasEnded()) {
					return check_grid;
				}
				
	
			}
			sc.close();
			return check_grid;
	}
	
	/**  
	 * Player VS Bot method used to Start the Tic-Tact-Toe game with the Player(X) 
	 * followed by the Bot(O)
	 * @param none
	 * @return the winner of the game in a String
	 */
	public String PlayervsR2D2() {
		

		Player r2d2 =  new R2D2(grid,wr, o,path);
		Player user_2 = new User(grid,x,sc,wr,path);

		
		String check_grid = "";
		
		saveAndPrintGrid();

		while(grid.getOpenPositionsAmt() > 0) {
			
			((User) user_2).play(previousGameHash,currentGameHash);
			saveAndPrintGrid();
			
			check_grid = wr.checkGrid();
			if(!check_grid.equals("")) {
				System.out.println(check_grid);
			}
			if(wr.getGameHasEnded()) {
				return check_grid;
			}
			
			((R2D2) r2d2).play(previousGameHash,currentGameHash);
			saveAndPrintGrid();
			
			check_grid = wr.checkGrid();
			if(!check_grid.equals("")) {
				System.out.println(check_grid);
			}
			if(wr.getGameHasEnded()) {
				return check_grid;
			}
			
		}
		sc.close();
		return check_grid;
}
	
	/**  
	 * Bot VS Bot method used to Start the Tic-Tact-Toe game with the Bot(X) 
	 * followed by the Bot(O)
	 * @param none
	 * @return the winner of the game in a String
	 */
	public String R2D2vsR2D2() {
		

		Player r2d2 =  new R2D2(grid,wr, x,path);
		Player r2d2_2 =  new R2D2(grid,wr, o,path);

		
		String check_grid = "";
		
		saveAndPrintGrid();

		while(grid.getOpenPositionsAmt() > 0) {
			
			
			((R2D2) r2d2).play(previousGameHash,currentGameHash);
			saveAndPrintGrid();
			
			check_grid = wr.checkGrid();
			if(!check_grid.equals("")) {
				System.out.println(check_grid);
			}
			if(wr.getGameHasEnded()) {
				return check_grid;
			}
			
			((R2D2) r2d2_2).play(previousGameHash,currentGameHash);
			saveAndPrintGrid();
			
			check_grid = wr.checkGrid();
			if(!check_grid.equals("")) {
				System.out.println(check_grid);
			}
			if(wr.getGameHasEnded()) {
				return check_grid;
			}
		}
		sc.close();
		return check_grid;
}
	
	/**  
	 * Saves the current State of the Tic-Tac-Toe Board externally in a JSON file, using helper method, and
	 * prints the Grid on the Console
	 * @param none
	 * @return none
	 */
	public void saveAndPrintGrid() {
		
		previousGameHash = currentGameHash;
		currentGameHash = grid.getGridHashCode();
		
		saveStateExternally();
		grid.printGrid();

	}
	
	/**  
	 * Helper method used to store current state of Tic-Tac_Toe board externally in a JSON File.
	 * @param none
	 * @return none
	 */
	public void saveStateExternally() {
		
		SavingStates ss = new SavingStates(grid,wr, path);
		
		try {
			ss.createCurrentGridStateJSON();
		
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
