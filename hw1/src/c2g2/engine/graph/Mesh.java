package c2g2.engine.graph;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

public class Mesh {

    private int vaoId;

    private List<Integer> vboIdList;

    private int vertexCount;

    private Material material;
    
    private float[] pos;
    private float[] textco;
    private float[] norms;
    private int[] inds;
    
    
    public Mesh(){
    	this(new float[]{-0.5f,-0.5f,-0.5f,-0.5f,-0.5f,0.5f,-0.5f,0.5f,-0.5f,-0.5f,0.5f,0.5f,0.5f,-0.5f,-0.5f,0.5f,-0.5f,0.5f,0.5f,0.5f,-0.5f,0.5f,0.5f,0.5f}, 
    			new float[]{0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f}, 
    			new float[]{0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f}, 
    			new int[]{0,6,4,0,2,6,0,3,2,0,1,3,2,7,6,2,3,7,4,6,7,4,7,5,0,4,5,0,5,1,1,5,7,1,7,3});
    }
    
    public void setMesh(float[] positions, float[] textCoords, float[] normals, int[] indices){
    	pos = positions;
    	textco = textCoords;
    	norms = normals;
    	inds = indices;
    	FloatBuffer posBuffer = null;
        FloatBuffer textCoordsBuffer = null;
        FloatBuffer vecNormalsBuffer = null;
        IntBuffer indicesBuffer = null;
        System.out.println("create mesh:");
        System.out.println("v: "+positions.length+" t: "+textCoords.length+" n: "+normals.length+" idx: "+indices.length);
        try {
            vertexCount = indices.length;
            vboIdList = new ArrayList<Integer>();

            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            // Position VBO
            int vboId = glGenBuffers();
            vboIdList.add(vboId);
            posBuffer = MemoryUtil.memAllocFloat(positions.length);
            posBuffer.put(positions).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            // Texture coordinates VBO
            vboId = glGenBuffers();
            vboIdList.add(vboId);
            textCoordsBuffer = MemoryUtil.memAllocFloat(textCoords.length);
            textCoordsBuffer.put(textCoords).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

            // Vertex normals VBO
            vboId = glGenBuffers();
            vboIdList.add(vboId);
            vecNormalsBuffer = MemoryUtil.memAllocFloat(normals.length);
            vecNormalsBuffer.put(normals).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, vecNormalsBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);

            // Index VBO
            vboId = glGenBuffers();
            vboIdList.add(vboId);
            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        } finally {
            if (posBuffer != null) {
                MemoryUtil.memFree(posBuffer);
            }
            if (textCoordsBuffer != null) {
                MemoryUtil.memFree(textCoordsBuffer);
            }
            if (vecNormalsBuffer != null) {
                MemoryUtil.memFree(vecNormalsBuffer);
            }
            if (indicesBuffer != null) {
                MemoryUtil.memFree(indicesBuffer);
            }
        }
    }

    public Mesh(float[] positions, float[] textCoords, float[] normals, int[] indices) {
    	setMesh(positions, textCoords, normals, indices);        
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void render() {


        // Draw the mesh
        glBindVertexArray(getVaoId());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);

        // Restore state
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void cleanUp() {
        glDisableVertexAttribArray(0);

        // Delete the VBOs
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        for (int vboId : vboIdList) {
            glDeleteBuffers(vboId);
        }

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }
    
    public void scaleMesh(float sx, float sy, float sz){
    	cleanUp(); //clean up buffer
    	//Reset position of each point
    	//Do not change textco, norms, inds
    	//student code 
    	for (int i = 0; i < pos.length/3; i++) {
    		pos[3*i] = pos[3*i] * sx;
    		pos[3*i+1] = pos[3*i+1] * sy;
    		pos[3*i+2] = pos[3*i+2] * sz;
		}   	
    	setMesh(pos, textco, norms, inds);
    }
    
    public void translateMesh(Vector3f trans){
    	cleanUp();
    	//reset position of each point
    	//Do not change textco, norms, inds
    	//student code
    	for(int i=0; i< pos.length/3; i++){
    		pos[3*i] = pos[3*i] + trans.x;
    		pos[3*i+1] = pos[3*i+1] + trans.y;
    		pos[3*i+2] = pos[3*i+2] + trans.z;
    	}
    	setMesh(pos, textco, norms, inds);
    }
    
    public void rotateMesh(Vector3f axis, float angle){
    	cleanUp();
    	//reset position of each point
    	//Do not change textco, norms, inds
    	//student code
    	axis.normalize();
    	for(int i=0; i< pos.length/3; i++){
    		float u = axis.x;
    		float v = axis.y;
    		float w = axis.z;
    		float pos_x = pos[3*i];
    		float pos_y = pos[3*i+1];
    		float pos_z = pos[3*i+2];
    		float s = (float) Math.sin(Math.toRadians(angle));
    		float c = (float) Math.cos(Math.toRadians(angle));
    		float m00 = u*u*(1-c)+c;
    		float m01 = u*v*(1-c)+w*s;
    		float m02 = u*w*(1-c)-v*s;
    		float m10 = u*v*(1-c)-w*s;
    		float m11 = v*v*(1-c)+c;
    		float m12 = v*w*(1-c)+u*s;
    		float m20 = u*w*(1-c)+v*s;
    		float m21 = v*w*(1-c)-u*s;
    		float m22 = w*w*(1-c)+c;
    		pos[3*i] = m00*pos_x + m01*pos_y + m02*pos_z;
    		pos[3*i+1] = m10*pos_x + m11*pos_y + m12*pos_z;
    		pos[3*i+2] = m20*pos_x + m21*pos_y + m22*pos_z;
    	}
    	setMesh(pos, textco, norms, inds);
    }
    
    public void reflectMesh(Vector3f p, Vector3f n){
    	cleanUp();
    	//reset position of each point
    	//Do not change textco, norms, inds
    	//student code
    	n.normalize();
    	
        float coef = -n.x * p.x - n.y * p.y - n.z * p.z;
	    float m00 = - 2 * n.x * n.x + 1.0F;
	    float m01 = - 2 * n.x * n.y;
	    float m02 = - 2 * n.x * n.z;
        float m10 = - 2 * n.y * n.x;
        float m11 = - 2 * n.y * n.y + 1.0F;
        float m12 = - 2 * n.y * n.z;
        float m20 = - 2 * n.z * n.x;
        float m21 = - 2 * n.z * n.y;
        float m22 = - 2 * n.z * n.z + 1.0F;
        float m30 = - 2 * coef * n.x;
        float m31 = - 2 * coef * n.y;
        float m32 = - 2 * coef * n.z;
      
        for(int i=0; i< pos.length/3; i++){
        	float pos_x = pos[3*i];
    		float pos_y = pos[3*i+1];
    		float pos_z = pos[3*i+2];
        	float divisor = pos_x * m30 + pos_y * m31 + pos_z * m32 + 1;
        	//Vector3f v = new Vector3f(pos[3*i],pos[3*i+1],pos[3*i+2]);
    		//v = matrix.transformPosition(v);
    		pos[3*i] = (pos_x * m00 + pos_y * m01 + pos_z * m02)/divisor;
    		pos[3*i+1] = (pos_x * m10 + pos_y * m11 + pos_z * m12)/divisor;
    		pos[3*i+2] = (pos_x * m20 + pos_y * m21 + pos_z * m22)/divisor;
    	}
    	setMesh(pos, textco, norms, inds);
    }
}
