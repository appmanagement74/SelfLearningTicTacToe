import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SavingStates{
	
	private Grid grid;
    private WinRules wr;
    private String path;
    private static int rand_avail_pos;
    private double default_percent = 0.0;
	
    
    /**  
	 * Constructor for SavingStates Class
	 * @param grid(Tic-Tac-Toe board),wr(game rules instance), path( path of where the JSON nodes/states will be saved), 
	 * rand_avail_pos(randomly generated or calculated board position)
	 * @return none
	 */
	SavingStates(Grid grid, WinRules wr,String path, int rand_avail_pos){
		
		this.grid = grid;
		this.wr = wr;
		this.path = path;
		this.rand_avail_pos = rand_avail_pos;
	}
	
	/**  
	 * Constructor for SavingSates Class without rand_avail_pos(randomly generated or calculated board position)
	 * @param grid(Tic-Tac-Toe board),wr(game rules instance), path( path of where the JSON nodes/states will be saved)
	 * @return none
	 */
	SavingStates(Grid grid, WinRules wr,String path){
		
		this.grid = grid;
		this.wr = wr;
		this.path = path;
	}
	
	/**  
	 * Checks if JSON file for current game state exists,
	 * 		if no, it creates it
	 *		if yes, it increments the JSON state files counter for amount of visits
	 * @param none
	 * @return none
	 */
	public void createCurrentGridStateJSON() throws ParseException, IOException {
		
		File file = new File(path, grid.getGridHashCode() + ".json");
        boolean exists = file.exists();
	
		if(!exists) {
	        FileWriter fileWriter = new FileWriter(file);

			createGameStateFile(fileWriter);
			
			fileWriter.flush();
	    	fileWriter.close();
		}
		else {
			
			FileReader fileReader = new FileReader(file);

			readAndReplaceGameStateFields(fileReader, file);
			fileReader.close();
		}
	}
	
	/**  
	 * Increments the visited counter of the received JSON state file
	 * @param none
	 * @return none
	 */
	public void readAndReplaceGameStateFields(FileReader fileReader, File file) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        
        Object obj = parser.parse(fileReader);
        
        JSONObject jo = (JSONObject) obj; 
        
        String counter = Long.toString((long) jo.get("stateOccurenceCounter")); 
        
        jo.put("stateOccurenceCounter", (long) jo.get("stateOccurenceCounter") + 1);
        
       
        FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(jo.toString());

        fileWriter.flush();
    	fileWriter.close();
	}
	
	/**  
	 * Creates JSON state file for current Tic-Tac-Toe Board/Grid. If current game state is an end to the game,
	 * win lose and tie probability attributes are set to 100% or 0% depending on how the game ended. 
	 * @param fileWriter object (to write Board/Grid values on JSON file)
	 * @return none
	 */
	public void createGameStateFile(FileWriter fileWriter) throws IOException {
		
		JSONObject gameState = new JSONObject();
		
		gameState.put("r1c1",grid.getGrid()[0][0]);
		gameState.put("r1c2",grid.getGrid()[0][1]);
		gameState.put("r1c3",grid.getGrid()[0][2]);
		
		gameState.put("r2c1",grid.getGrid()[1][0]);
		gameState.put("r2c2",grid.getGrid()[1][1]);
		gameState.put("r2c3",grid.getGrid()[1][2]);
		
		gameState.put("r3c1",grid.getGrid()[2][0]);
		gameState.put("r3c2",grid.getGrid()[2][1]);
		gameState.put("r3c3",grid.getGrid()[2][2]);
		gameState.put("stateOccurenceCounter", 1);
		gameState.put("Randomly chosen grid location", rand_avail_pos);
		gameState.put("Children Amount", 0);



		String string_grid = grid.getGrid()[0][0] + "," + grid.getGrid()[0][1] + "," + grid.getGrid()[0][2] + "\n" + 
							 grid.getGrid()[1][0] + "," + grid.getGrid()[1][1] + "," + grid.getGrid()[1][2] + "\n" +
							 grid.getGrid()[2][0] + "," + grid.getGrid()[2][1] + "," + grid.getGrid()[2][2];
		
		gameState.put("Grid",string_grid);
		
		JSONObject children = new JSONObject();	
		String winner = wr.checkGrid();
		if(!winner.equals("")) {
			
			if(winner.equals("X" + wr.getWinMessage())) {
				gameState.put("win_percentage_for_player_X", 100.0);
				gameState.put("win_percentage_for_player_O", 0.0);
				gameState.put("tie_percentage", 0.0);
			}
			else if(winner.equals("O" + wr.getWinMessage())) {
				gameState.put("win_percentage_for_player_X", 0.0);
				gameState.put("win_percentage_for_player_O", 100.0);
				gameState.put("tie_percentage", 0.0);

			}
			else {
				gameState.put("win_percentage_for_player_X", 0.0);
				gameState.put("win_percentage_for_player_O", 0.0);
				gameState.put("tie_percentage", 100.0);
			}

		}
		else if(winner.equals("")){
			
//			gameState.put("win_percentage_for_player_X", default_percent);
//			gameState.put("win_percentage_for_player_O", default_percent);
//			gameState.put("tie_percentage", default_percent);
		}
		else {
			System.out.println("ERROR in creating new node");
			grid.printGrid();
			System.exit(0);
		}
		gameState.put("children", children);

		fileWriter.write(gameState.toString());
	}
	
	/**  
	 * Checks if a JSON state file exist for Grid/Tic-Tac-Toe board state passed in
	 * @param Grid object
	 * @return boolean showing whether passed in grid exists as a JSON file
	 */
	public boolean checkIfStateFileExists(Grid future_grid) {
		
		File future_file = new File(path, future_grid.getGridCopy().getGridHashCode() + ".json");

        boolean exists = future_file.exists();

        return exists;
	}
	
	/**  
	 * Adds children objects to current Grid state JSON file. Sets a Priority attribute to children that don't
	 * lead to an end game (X winning, O winning, Trying). If children lead to an end game, probability values are set 
	 * to 100% for the result(X Winning, O Winning or Tie) and 0% for what did not occur)
	 * @param current_grid (Current Tic-Tac-Toe board state) future_grid ("Future" Tic-Tac-Toe board state)
	 * @return none
	 */
	public void addChildrenToCurrentFile(Grid current_grid, Grid future_grid) throws IOException, ParseException {
		
		//instead of undetermined, set hashcode value to future result value
		FileReader fileReader;

		File file = new File(path, current_grid.getGridCopy().getGridHashCode() + ".json");

		fileReader = new FileReader(file);
		JSONParser parser = new JSONParser();
        
		
		JSONObject obj = (JSONObject) parser.parse(fileReader);
		        
		JSONObject children = (JSONObject) obj.get("children"); 

        WinRules future_result = new WinRules(future_grid);
                
        JSONObject child = new JSONObject();
    	String f_result = future_result.checkGrid();

        if(f_result.equals("")) {
        	
        	child.put("Randomly chosen grid location", rand_avail_pos);//this works
            child.put("Priority", true);
            children.put(future_grid.getGridCopy().getGridHashCode(), child);
            
        }
        else {
        	if(f_result.equals("X" + wr.getWinMessage())) {
            	child.put("win_percentage_for_player_X", 100.0);
            	child.put("win_percentage_for_player_O", 0.0);
            	child.put("tie_percentage", 0.0);
			}
			else if(f_result.equals("O" + wr.getWinMessage())) {
				child.put("win_percentage_for_player_X", 0.0);
				child.put("win_percentage_for_player_O", 100.0);
				child.put("tie_percentage", 0.0);

			}
			else {
				child.put("win_percentage_for_player_X", 0.0);
				child.put("win_percentage_for_player_O", 0.0);
				child.put("tie_percentage", 100.0);
			}
            children.put(future_grid.getGridCopy().getGridHashCode(), child);
        }
        
        //after new children are added, parent result status must be updated
        updateResult(obj);
        
        obj.put("children", children);
        
        FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(obj.toString());
		
		fileWriter.flush();
	    fileWriter.close();
        
        fileReader.close();
	}
	
	
	/**  
	 * Gets the Grid/Tic-Tac-Toe board position from the child of a JSON file.
	 * @param child_value (child object from a JSON state file)
	 * @return randomLoc(Grid/Tic-Tac-Toe board position from the child of a JSON file) 
	 */
	public int getRandomLocationFromParentNode(JSONObject child_value ) {
		
		int randomLoc = 0;
		
		if((child_value.get("Randomly chosen grid location").getClass().getSimpleName()).equals("long") ||
				(child_value.get("Randomly chosen grid location").getClass().getSimpleName()).equals("Long")) {
			
			randomLoc =  (int) ((long)child_value.get("Randomly chosen grid location"));
		}
		else {
			randomLoc =  (int) child_value.get("Randomly chosen grid location");
		}
		
		return randomLoc;
	}
	
	/**  
	 * Checks every child, of JSON state file, and calculates the average probability of X winning, O winning 
	 * and Tying and updates its attributes. It also sets the probability of unvisited children nodes to the max integer value, so
	 * that they have priority in being chosen. 
	 * @param JSON Object representing current state JSON FIle
	 * @return none
	 */
	public void updateResult(JSONObject obj) {
		
		JSONObject children = (JSONObject) obj.get("children"); 

		Set<String> all_children_keys = children.keySet();
		
		Double parent_win_percentage_for_player_X = 0.0;
		Double parent_win_percentage_for_player_O = 0.0;
		Double parent_tie_percentage = 0.0;

		int counter = 0;
		
		double win_x_max = 0;
		double win_o_max = 0;
		double tie_max = 0;
		
		double win_x_min = 10;
		double win_o_min = 10;
		double tie_min = 10;
		
		int best_child_x_pos = 0;
		int best_child_o_pos = 0;
		int best_tie_pos = 0;
		
		int worst_child_x_pos = 0;
		int worst_child_o_pos = 0;
		int worst_tie_pos = 0;
		
		String best_child_x_pos_key = ""; 
		String best_child_o_pos_key = "";
		String best_tie_pos_key = "";
		
		String worst_child_x_pos_key = ""; 
		String worst_child_o_pos_key = "";
		String worst_tie_pos_key = "";
		
		
		int best_child_x_pos_prioritized = 0;
		int best_child_o_pos_prioritized = 0;
		int best_tie_pos_prioritized = 0;
		
		int worst_child_x_pos_prioritized = 0;
		int worst_child_o_pos_prioritized = 0;
		int worst_tie_pos_prioritized = 0;

		int randomLoc = 0;
		
		
		for(String child_key: all_children_keys) {
			//need to access result values for each child
		
			counter++;
			JSONObject child_value = (JSONObject) children.get(child_key);
			//System.out.println(child_value);
			//counter++;
			if(child_value.get("win_percentage_for_player_X") != null 
					&& child_value.get("win_percentage_for_player_O") != null
					&& child_value.get("tie_percentage") != null
					&& child_value.get("Randomly chosen grid location") != null) {
				
				
				//counter++;
				Double win_x = (double) child_value.get("win_percentage_for_player_X");
				Double win_o = (double) child_value.get("win_percentage_for_player_O");
				Double tie = (double) child_value.get("tie_percentage");
				
				
				randomLoc = getRandomLocationFromParentNode(child_value);
				
				
				JSONObject old_child = null;
				
				if(win_x > win_x_max) {
					win_x_max = win_x;
					best_child_x_pos_key = child_key;
					best_child_x_pos = randomLoc;
				}
				else if (win_x == win_x_max){
					//check which one has worst O				
					//if O is the same, tie percentage is the same
					
					old_child = (JSONObject) children.get(best_child_x_pos_key);
					
					if(old_child != null) {
						if(old_child.get("win_percentage_for_player_O") != null) {
							double old_win_o = (double) old_child.get("win_percentage_for_player_O");
							
							if(old_win_o < win_o) {
								best_child_x_pos = getRandomLocationFromParentNode(old_child);
							}
						}
					}
				}
				if(win_o > win_o_max) {
					win_o_max = win_o;
					best_child_o_pos_key = child_key;
					best_child_o_pos = randomLoc;
				}
				else if(win_o == win_o_max){
					
					old_child = (JSONObject) children.get(best_child_o_pos_key);
					
					if(old_child != null) {
						if(old_child.get("win_percentage_for_player_x") != null) {
							double old_win_x = (double) old_child.get("win_percentage_for_player_x");
							
							if(old_win_x < win_x) {
								best_child_o_pos = getRandomLocationFromParentNode(old_child);
							}
						}
					}
					
				}
				
				if(tie > tie_max) {
					tie_max = tie;
					best_tie_pos_key = child_key;
					best_tie_pos = randomLoc;
				}
				
				
				if(win_x < win_x_min) {
					win_x_min = win_x;
					worst_child_o_pos_key = child_key;
					worst_child_x_pos = randomLoc;
				}
				else if(win_x == win_x_min){
					
					old_child = (JSONObject) children.get(best_child_x_pos_key);
					
					if(old_child != null) {
						if(old_child.get("win_percentage_for_player_O") != null) {
							double old_win_o = (double) old_child.get("win_percentage_for_player_O");
							
							if(old_win_o > win_o) {
								worst_child_x_pos = getRandomLocationFromParentNode(old_child);
							}
						}
					}
				}
				if(win_o < win_o_min) {
					win_o_min = win_o;
					worst_child_o_pos_key = child_key;
					worst_child_o_pos = randomLoc;
				}
				else if(win_o == win_o_min) {
					
					old_child = (JSONObject) children.get(best_child_o_pos_key);
					
					if(old_child != null) {
						if(old_child.get("win_percentage_for_player_x") != null) {
							double old_win_x = (double) old_child.get("win_percentage_for_player_x");
							
							if(old_win_x > win_x) {
								worst_child_o_pos = getRandomLocationFromParentNode(old_child);
							}
						}
					}
				}
				
				if(tie < tie_min) {
					tie_min = tie;
					worst_tie_pos_key = child_key;
					worst_tie_pos = randomLoc;
				}
				
				parent_win_percentage_for_player_X += win_x;
				parent_win_percentage_for_player_O += win_o;
				parent_tie_percentage += tie;
				
			}
			
			if(child_value.get("Priority") != null && child_value.get("Randomly chosen grid location") != null) {
				
				boolean prioritized = (boolean) child_value.get("Priority");
				
				randomLoc = 0;
				
				if((child_value.get("Randomly chosen grid location").getClass().getSimpleName()).equals("long") ||
						(child_value.get("Randomly chosen grid location").getClass().getSimpleName()).equals("Long")) {
					
					randomLoc =  (int) ((long)child_value.get("Randomly chosen grid location"));
				}
				else {
					randomLoc =  (int) child_value.get("Randomly chosen grid location");
				}
				
				if(prioritized) {
					
					best_child_x_pos_prioritized = randomLoc;
					best_child_o_pos_prioritized = randomLoc;
					best_tie_pos_prioritized = randomLoc;
					
					worst_child_x_pos_prioritized = randomLoc;
					worst_child_o_pos_prioritized = randomLoc;
					worst_tie_pos_prioritized = randomLoc;
					
					
					
					parent_win_percentage_for_player_X = Double.MAX_VALUE;
					parent_win_percentage_for_player_O = Double.MAX_VALUE;
					parent_tie_percentage = Double.MAX_VALUE;
					
				}
			}
			
		}
		
		if(best_child_x_pos_prioritized > 0 && best_child_o_pos_prioritized > 0 && best_tie_pos_prioritized > 0) {
			best_child_x_pos = best_child_x_pos_prioritized;
			best_child_o_pos = best_child_o_pos_prioritized;
			best_tie_pos = best_tie_pos_prioritized;
			
			worst_child_x_pos = worst_child_x_pos_prioritized;
			worst_child_o_pos = worst_child_o_pos_prioritized;
			worst_tie_pos = worst_tie_pos_prioritized;
		}
		
	
		
		parent_win_percentage_for_player_X = parent_win_percentage_for_player_X/counter;
		parent_win_percentage_for_player_O = parent_win_percentage_for_player_O/counter;;
		parent_tie_percentage = parent_tie_percentage/counter;
		
		obj.put("win_percentage_for_player_X",parent_win_percentage_for_player_X);
		obj.put("win_percentage_for_player_O",parent_win_percentage_for_player_O);
		obj.put("tie_percentage",parent_tie_percentage);
		
		
		obj.put("MAX win_percentage_for_player_X",win_x_max);
		obj.put("MAX win_percentage_for_player_O",win_o_max);
		obj.put("MAX tie_percentage",tie_max);
		
		
		obj.put("MIN win_percentage_for_player_X",win_x_min);
		obj.put("MIN win_percentage_for_player_O",win_o_min);
		obj.put("MIN tie_percentage",tie_min);
		
		
		obj.put("BEST child X",best_child_x_pos);
		obj.put("BEST child O",best_child_o_pos);
		obj.put("BEST child tie", best_tie_pos);
		
		
		obj.put("WORST child X",worst_child_x_pos);
		obj.put("WORST child O",worst_child_o_pos);
		obj.put("WORST child tie", worst_tie_pos);
		obj.put("Children Amount", children.size());
	
	}
	
	/**  
	 * Updates the children of the current Grid JSON state file, so that they reflect correct the "Future" grid
	 * JSON state files they point to. Priority for child visitation is set to true, if future file doesn't exist.
	 * @param current_grid (current Tic-Tac-Toe board game state), future_grid (future Tic-Tac-Toe board game state)
	 * @return none
	 */
	public void updateChildrenResults(Grid current_grid, Grid future_grid) throws IOException, ParseException {
		
		//instead of undetermined, set hashcode value to future result value
		FileReader fileReader;
		FileReader fileReader2;

		String future_hash = future_grid.getGridCopy().getGridHashCode();
		File file = new File(path, current_grid.getGridCopy().getGridHashCode() + ".json");
		File file2 = new File(path, future_hash + ".json");

		fileReader = new FileReader(file);
		fileReader2 = new FileReader(file2);

		JSONParser parser = new JSONParser();
		JSONParser parser2 = new JSONParser();

        
		JSONObject obj = (JSONObject) parser.parse(fileReader);
		JSONObject obj2 = (JSONObject) parser2.parse(fileReader2);

		        
		JSONObject children = (JSONObject) obj.get("children"); 
        
        
        JSONObject child = new JSONObject();
        
        
        if(obj2.get("win_percentage_for_player_X") != null 
        		&&  obj2.get("win_percentage_for_player_O") != null
        		&& obj2.get("tie_percentage") != null) {
        	
        	child.put("win_percentage_for_player_X", obj2.get("win_percentage_for_player_X"));
    		child.put("win_percentage_for_player_O", obj2.get("win_percentage_for_player_O"));
    		child.put("tie_percentage", obj2.get("tie_percentage"));
    		child.put("Priority", false);
        	
        }
        else {
            child.put("Priority", true);

        }
        
        
        child.put("Randomly chosen grid location", rand_avail_pos);

        
        children.put(future_hash,child);
        obj.put("children", children);
        
        updateResult(obj);
        
        
        FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(obj.toString());
		
		fileWriter.flush();
	    fileWriter.close();
        
        fileReader.close();
        fileReader2.close();
	}
	
	/**  
	 * locates child with best possibility of winning(or in some cases tying)
	 * @param Symbol(Symbol of player( X or O))
	 * @return integer position value of child with best chances of winning.
	 */
	public int getBestChild(String symbol) throws IOException, ParseException {
		
		
		FileReader fileReader;
		
		File file = new File(path, grid.getGridCopy().getGridHashCode() + ".json");
		
		fileReader = new FileReader(file);
		
		JSONParser parser = new JSONParser();
		
		JSONObject obj = (JSONObject) parser.parse(fileReader);
		
		
		if((int)((long)(obj.get( "Children Amount"))) < grid.getOpenPositionsAmt()) {
			return 0;
		}
		
		
		if(obj.get("BEST child X") != null 
				&& obj.get("BEST child O") != null
				&& obj.get("BEST child tie") != null
				&& obj.get("WORST child X") != null
				&& obj.get("WORST child O") != null
				&& obj.get("WORST child tie") != null) {
			
			
			int best_child_x_pos = (int) ((long)obj.get("BEST child X"));
			int best_child_o_pos = (int) ((long)obj.get("BEST child O"));
			int best_tie_pos = (int) ((long)obj.get("BEST child tie"));
			
			int worst_child_x_pos = (int) ((long)obj.get("WORST child X"));
			int worst_child_o_pos = (int) ((long)obj.get("WORST child O"));
			int worst_tie_pos = (int) ((long)obj.get("WORST child tie"));
			
			
			if(symbol.equals("X")) {
				
				if(best_child_x_pos > 0) {
					return best_child_x_pos;
				}
				else {
					if(best_tie_pos > 0) {
						return best_tie_pos;
					}
					else {
						return worst_child_o_pos;
					}
				}
			}
			else {
				if(best_child_o_pos > 0) {
					return best_child_o_pos;
				}
				else {
					if(best_tie_pos > 0) {
						return best_tie_pos;
					}
					else {
						return worst_child_x_pos;
					}
				}
			}
		}
		
		fileReader.close();
		return 0;
	}
}
