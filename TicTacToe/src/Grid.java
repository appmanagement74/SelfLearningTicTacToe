import java.util.Arrays;
import java.util.ArrayList;

public class Grid {
	
	
	private String[] top = {"A","B","C"};
	private String[] side = {"1","2","3"};
	private String default_value = "_";
	private String[][] game_grid = {{"_","_","_"},{"_","_","_"},{"_","_","_"}};
	private int[] positions = {1,2,3,4,5,6,7,8,9};
	private int col_amt = 3;
	private int row_amt = 3;

	private int row_size;
	private int col_size;
	private int print_counter;
	private int open_position_amt;
	
	Grid(){
		row_size = 3;
		col_size = 3;
		print_counter = 0;
		open_position_amt = row_size * col_size;
	}
	
	
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
	
	public String[][] getGrid() {
		
		return game_grid;
	}
	
	public int getOpenPositionsAmt() {
		
		return open_position_amt;
	}
	
	public void setSingleGridValue(int row, int col, String value) {
		
		game_grid[row][col] = value;
	
		int index = (row * col_amt) + col;
		positions[index] = -1;
	}
	
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
	
	public void subtract1FromOpenPositionsAmt() {
		
		this.open_position_amt--;
	}
	
	public String getDefaultValue() {
		return default_value;
	}
	
	public int getGridHashCode() {
		
		int hash_code1 = Arrays.deepHashCode(game_grid);
		return hash_code1;
	}
	
	public Grid getGridCopy() {
		
		Grid copy = new Grid();
		
		copy.setTop(this.top);
		copy.setSide(this.side);
		copy.setDefaultValue(this.default_value);
		copy.setGameGrid(this.game_grid);
		copy.setPositions(this.positions);
		copy.setColAmt(this.col_amt);
		copy.setRowAmt(this.row_amt);
		copy.setRowSize(this.row_size);
		copy.setColSize(this.col_size);
		copy.setPrintCounter(this.print_counter);
		copy.setOpenPositionAmt(open_position_amt);
		
		return copy;
	}


	
	public void setTop(String[] top) {
		
		for(int i = 0; i < (this.top).length; i++) {
			
			this.top[i] = top[i];
		}
	}
	
	public void setSide(String[] side) {
		
		for(int i = 0; i < (this.side).length; i++) {
			
			this.side[i] = side[i];
		}
	}
	
	public void setDefaultValue(String default_value) {
		
		this.default_value = default_value;
	}
	
	public void setGameGrid(String[][] game_grid) {
		
		for(int i = 0; i < row_amt; i++) {
			for(int j = 0; j < col_amt; j++) {
				this.game_grid[i][j] = game_grid[i][j];
			}
		}
	}
	
	public void setPositions(int[] positions) {
		
		for(int i = 0; i < (this.positions).length; i++) {
			
			this.positions[i] = positions[i];
		}
	}
	
	public void setColAmt(int col_amt) {
		this.col_amt = col_amt;
	}
	
	public void setRowAmt(int row_amt) {
		this.row_amt = row_amt;
	}
	
	public void setRowSize(int row_size) {
		this.row_size = row_size;
	}
	
	public void setColSize(int col_size) {
		this.col_size = col_size;
	}
	
	public void setPrintCounter(int print_counter) {
		this.print_counter = print_counter;
	}
	
	public void setOpenPositionAmt(int open_position_amt) {
		this.open_position_amt = open_position_amt;
	}
	
}
