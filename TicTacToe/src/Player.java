
public abstract class Player {
	
	protected Grid grid;
	protected String symbol;
	protected int turns_taken;
	protected final String opponentSymbol;
	protected WinRules wr;
	String path;

	
	/**  
	 * Constructor for Player Class
	 * @param Tic-Tac-Toe board, Symbol of player (X or 0),
	 *  game rules instance, path of where the JSON nodes/states will be saved
	 * @return none
	 */
	Player(Grid grid, String symbol, WinRules wr,String path){
		
		this.grid = grid;
		this.symbol = symbol;
		this.wr = wr;
		this.path = path;
		opponentSymbol = getOpponentSymbol();
	}
	
	/**  
	 * Abstact method play() that the User and Bot classes have to define
	 * @param none
	 * @return none
	 */
	public abstract void play(String previousGameHash,String currentGameHash);
	
	public String getOpponentSymbol() {
		
		if(symbol.equals("X")) {
			return "O";
		}	
		return "X";
	}

}
