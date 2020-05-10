import java.util.Arrays;

public class TicTacToe{
	
	private static int x_wins_amt = 0;
	private static int o_wins_amt = 0;
	private static int ties_amt = 0;
	private static WinRules wr = new WinRules();
	
	public static void main(String[] args) {
		
		GamePlay gamePlay = new GamePlay();
		//gamePlay.R2D2vsPlayer();
		
				
		for(int i = 0; i < 10000; i++) {
			GamePlay gamePlay2 = new GamePlay();

			String result = gamePlay2.R2D2vsR2D2();
			
			
			if(result.equals("X" + wr.getWinMessage())) {
				x_wins_amt++;
			}
			else if(result.equals("O" + wr.getWinMessage())) {
				o_wins_amt++;
			}
			else {
				ties_amt++;
			}
			
			System.out.println("Amount of X WINS: " + x_wins_amt);
			System.out.println("Amount of O WINS: " + o_wins_amt);
			System.out.println("Amount of TIES: " + ties_amt);
		}
		
		

	}
}