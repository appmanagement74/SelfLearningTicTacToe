
public class WinRules {
	
	private Grid grid;
	private boolean game_has_ended;
	private String win_message;
	private String tie_message;
	
	WinRules(Grid grid){
		this.grid = grid;
		game_has_ended = false;
		win_message = " Wins!";
		tie_message = "Tie!";
	}
	
	WinRules(){
		game_has_ended = false;
		win_message = " Wins!";
		tie_message = "Tie!";
	}
	
	public String checkGrid() {
		
		if(grid.getOpenPositionsAmt() <= 0) {
			game_has_ended = true;
		}
		
		String diag = checkDiagonal();
		String horz =checkHorizonal();
		String vert = checkVertical();
		
		if((!diag.equals(""))) {
			game_has_ended = true;
			return (diag + win_message);
		}
		else if((!horz.equals(""))) {
			game_has_ended = true;
			return (horz + win_message);
		}
		else if((!vert.equals(""))) {
			game_has_ended = true;
			return (vert + win_message);
		}
		else {
			if(diag.equals("") && horz.equals("") && vert.equals("") && game_has_ended)
			return (diag + tie_message);
		}
		
		return "";
		
	}
	
	public String checkDiagonal() {
		
		String r1c1 = grid.getGrid()[0][0];
		String r2c2 = grid.getGrid()[1][1];
		String r3c3 = grid.getGrid()[2][2];
		
		String r1c3 = grid.getGrid()[0][2];
		String r3c1 = grid.getGrid()[2][0];
		
		if(r1c1.equals(r2c2) && r1c1.equals(r3c3) && (!r1c1.equals(grid.getDefaultValue()))) {
			return r1c1;
		}
		else if(r1c3.equals(r2c2) && r1c3.equals(r3c1) && (!r1c3.equals(grid.getDefaultValue()))){
			return r1c3;
		}
		
		
		return "";
	}
	
	public String checkHorizonal() {
		
		String r1c1 = grid.getGrid()[0][0];
		String r1c2 = grid.getGrid()[0][1];
		String r1c3 = grid.getGrid()[0][2];
		
		String r2c1 = grid.getGrid()[1][0];
		String r2c2 = grid.getGrid()[1][1];
		String r2c3 = grid.getGrid()[1][2];
		
		String r3c1 = grid.getGrid()[2][0];
		String r3c2 = grid.getGrid()[2][1];
		String r3c3 = grid.getGrid()[2][2];
		
		
		if(r1c1.equals(r1c2) && r1c1.equals(r1c3) && (!r1c1.equals(grid.getDefaultValue()))) {
			return r1c1;
		}
		else if(r2c1.equals(r2c2) && r2c1.equals(r2c3) && (!r2c3.equals(grid.getDefaultValue()))){
			return r2c1;
		}
		else if(r3c1.equals(r3c2) && r3c1.equals(r3c3) && (!r3c1.equals(grid.getDefaultValue()))) {
			return r3c1;
		}
		
		return "";
	}
	
	public String checkVertical() {
		
		String r1c1 = grid.getGrid()[0][0];
		String r2c1 = grid.getGrid()[1][0];
		String r3c1 = grid.getGrid()[2][0];
		
		String r1c2 = grid.getGrid()[0][1];
		String r2c2 = grid.getGrid()[1][1];
		String r3c2 = grid.getGrid()[2][1];
		
		String r1c3 = grid.getGrid()[0][2];
		String r2c3 = grid.getGrid()[1][2];
		String r3c3 = grid.getGrid()[2][2];
		
		if(r1c1.equals(r2c1) && r1c1.equals(r3c1) && (!r1c1.equals(grid.getDefaultValue()))) {
			return r1c1;
		}
		else if(r1c2.equals(r2c2) && r1c2.equals(r3c2) && (!r1c2.equals(grid.getDefaultValue()))){
			return r1c2;
		}
		else if(r1c3.equals(r2c3) && r1c3.equals(r3c3) && (!r1c3.equals(grid.getDefaultValue()))) {
			return r1c3;
		}
		
		return "";
	}
	
	public boolean getGameHasEnded() {
		return game_has_ended;
	}
	
	public String getWinMessage() {
		
		return win_message;
	}
	
	public String getTieMessage() {
		return tie_message;
	}
}
