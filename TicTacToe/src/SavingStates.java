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
	
	SavingStates(Grid grid, WinRules wr,String path, int rand_avail_pos){
		
		this.grid = grid;
		this.wr = wr;
		this.path = path;
		this.rand_avail_pos = rand_avail_pos;
	}
	
	SavingStates(Grid grid, WinRules wr,String path){
		
		this.grid = grid;
		this.wr = wr;
		this.path = path;
	}
	
	public void createCurrentGridStateJSON() throws ParseException, IOException {
		
		File file = new File(path, grid.getGridHashCode() + ".json");
        boolean exists = file.exists();
        //System.out.println("exits:" + exists);
        
	
		if(!exists) {
	        FileWriter fileWriter = new FileWriter(file);

			createGameStateFile(fileWriter);
			
			fileWriter.flush();
	    	fileWriter.close();
		}
		else {
			
			FileReader fileReader = new FileReader(file);

			//System.out.println("test1.1");
			readAndReplaceGameStateFields(fileReader, file);
			fileReader.close();

		}
	}
	
	public void readAndReplaceGameStateFields(FileReader fileReader, File file) throws IOException, ParseException {
			
		//System.out.println("test1.2");

		//System.out.println("test1.3");

        JSONParser parser = new JSONParser();
        
        Object obj = parser.parse(fileReader);
        
        JSONObject jo = (JSONObject) obj; 
        
        String counter = Long.toString((long) jo.get("stateOccurenceCounter")); 
        
        jo.put("stateOccurenceCounter", (long) jo.get("stateOccurenceCounter") + 1);
        
       
        FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(jo.toString());

        fileWriter.flush();
    	fileWriter.close();
        
        //System.out.println("counter:" + counter);
	}
	
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
	
	public boolean checkIfFutureStateExists(Grid future_grid) {
		
		File future_file = new File(path, future_grid.getGridCopy().getGridHashCode() + ".json");

        boolean exists = future_file.exists();

        return exists;
	}
	
	public void addChildrenToCurrentFile(Grid current_grid, Grid future_grid) throws IOException, ParseException {
		
		//instead of undetermined, set hashcode value to future result value
		FileReader fileReader;

		File file = new File(path, current_grid.getGridCopy().getGridHashCode() + ".json");

		fileReader = new FileReader(file);
		JSONParser parser = new JSONParser();
        
		
		JSONObject obj = (JSONObject) parser.parse(fileReader);
		
		//System.out.println((obj.get("children")).getClass().getName());
        
		JSONObject children = (JSONObject) obj.get("children"); 

        WinRules future_result = new WinRules(future_grid);
        
        //System.out.println("future: " + future_result.checkGrid());
        
        JSONObject child = new JSONObject();
    	String f_result = future_result.checkGrid();

        if(f_result.equals("")) {
//        	child.put("win_percentage_for_player_X", default_percent);
//			child.put("win_percentage_for_player_O", default_percent);
//			child.put("tie_percentage", default_percent);
        	
        	child.put("Randomly chosen grid location", rand_avail_pos);//this works
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
        	//child.put("Randomly chosen grid location", -1);

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
		
		for(String child_key: all_children_keys) {
			//need to access result values for each child
		
			
			JSONObject child_value = (JSONObject) children.get(child_key);
			System.out.println(child_value);
			
			if(child_value.get("win_percentage_for_player_X") != null 
					&& child_value.get("win_percentage_for_player_O") != null
					&& child_value.get("tie_percentage") != null) {
				
				counter++;
				
				Double win_x = (double) child_value.get("win_percentage_for_player_X");
				Double win_o = (double) child_value.get("win_percentage_for_player_O");
				Double tie = (double) child_value.get("tie_percentage");

				win_x_max = Math.max(win_x_max,win_x);
				win_o_max = Math.max(win_o_max,win_o);
				tie_max = Math.max(tie_max, tie);

				parent_win_percentage_for_player_X += win_x;
				parent_win_percentage_for_player_O += win_o;
				parent_tie_percentage += tie;
				
			}
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
	}
	
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
        
        System.out.println("children: " + obj.get("children"));

        
        if(obj2.get("win_percentage_for_player_X") != null 
        		&&  obj2.get("win_percentage_for_player_O") != null
        		&& obj2.get("tie_percentage") != null) {
        	
        	child.put("win_percentage_for_player_X", obj2.get("win_percentage_for_player_X"));
    		child.put("win_percentage_for_player_O", obj2.get("win_percentage_for_player_O"));
    		child.put("tie_percentage", obj2.get("tie_percentage"));

        	
        }
        
        child.put("Randomly chosen grid location", rand_avail_pos);
        
		
        children.put(future_hash,child);
        obj.put("children", children);
        
        updateResult(obj);
        
        System.out.println("children2: " + obj.get("children"));

        
        FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(obj.toString());
		
		fileWriter.flush();
	    fileWriter.close();
        
        fileReader.close();
        fileReader2.close();
	}
		
	public void checkChildrenUpdateResult() throws IOException, ParseException {
		
		File file = new File(path, grid.getGridCopy().getGridHashCode() + ".json");
		
		FileReader fileReader = new FileReader(file);
		
		JSONParser parser = new JSONParser();
        
		Object obj = parser.parse(fileReader);
		
		JSONObject jo = (JSONObject) obj; 
        
        JSONObject children = (JSONObject) jo.get("children"); 
        
        JSONArray key = (JSONArray) children.keySet();

        for(int i = 0; i < ((CharSequence) key).length(); i++) {
        	
        	String future_result = (String) children.get(key);
        	System.out.println("future results: " + future_result);
        }


	}
}
