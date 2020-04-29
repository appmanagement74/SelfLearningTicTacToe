import java.util.Arrays;

public class TicTacToe{
	
	
	public static void main(String[] args) {
		
		GamePlay gamePlay = new GamePlay();
		//gamePlay.R2D2vsPlayer();
		
		
		
		for(int i = 0; i < 3; i++) {
			GamePlay gamePlay2 = new GamePlay();

			gamePlay2.R2D2vsR2D2();
		}
		
		String[][] game_grid = {{"_","_","X"},{"_","_","_"},{"_","_","_"}};
		String[][] game_grid2 = {{"_","X",""},{"_","_","_"},{"_","_","_"}};

		int hash_code1 = Arrays.deepHashCode(game_grid);
		int hash_code2 = Arrays.deepHashCode(game_grid);

		
		System.out.println(hash_code1);
		System.out.println(hash_code2);


	
		

		

	}
}