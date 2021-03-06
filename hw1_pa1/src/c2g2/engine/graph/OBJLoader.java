package c2g2.engine.graph;

import java.lang.Object;
import java.io.BufferedReader;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;


public class OBJLoader {
    	
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
    	
        
    
    private static Vector3f convertVertex(String line){
    	line = line.replaceAll("\\s+", " ");
    	String[] s = line.split(" ");
    	float x = Float.valueOf(s[1]);
    	float y = Float.valueOf(s[2]);
    	float z = Float.valueOf(s[3]);
    	Vector3f output = new Vector3f(x,y,z);
    	return output;
    }
    
    private static Vector2f convertTexture(String line){
    	line = line.replaceAll("\\s+", " ");
    	String[] s = line.split(" ");
    	float x = Float.valueOf(s[1]);
    	float y = Float.valueOf(s[2]);
    	Vector2f output = new Vector2f(x,y);
    	return output;
    }
    
    private static Vector3f convertNormal(String line){
    	line = line.replaceAll("\\s+", " ");
    	String[] s = line.split(" ");
    	float x = Float.valueOf(s[1]);
    	float y = Float.valueOf(s[2]);
    	float z = Float.valueOf(s[3]);
    	Vector3f output = new Vector3f(x,y,z);
    	return output;
    }
    
    private static Integer[][] convertFace(String line){
    	line = line.replaceAll("\\s+"," ");
    	String[] s = line.split(" ");
    	int count = 0;//number of elements
    	if(s[1].length()==1)
    		count = 1;
    	else{
			for(int j=0;j<s[1].length()-1;j++){
				if(s[1].charAt(j)=='/' && s[1].charAt(j+1)=='/'){
					count = 2;
					break;
				}
				else if(s[1].charAt(j)=='/')
					count = 3;
			}
			if(count==0)
				count = 1;
    	}
    	//System.out.println("count is: "+count);
    	Integer[][] output = new Integer[3][];
		if(count==1){
			output[0] = new Integer[]{Integer.valueOf(s[1])-1};
			output[1] = new Integer[]{Integer.valueOf(s[2])-1};
			output[2] = new Integer[]{Integer.valueOf(s[3])-1};
		}
		else if(count==2){
			String[] temp1 = s[1].split("//");
			output[0] = new Integer[]{Integer.valueOf(temp1[0])-1,Integer.valueOf(temp1[1])-1};
			String[] temp2 = s[2].split("//");
			output[1] = new Integer[]{Integer.valueOf(temp2[0])-1,Integer.valueOf(temp2[1])-1};
			String[] temp3 = s[3].split("//");
			output[2] = new Integer[]{Integer.valueOf(temp3[0])-1,Integer.valueOf(temp3[1])-1};
		}
		else{
			String[] temp1 = s[1].split("/");
			output[0] = new Integer[]{Integer.valueOf(temp1[0])-1,Integer.valueOf(temp1[1])-1,Integer.valueOf(temp1[2])-1};
			String[] temp2 = s[2].split("/");
			output[1] = new Integer[]{Integer.valueOf(temp2[0])-1,Integer.valueOf(temp1[1])-1,Integer.valueOf(temp2[2])-1};
			String[] temp3 = s[3].split("/");
			output[2] = new Integer[]{Integer.valueOf(temp3[0])-1,Integer.valueOf(temp1[1])-1,Integer.valueOf(temp3[2])-1};
		}
		//System.out.print(output.x+" ");
		//System.out.print(output.y+" ");
		//System.out.print(output.z+"\n");
		
    	return output;
    }
    
    public static void getNormsTextCoords(List<Integer[][]> ListI,
    		List<Vector2f> ListT,List<Vector3f> ListN,List<Integer> indices,
    		float[] norms,float[] textCoords){
    	for(Integer[][] tuple : ListI){
    		for(int i=0;i<3;i++){
    			int index = tuple[i][0];
    			indices.add(index);
    			
    			Vector2f texture = ListT.get(tuple[i][1]);
    			textCoords[2*index] = texture.x;
    			textCoords[2*index+1] = texture.y;
    			
    			Vector3f norm = ListN.get(tuple[i][2]);
    			norms[3*index] = norm.x;
    			norms[3*index+1] = norm.y;
    			norms[3*index+2] = norm.z;
    		}
    	}
    	
    }
    
    public static Mesh loadMesh(String fileName) throws Exception {
    	//// --- student code ---
    	
    	List<Vector3f> ListP = new ArrayList<Vector3f>();
    	List<Vector2f> ListT = new ArrayList<Vector2f>();
    	List<Vector3f> ListN = new ArrayList<Vector3f>();
    	List<Integer[][]> ListI = new ArrayList<Integer[][]>();
    	
  		
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        
        String line;
        while((line=br.readLine())!=null){
        	//System.out.println(line);
        	if(line.split(" ")[0].equals("v")){
        		//System.out.println("yes it is vertex");
        		Vector3f output = convertVertex(line);
        		ListP.add(output);
        	}
        	else if(line.split(" ")[0].equals("vt")){
        		Vector2f output = convertTexture(line);
        		ListT.add(output);
        	}
        	else if(line.split(" ")[0].equals("vn")){
        		Vector3f output = convertNormal(line);
        		ListN.add(output);
        	}
        	else if(line.split(" ")[0].equals("f")){
        		Integer[][] output = convertFace(line);
        		ListI.add(output);
        	}
        }
        br.close();
        float[] positions = new float[ListP.size()*3];
        //System.out.println(ListP.size());
        //System.out.println("vertices are: ");
        for(int i=0;i<ListP.size();i++){
        	positions[3*i] = ListP.get(i).x;
        	positions[3*i+1] = ListP.get(i).y;
        	positions[3*i+2] = ListP.get(i).z;
        	//System.out.println(positions[i]+" ");
        }
        
        float[] norms = new float[ListP.size()*3];
        float[] textCoords = new float[ListP.size()*2];
        List<Integer> indices = new ArrayList<Integer>();
        
        if(ListI.get(0)[0].length==1){
        	for(Integer[][] tuple : ListI){
        		for(int i=0;i<3;i++){
        			int index = tuple[i][0];
        			indices.add(index);
        			
        			textCoords[2*index] = 0;
        			textCoords[2*index+1] = 0;
        			
        			norms[3*index] = 0;
        			norms[3*index+1] = 0;
        			norms[3*index+2] = 0;
        		}
        	}
        }
        else if(ListI.get(0)[0].length==2){
        	for(Integer[][] tuple : ListI){
        		for(int i=0;i<3;i++){
        			int index = tuple[i][0];
        			indices.add(index);
        			
        			textCoords[2*index] = 0;
        			textCoords[2*index+1] = 0;
        			
        			Vector3f norm = ListN.get(tuple[i][1]);
        			norms[3*index] = norm.x;
        			norms[3*index+1] = norm.y;
        			norms[3*index+2] = norm.z;
        		}
        	}
        }
        else{
        	for(Integer[][] tuple : ListI){
        		for(int i=0;i<3;i++){
        			int index = tuple[i][0];
        			indices.add(index);
        			
        			Vector2f texture = ListT.get(tuple[i][1]);
        			textCoords[2*index] = texture.x;
        			textCoords[2*index+1] = texture.y;
        			
        			Vector3f norm = ListN.get(tuple[i][2]);
        			norms[3*index] = norm.x;
        			norms[3*index+1] = norm.y;
        			norms[3*index+2] = norm.z;
        		}
        	}
        }
        int[] indicesArray = new int[indices.size()];
        for(int i=0;i<indices.size();i++){
        	indicesArray[i] = indices.get(i);
        }
        		
    	return new Mesh(positions, textCoords, norms, indicesArray);
    }
    
    /*public static void main(String[] args) throws Exception{
    	
    	//Mesh m = OBJLoader.loadMesh("src/resources/models/cube.obj");
    	System.out.println("\n");
    	Mesh m2 = OBJLoader.loadMesh("src/resources/models/cube"
    			+ ".obj");
    }*/

}
