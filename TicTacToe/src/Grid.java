import java.util.Arrays;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Grid {
	
	private String[] top = {"A","B","C"};
	private String[] side = {"1","2","3"};
	private String default_value = "_";
	private String[][] game_grid = {{"_","_","_"},{"_","_","_"},{"_","_","_"}};
	private int[] positions = {1,2,3,4,5,6,7,8,9};
	private int row_size;
	private int col_size;
	private int print_counter;
	private int open_position_amt;
	
	
	/**  
	 * Constructor for Grid Class
	 * @param none
	 * @return none
	 */
	Grid(){
		row_size = 3;
		col_size = 3;
		print_counter = 0;
		open_position_amt = row_size * col_size;
	}
	
	
	/**  
	 * Prints 2D array, representing Tic-Tac-Toe Board, on the console.
	 * @param none
	 * @return none
	 */
	public void printGrid() {
		
		print_counter++;
		System.out.print(print_counter + ")" + "///////////\n ");
		for(int i = 0; i < col_size; i++) {
			System.out.print(" " + top[i]);
		}
		System.out.print("\n");
		
		for(int i = 0; i < row_size; i++) {
			for(int j = 0; j < col_size; j++) {
				if(j == 0) {
					System.out.print(side[i]);
				}
				System.out.print("|" + game_grid[i][j]);
			}
			System.out.print("|\n");
		}
		System.out.print("//////////////\n");
		
	}
	
	/**  
	 * Accessor method for game_grid attribute
	 * @param none
	 * @return 2D String array representing Tic-Tac-Toe board
	 */
	public String[][] getGrid() {
		
		return game_grid;
	}
	
	/**  
	 * Accessor method for open_position_amt attribute
	 * @param none
	 * @return an integer with the amount of available 
	 * positions on the Tic-Tac-Toe board
	 */
	public int getOpenPositionsAmt() {
		
		return open_position_amt;
	}
	
	/**  
	 * Places player symbol (X or O) on the Tic-Tac-Toe board
	 * @param row and column where players symbol will be placed and the symbol.
	 * @return none
	 */
	public void setSingleGridValue(int row, int col, String value) {
		
		game_grid[row][col] = value;
	
		int index = (row * col_size) + col;
		positions[index] = -1;
	}
	
	/**  
	 * Searches board to acquire all available positions on the Tic-Tac-Toe board.
	 * @param none
	 * @return Integer array with available Tic-Tac-Toe board positions
	 */
	public Integer[] getAvailablePositions() {
		
		ArrayList<Integer> available_positions = new ArrayList<Integer>();
		for(int i = 0; i < positions.length; i++) {
			
			if(positions[i] != -1) {
				available_positions.add(positions[i]);
			}
		}
		
		Integer[] availables = new Integer[available_positions.size()];
		available_positions.toArray(availables);
		
		return availables;
	}
	
	/**  
	 * Decrements by one the amount of available positions 
	 * on the Tic-Tac_Toe board
	 * @param none
	 * @return none
	 */
	public void subtract1FromOpenPositionsAmt() {
		
		this.open_position_amt--;
	}
	
	/**  
	 * Accessor method for default_value("_") attribute
	 * @param none
	 * @return none
	 */
	public String getDefaultValue() {
		return default_value;
	}
	
	/**  
	 * Creates String version of Tic-Tac-Toe board
	 * @param none
	 * @return string representing Tic-Tac-Toe-Board
	 */
	public String gameGridToString() {
		
		String string_grid = "";
		
		for(int i = 0; i < row_size; i++) {
			for(int j = 0; j < col_size; j++) {
				string_grid += game_grid[i][j];
			}
		}
		return string_grid;
	}
	
	/**  
	 * Creates SHA256 Hash code based on the current state of the Tic-Tac_Toe board 
	 * using helper methods getSHA(String input) and toHexString(byte[] hash).
	 * @param none
	 * @return SHA256 Hash code of current Tic-Tac-Toe board
	 */
	public String getGridHashCode() {
	
		try {
			return toHexString(getSHA(gameGridToString()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return ""; 
	}
	
	
	public static byte[] getSHA(String input) throws NoSuchAlgorithmException 
    {  
        // Static getInstance method is called with hashing SHA  
        MessageDigest md = MessageDigest.getInstance("SHA-256");  
  
        // digest() method called  
        // to calculate message digest of an input  
        // and return array of byte 
        return md.digest(input.getBytes(StandardCharsets.UTF_8));  
    } 
	public static String toHexString(byte[] hash) 
    { 
        // Convert byte array into signum representation  
        BigInteger number = new BigInteger(1, hash);  
  
        // Convert message digest into hex value  
        StringBuilder hexString = new StringBuilder(number.toString(16));  
  
        // Pad with leading zeros 
        while (hexString.length() < 32)  
        {  
            hexString.insert(0, '0');  
        }  
  
        return hexString.toString();  
    } 
	
	/**  
	 * Creates a copy of the current Tic-Tac-Toe board/Grid
	 * @param none
	 * @return copy of Tic-Tac-Toe board/Grid
	 */
	public Grid getGridCopy() {
		
		Grid copy = new Grid();
		
		copy.setTop(this.top);
		copy.setSide(this.side);
		copy.setDefaultValue(this.default_value);
		copy.setGameGrid(this.game_grid);
		copy.setPositions(this.positions);
		copy.setRowSize(this.row_size);
		copy.setColSize(this.col_size);
		copy.setPrintCounter(this.print_counter);
		copy.setOpenPositionAmt(open_position_amt);
		
		return copy;
	}
	
	/**  
	 * Sets labels for the columns of the Tic-Tac_Toe board
	 * @param Array with sting elements 
	 * representing the labels for each column
	 * @return none
	 */
	public void setTop(String[] top) {
		
		for(int i = 0; i < (this.top).length; i++) {
			
			this.top[i] = top[i];
		}
	}
	
	/**  
	 * Sets labels for the rows of the Tic-Tac_Toe board
	 * @param Array with sting elements 
	 * representing the labels for each row
	 * @return none
	 */
	public void setSide(String[] side) {
		
		for(int i = 0; i < (this.side).length; i++) {
			
			this.side[i] = side[i];
		}
	}
	
	/**  
	 * Sets the Default Empty value for the Tic-Tac_Toe board
	 * @param none
	 * @return none
	 */
	public void setDefaultValue(String default_value) {
		
		this.default_value = default_value;
	}
	
	/**  
	 * Replaces Tic-Tac_Toe board values with other Tic-Tac-Toe board values
	 * @param none
	 * @return none
	 */
	public void setGameGrid(String[][] game_grid) {
		
		for(int i = 0; i < row_size; i++) {
			for(int j = 0; j < col_size; j++) {
				this.game_grid[i][j] = game_grid[i][j];
			}
		}
	}
	
	/**  
	 * Sets the available positions on the Tic-Tac-Toe board
	 * @param none
	 * @return none
	 */
	public void setPositions(int[] positions) {
		
		for(int i = 0; i < (this.positions).length; i++) {
			
			this.positions[i] = positions[i];
		}
	}
	
	/**  
	 * Sets the amount of rows for the Tic-Tac_Toe board
	 * @param int value representing amount of rows
	 * @return none
	 */
	public void setRowSize(int row_size) {
		this.row_size = row_size;
	}
	
	/**  
	 * Sets the amount of columns for the Tic-Tac_Toe board
	 * @param int value representing amount of columns 
	 * @return none
	 */
	public void setColSize(int col_size) {
		this.col_size = col_size;
	}
	
	/**  
	 * Sets counter to count the amount of moves in each game
	 * @param int value representing amount of columns 
	 * @return none
	 */
	public void setPrintCounter(int print_counter) {
		this.print_counter = print_counter;
	}
	
	
	/**  
	 * Sets the amount available positions on the Tic-Tac-Toe board
	 * @param none
	 * @return none
	 */
	public void setOpenPositionAmt(int open_position_amt) {
		this.open_position_amt = open_position_amt;
	}
	
}
