
public abstract class Player {
	
	protected Grid grid;
	protected String symbol;
	protected int turns_taken;
	protected final String opponentSymbol;
	protected WinRules wr;
	String path;

	
	
	Player(Grid grid, String symbol, WinRules wr,String path){
		
		this.grid = grid;
		this.symbol = symbol;
		this.wr = wr;
		this.path = path;
		opponentSymbol = getOpponentSymbol();
	}
	
	public abstract void play(String previousGameHash,String currentGameHash);
	
	public String getOpponentSymbol() {
		
		if(symbol == "X") {
			return "O";
		}	
		return "X";
	}

}
