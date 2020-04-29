import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SavingStates{
	
	private Grid grid;
    private WinRules wr;
    private String path;
	
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

		
		String string_grid = grid.getGrid()[0][0] + "," + grid.getGrid()[0][1] + "," + grid.getGrid()[0][2] + "\n" + 
							 grid.getGrid()[1][0] + "," + grid.getGrid()[1][1] + "," + grid.getGrid()[1][2] + "\n" +
							 grid.getGrid()[2][0] + "," + grid.getGrid()[2][1] + "," + grid.getGrid()[2][2];
		
		gameState.put("Grid",string_grid);
		
		JSONObject children = new JSONObject();	
		String winner = wr.checkGrid();
		if(!winner.equals("")) {
			gameState.put("result", winner);
		}
		else if(winner.equals("")){
			gameState.put("result", "Undetermined");
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
        
        if(future_result.checkGrid().equals("")) {
            children.put(future_grid.getGridCopy().getGridHashCode(), "undetermined");
        }
        else {
            children.put(future_grid.getGridCopy().getGridHashCode(), future_result.checkGrid());
        }
        
        obj.put("children", children);
        
        FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(obj.toString());
		
		fileWriter.flush();
	    fileWriter.close();
        
        fileReader.close();
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
