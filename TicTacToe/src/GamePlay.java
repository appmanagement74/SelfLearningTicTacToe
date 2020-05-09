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

	GamePlay(){
		
		grid = new Grid();
		sc = new Scanner(System.in);
		wr = new WinRules(grid);
		path = "/Volumes/TICTACTOE/TicTacToeStates/";
		
	}
	
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
	
	
	
	public void saveAndPrintGrid() {
		
		previousGameHash = currentGameHash;
		currentGameHash = grid.getGridHashCode();
		
		saveStateExternally();
		grid.printGrid();

	}
	
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
