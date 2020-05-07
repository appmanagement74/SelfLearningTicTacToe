import java.util.Arrays;

public class TicTacToe{
	
	
	public static void main(String[] args) {
		
		GamePlay gamePlay = new GamePlay();
		//gamePlay.R2D2vsPlayer();
		
				
		for(int i = 0; i < 10000; i++) {
			GamePlay gamePlay2 = new GamePlay();

			gamePlay2.R2D2vsR2D2();
		}

	}
}