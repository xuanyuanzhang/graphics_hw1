package c2g2.game;

import c2g2.engine.graph.Material;
import c2g2.engine.graph.Mesh;
import c2g2.engine.graph.OBJLoader;
import c2g2.engine.GameItem;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import c2g2.engine.GameEngine;
import c2g2.engine.IGameLogic;

public class Grid {
	
	private int scale;
	private int interval = 2;
	public Integer[][] grid;
	
	public Grid(){
		grid = new Integer[][]{{0,0,0,0,0,1,2},
			 				   {0,1,0,0,0,1,0},
			 				   {0,1,0,1,1,1,0},
			 				   {0,1,1,1,0,0,0},
			 				   {0,1,0,0,0,0,0},
			 				   {0,1,1,1,1,1,0},
			 				   {0,1,0,0,0,0,0}};
 	 	scale = grid.length;
	}
	
	
	public boolean canMove(float x, float y, String movement){
		int i = 6-Math.round(y)/2;
		System.out.println("in "+i+" th array");
		int j = Math.round(x)/2;
		System.out.println("in "+j+" th index");
		if(movement.equals("UP"))
			return !(i==0||grid[i-1][j]==0);
		else if(movement.equals("DOWN"))
			return !(i==scale-1||grid[i+1][j]==0);
		else if(movement.equals("RIGHT"))
			return !(j==scale-1||grid[i][j+1]==0);
		else
			return !(j==0||grid[i][j-1]==0);
	}
	
	public boolean isAtTarget(float x, float y){
		int x_axis = Math.round(y)/2;
		int y_axis = Math.round(x)/2;
		return x_axis==(scale-1) && y_axis==(scale-1);
	}
	
	public GameItem[] makeGrid() throws Exception{
		List<GameItem> output = new ArrayList<GameItem>();
		System.out.println(grid.length);
		scale = grid.length;
		Mesh wall = OBJLoader.loadMesh("src/resources/models/cube.obj");
		Material material_wall = new Material(new Vector3f(1f, 0.8f, 0.8f), 1f);
		wall.setMaterial(material_wall);
		Mesh floor = OBJLoader.loadMesh("src/resources/models/cube.obj");
		Material material_floor = new Material(new Vector3f(0.3f, 0.3f, 0.3f), 1f);
		floor.setMaterial(material_floor);
		
		for(int i=0;i<scale;i++){
			for(int j=1;j<=scale;j++){
				if(grid[j-1][i]==0){
					GameItem item = new GameItem(wall);
	        		item.setPosition(interval*i,interval*(scale-j),0);
	        		output.add(item);
				}
				if(grid[j-1][i]==2){
					Mesh box2 = OBJLoader.loadMesh("src/resources/models/cube.obj");
					Material material2 = new Material(new Vector3f(1f, 0f, 0f), 1f);
			        box2.setMaterial(material2);
					GameItem item = new GameItem(box2);
	        		item.setPosition(interval*i,interval*(scale-j),0.5f);
	        		output.add(item);
				}
				if(grid[j-1][i]==1){
					GameItem item = new GameItem(floor);
	        		item.setPosition(interval*i,interval*(scale-j),-2);
	        		output.add(item);
				}
			}
		}
		//x and y axis
		Mesh yAxis = OBJLoader.loadMesh("src/resources/models/cube.obj");
        Material material2 = new Material(new Vector3f(0f, 1f, 0f), 1f);
        yAxis.setMaterial(material2);
        Mesh xAxis = OBJLoader.loadMesh("src/resources/models/cube.obj");
        Material material3 = new Material(new Vector3f(0f, 0f, 1f), 1f);
        xAxis.setMaterial(material3);
        for(int i=0;i<10;i++){
        	GameItem item1 = new GameItem(yAxis);
        	item1.setPosition(0, interval*i, 0);
        	output.add(item1);
        	GameItem item2 = new GameItem(yAxis);
        	item2.setPosition(0, -interval*i, 0);
        	output.add(item2);
        	//GameItem item3 = new GameItem(xAxis);
        	//item3.setPosition(interval*i, 0, 0);
        	//output.add(item3);
        	//GameItem item4 = new GameItem(xAxis);
        	//item4.setPosition(-interval*i, 0, 0);
        	//output.add(item4);
        }
        /*Mesh diag = OBJLoader.loadMesh("src/resources/models/cube.obj");
        Material material4 = new Material(new Vector3f(1f, 0f, 0f), 1f);
        xAxis.setMaterial(material4);
        for(int i=0;i<5;i++){
        	GameItem item1 = new GameItem(yAxis);
        	item1.setPosition(0, interval*i, 0);
        	output.add(item1);
        }*/
		
		GameItem[] arr = output.toArray(new GameItem[output.size()]);
		return arr;
	}
	
	public GameItem[] gridTest() throws Exception{
		List<GameItem> output = new ArrayList<GameItem>();
		
		Mesh box = OBJLoader.loadMesh("src/resources/models/cube.obj");
		Material material = new Material(new Vector3f(1f, 1f, 1f), 1f);
        box.setMaterial(material);
        
        for(int i=0;i<grid.length;i++){
        	for(int j=0;j<grid.length;j++){
        		if(grid[j][i] == 1){
	        		GameItem item = new GameItem(box);
	        		item.setPosition(interval*i,interval*(scale-j),0);
	        		output.add(item);
        		}
        	}
        }
        //x-axis and y-axis
        Mesh box2 = OBJLoader.loadMesh("src/resources/models/cube.obj");
        Material material2 = new Material(new Vector3f(1f, 0f, 0f), 1f);
        box2.setMaterial(material2);
        for(int i=0;i<10;i++){
        	GameItem item1 = new GameItem(box2);
        	item1.setPosition(0, interval*i, 0);
        	output.add(item1);
        	GameItem item2 = new GameItem(box2);
        	item2.setPosition(0, -interval*i, 0);
        	output.add(item2);
        	GameItem item3 = new GameItem(box2);
        	item3.setPosition(interval*i, 0, 0);
        	output.add(item3);
        	GameItem item4 = new GameItem(box2);
        	item4.setPosition(-interval*i, 0, 0);
        	output.add(item4);
        }
        
        GameItem[] arr = output.toArray(new GameItem[output.size()]);
		return arr;
	}
	
	

}
