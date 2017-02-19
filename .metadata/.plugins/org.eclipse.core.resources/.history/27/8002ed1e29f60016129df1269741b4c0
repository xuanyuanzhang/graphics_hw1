package c2g2.engine.graph;

import java.io.BufferedReader;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;


public class OBJLoader {
    public static Mesh loadMesh(String fileName) throws Exception {
    	//// --- student code ---
    	
    	float[] positions;
    	float[] textCoords;
    	float[] norms;
    	int[] indices;
    	
    	//positions = new float[]{-0.5f,-0.5f,-0.5f,-0.5f,-0.5f,0.5f,-0.5f,0.5f,-0.5f,-0.5f,0.5f,0.5f,0.5f,-0.5f,-0.5f,0.5f,-0.5f,0.5f,0.5f,0.5f,-0.5f,0.5f,0.5f,0.5f}; 
    	//textCoords = new float[]{0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f};
    	//norms = new float[]{0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f};
    	//indices = new int[]{0,6,4,0,2,6,0,3,2,0,1,3,2,7,6,2,3,7,4,6,7,4,7,5,0,4,5,0,5,1,1,5,7,1,7,3};
        //your task is to read data from an .obj file and fill in those arrays.
        //the data in those arrays should use following format.
        //positions[0]=v[0].position.x positions[1]=v[0].position.y positions[2]=v[0].position.z positions[3]=v[1].position.x ...
        //textCoords[0]=v[0].texture_coordinates.x textCoords[1]=v[0].texture_coordinates.y textCoords[2]=v[1].texture_coordinates.x ...
        //norms[0]=v[0].normals.x norms[1]=v[0].normals.y norms[2]=v[0].normals.z norms[3]=v[1].normals.x...
        //indices[0]=face[0].ind[0] indices[1]=face[0].ind[1] indices[2]=face[0].ind[2] indices[3]=face[1].ind[0]...(assuming all the faces are triangle face)
    	/*positions = new float[]{1.0f, -1.0f, -1.0f,
        		1.0f, -1.0f, 1.0f,
        		-1.0f, -1.0f, 1.0f,
        		-1.0f, -1.0f, -1.0f,
        		1.0f, 1.0f, -1.0f,
        		1.0f, 1.0f, 1.0f,
        		-1.0f, 1.0f, 1.0f,
        		-1.0f, 1.0f, -1.0f,
        		1.0f, -1.0f, -1.0f,
        		1.0f, -1.0f, -1.0f,
        		1.0f, -1.0f, 1.0f,
        		1.0f, -1.0f, 1.0f,
        		-1.0f, -1.0f, -1.0f,
        		-1.0f, -1.0f, -1.0f,
        		1.0f, 1.0f, -1.0f,
        		1.0f, 1.0f, -1.0f,
        		-1.0f, -1.0f, 1.0f,
        		-1.0f, -1.0f, 1.0f,
        		1.0f, 1.0f, 1.0f,
        		1.0f, 1.0f, 1.0f,
        		-1.0f, 1.0f, 1.0f,
        		-1.0f, 1.0f, 1.0f,
        		-1.0f, 1.0f, -1.0f,
        		-1.0f, 1.0f, -1.0f}; 
    	textCoords = new float[]{0.50f, 1.0f,
    			0.50f, 0.50f,
    			1.0f, 0.50f,
    			0.0f, 0.50f,
    			0.0f, 0.0f,
    			0.50f, 0.0f,
    			0.0f, 1.0f,
    			0.00020f, 1.0f,
    			0.50f, 1.0f,
    			1.0f, 1.0f};
    	norms = new float[]{0.0f, -1.0f, 0.0f,
    			0.0f, 1.0f, 0.0f,
    			1.0f, 0.0f, 0.0f,
    			0.0f, 0.0f, 1.0f,
    			-1.0f, 0.0f, 0.0f,
    			0.0f, 0.0f, -1.0f};
    	indices = new int[]{11,1,1,17,2,1,13,3,1,
    			24,4,2,22,5,2,20,6,2,
    			15,1,3,19,7,3,12,4,3,
    			6,1,4,21,8,4,18,4,4,
    			3,2,5,7,9,5,23,7,5,
    			1,4,6,4,2,6,8,1,6,
    			9,10,1,11,1,1,13,3,1,
    			16,2,2,24,4,2,20,6,2,
    			10,2,3,15,1,3,12,4,3,
    			2,2,4,6,1,4,18,4,4,
    			14,4,5,3,2,5,23,7,5,
    			5,7,6,1,4,6,8,1,6};
        for(int i=0;i<indices.length;i++){
        	indices[i] --;
        }*/
        
    	ArrayList<String> input = new ArrayList<String>();
      	
        int position_count = 0;
  		int textCoords_count = 0;
  		int norms_count = 0;
  		int indices_count = 0;
  		
        BufferedReader br = new BufferedReader(new FileReader(fileName));
      	try{
      		String line = br.readLine();
      		if(fileName.equals("src/resources/models/bunny.obj")){
      			line = br.readLine();
      			line = br.readLine();
      			line = br.readLine();
      		}
      		
      		while(line != null){
      			//System.out.println(line);
      			//System.out.println(line.charAt(0));
      			if((line.charAt(0)=='v' || line.charAt(0)=='f')&&(line.charAt(4) < 'a' || line.charAt(4) > 'z')){
      				//System.out.println(line);
      				input.add(line);
  	    			if(line.charAt(0)=='v' && line.charAt(1)==' '){
  	    				position_count+=3;
  	    			}
  	    			else if(line.charAt(0)=='v' && line.charAt(1)=='t')
  	    				textCoords_count+=2;
  	    			else if(line.charAt(0)=='v' && line.charAt(1)=='n')
  	    				norms_count+=3;
  	    			else if(line.charAt(0)=='f')
  	    				indices_count+=3;
      			}
      			line = br.readLine();
      		}
      	}finally{
      		br.close();
      	}
      	positions = new float[position_count];
        textCoords = new float[textCoords_count];
        norms = new float[norms_count];
        indices = new int[indices_count];
      	
        position_count = 0;
  		textCoords_count = 0;
  		norms_count = 0;
  		indices_count = 0;
          
        for(int i=0;i<input.size();i++){
        	String[] output;
        	if(fileName.equals("src/resources/models/cube2.obj")){
        		output = input.get(i).split("  ");
        	}
        	else{
        		output = input.get(i).split(" ");
        	}
          	String[] arr;
          	int l = output.length;
  			if(output[0].equals("v")){
  				//System.out.println("yes?");
  				for(int j=1;j<l;j++){
  					positions[position_count] = Float.valueOf(output[j]);
  					//System.out.println("first number is: "+ positions[position_count]);
  					position_count ++;
  				}
  			}
  			else if(output[0].equals("vt")){
  				for(int j=1;j<l;j++){
  					textCoords[textCoords_count] = Float.valueOf(output[j]);
  					//System.out.println("first number is: "+ textCoords[textCoords_count]);
  					textCoords_count ++;
  				}
  			}
  			else if(output[0].equals("vn")){
  				for(int j=1;j<l;j++){
  					norms[norms_count] = Float.valueOf(output[j]);
  					//System.out.println("first number is: "+ norms[norms_count]);
  					norms_count ++;
  				}
  			}
  			
  			else if(output[0].equals("f")){
  				//int size = (fileName.equals("src/resources/models/bunny.obj") ? )
  				for(int j=1;j<l;j++){
  					if(fileName.equals("src/resources/models/bunny.obj")){
  						arr = output[j].split("//");
  					}
  					else if(fileName.equals("src/resources/models/cube2.obj")){
  						arr = output[j].split("  ");
  					}
  					else{
  						arr = output[j].split("/");
  					}
  					//System.out.println(arr.length);
  					System.out.println(arr[0]);
					//System.out.println(arr[k]);
					//System.out.println(indices_count);
					indices[indices_count] = Integer.valueOf(arr[0])-1;
					//System.out.println("first number is: "+ indices[indices_count]);
					indices_count ++;
  				}
  			}
          }
        
        return new Mesh(positions, textCoords, norms, indices);
    }

}
