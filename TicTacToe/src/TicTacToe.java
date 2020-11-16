import java.util.Arrays;

public class TicTacToe{
	
	private static int x_wins_amt = 0;
	private static int o_wins_amt = 0;
	private static int ties_amt = 0;
	private static WinRules wr = new WinRules();
	private static String path = "/Users/salram/Desktop/SelfLearningTicTacToe/gameTree";
	
	
	/**  
	 * main method used for running the different combinations or User and Bot games
	 * @param terminal input arguments not used in eclipse
	 * @return none
	 */
	public static void main(String[] args) {
		
		GamePlay gamePlay = new GamePlay(path);
		//gamePlay.R2D2vsPlayer();
		
		for(int i = 0; i < 3; i++) {
			gamePlay.PlayervsR2D2();
		}
		
				
//		for(int i = 0; i < 1000000; i++) {
//			GamePlay gamePlay2 = new GamePlay(path);
//
//			String result = gamePlay2.R2D2vsR2D2();
//			
//			
//			if(result.equals("X" + wr.getWinMessage())) {
//				x_wins_amt++;
//			}
//			else if(result.equals("O" + wr.getWinMessage())) {
//				o_wins_amt++;
//			}
//			else {
//				ties_amt++;
//			}	
//		}
//		System.out.println("Amount of X WINS: " + x_wins_amt);
//		System.out.println("Amount of O WINS: " + o_wins_amt);
//		System.out.println("Amount of TIES: " + ties_amt);
		
		

	}
}