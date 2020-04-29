import java.util.Arrays;

public class TicTacToe{
	
	
	public static void main(String[] args) {
		
		GamePlay gamePlay = new GamePlay();
		//gamePlay.R2D2vsPlayer();
		
		
		
		for(int i = 0; i < 500; i++) {
			GamePlay gamePlay2 = new GamePlay();

			gamePlay2.R2D2vsR2D2();
		}
		
//		String[][] game_grid = {{"_","_","X"},{"_","_","_"},{"_","_","_"}};
//		String[][] game_grid2 = {{"_","X",""},{"_","_","_"},{"_","_","_"}};
//
//		int game_grid_hash_code = Arrays.deepHashCode(game_grid);
//		int row1 = Arrays.hashCode(game_grid[0]);
//		int row2 = Arrays.hashCode(game_grid[1]);
//		int row3 = Arrays.hashCode(game_grid[2]);
//
//		String final_hash = String.valueOf(game_grid_hash_code) + 
//				String.valueOf(row1) + String.valueOf(row2) + String.valueOf(row3);;
//				
//		System.out.println(final_hash);
//		
//		game_grid_hash_code = Arrays.deepHashCode(game_grid);
//		row1 = Arrays.hashCode(game_grid2[0]);
//		row2 = Arrays.hashCode(game_grid2[1]);
//		row3 = Arrays.hashCode(game_grid2[2]);
//
//		final_hash = String.valueOf(game_grid_hash_code) + 
//				String.valueOf(row1) + String.valueOf(row2) + String.valueOf(row3);;
//				
//		System.out.println(final_hash);


	
		

		

	}
}