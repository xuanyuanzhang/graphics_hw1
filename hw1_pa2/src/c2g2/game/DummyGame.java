package c2g2.game;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;


import static org.lwjgl.glfw.GLFW.*;

import c2g2.engine.GameItem;
import c2g2.engine.GameEngine;
import c2g2.engine.IGameLogic;
import c2g2.engine.MouseInput;
import c2g2.engine.Window;
import c2g2.engine.graph.Camera;
import c2g2.engine.graph.DirectionalLight;
import c2g2.engine.graph.Material;
import c2g2.engine.graph.Mesh;
import c2g2.engine.graph.OBJLoader;
import c2g2.engine.graph.PointLight;

public class DummyGame implements IGameLogic {

    private static final float MOUSE_SENSITIVITY = 0.0f;
    
    private static final float TRANSLATE_STEP = 0.1f;
    
    private static final float ROTATION_STEP = 0.032f;
    
    //private float rocket_acceleration = 0.1f;
    
    //private double time;

    private final Vector3f cameraInc;

    private final Renderer renderer;

    private final Camera camera;

    private GameItem[] gameItems;

    private Vector3f ambientLight;

    private PointLight pointLight;

    private DirectionalLight directionalLight;

    private float lightAngle;

    private static final float CAMERA_POS_STEP = 0.075f;
    
    private int currentObj;
    
    private RotationLock rotationLock;
    
    private PositionLock positionLock;
    
    private float angle;//angle range from 0 to 360 degrees
    
    private boolean canMoveUp=true;;
    private boolean canMoveDown=false;
    private boolean canMoveLeft=false;
    private boolean canMoveRight=false;
    
    private Grid grid;

    public DummyGame() {
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f(0.0f, 0.0f, 0.0f);
        lightAngle = -90;
        currentObj=0;
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);
        float reflectance = 1f;        
        // NOTE: 
        //   please uncomment following lines to test your OBJ Loader.
        Mesh mesh = OBJLoader.loadMesh("src/resources/models/cube.obj");
//        Mesh mesh = new Mesh();  // comment this line when you enable OBJLoader
        Material material = new Material(new Vector3f(1f, 0f, 0f), reflectance);
        mesh.setMaterial(material);
        GameItem gameItem1 = new GameItem(mesh);
        //gameItem1.setPosition(-2, -2, -2);
        gameItem1.setScale(0.001f);
        gameItem1.setPosition(5, 0, -10);
        //gameItem1.setRotation(15, -15, 0);
        
        //Mesh mesh2 = OBJLoader.loadMesh("src/resources/models/3d-model.obj");
        Mesh mesh2 = OBJLoader.loadMesh("src/resources/models/teapot.obj");
        Material material2 = new Material(new Vector3f(1f, 1f, 1f), reflectance);
        mesh2.setMaterial(material2);
        GameItem gameItem2 = new GameItem(mesh2);
        gameItem2.setScale(5f);
        gameItem2.setPosition(0, 0, 0);
        //gameItems = new GameItem[81];
        //gameItems[0] = gameItem2;
        
        Grid g = new Grid();
        gameItems = g.makeGrid();
        
        ambientLight = new Vector3f(0.2f, 0.6f, 0.6f);
        Vector3f lightColour = new Vector3f(1, 1, 1);
        Vector3f lightPosition = new Vector3f(0, 0, 1);
        float lightIntensity = 0.0f;
        pointLight = new PointLight(lightColour, lightPosition, lightIntensity);
        PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
        pointLight.setAttenuation(att);

        lightPosition = new Vector3f(-1, 0, 0);
        lightColour = new Vector3f(1, 1, 1);
        directionalLight = new DirectionalLight(lightColour, lightPosition, lightIntensity);
        
        rotationLock = new RotationLock();
        positionLock = new PositionLock();
        grid = new Grid();
        
        angle = 0.0f;
        
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {

    	if(window.isKeyPressed(GLFW_KEY_RIGHT)){
    		//rotation by manipulating mesh
    		if(rotationLock.canRotate()&&(!positionLock.isMoving())){
	    		Vector3f loc = camera.getPosition();
	    		float x = loc.x;
	    		float y = loc.y;
	    		float z = loc.z;
		    	camera.setRotation(0, 0, camera.getRotation().z+ROTATION_STEP);
		    	float x_cord = (float) Math.sin(camera.getRotation().z);
		    	float y_cord = (float) Math.cos(camera.getRotation().z);
	    		Matrix4f m = new Matrix4f();
	    		m.rotateZ(ROTATION_STEP);
		    	camera.setTarget(new Vector3f(x_cord+camera.getPosition().x,y_cord+camera.getPosition().y,0));
		    	System.out.println(camera.getTarget().x+" "+camera.getTarget().y);
		    	System.out.println(x+" "+y+" "+z);
	    		System.out.println(camera.getRotation().z);
	    		rotationLock.increment((float)Math.toDegrees(ROTATION_STEP));
	    		angle+=(float)Math.toDegrees(ROTATION_STEP);
	    		angle = (angle+360)%360;
	    		System.out.println("angle is: "+angle);
    		}
    	}
    	else if(window.isKeyPressed(GLFW_KEY_LEFT)){
    		//rotation by manipulating mesh
    		if(rotationLock.canRotate()&&(!positionLock.isMoving())){
	    		Vector3f loc = camera.getPosition();
	    		float x = loc.x;
	    		float y = loc.y;
	    		float z = loc.z;
	    		camera.setRotation(0, 0, camera.getRotation().z-ROTATION_STEP);
	    		float x_cord = (float) Math.sin(camera.getRotation().z);
		    	float y_cord = (float) Math.cos(camera.getRotation().z);
		    	camera.setTarget(new Vector3f(x_cord+camera.getPosition().x,y_cord+camera.getPosition().y,0));
		    	System.out.println(camera.getTarget().x+" "+camera.getTarget().y);
		    	System.out.println(x+" "+y+" "+z);
	    		System.out.println(camera.getRotation().z);
	    		rotationLock.increment((float)-Math.toDegrees(ROTATION_STEP));
	    		angle-=(float)Math.toDegrees(ROTATION_STEP);
	    		angle = (angle+360)%360;
	    		System.out.println("angle is: "+angle);
    		}
    	}
    	else if(window.isKeyPressed(GLFW_KEY_SPACE)){
    		if(!rotationLock.canRotate()){
    			System.out.println("rotation islocked!");
    			angle = Math.round(angle);
    			rotationLock.free();
    		}
    		if(!positionLock.canMove()){
    			System.out.println("position locked");
    			positionLock.free();
    		}
    		if(grid.isAtTarget(camera.getPosition().x, camera.getPosition().y)){
    			for(int i=0;i<gameItems.length;i++){
    				gameItems[i].getMesh().setMaterial(new Material(new Vector3f(1f, 0f, 0f), 1f));
    			}
    		}
    		canMoveUp = grid.canMove(camera.getPosition().x, camera.getPosition().y, "UP");
    		canMoveDown = grid.canMove(camera.getPosition().x, camera.getPosition().y, "DOWN");
    		canMoveRight = grid.canMove(camera.getPosition().x, camera.getPosition().y, "RIGHT");
    		canMoveLeft = grid.canMove(camera.getPosition().x, camera.getPosition().y, "LEFT");
    	}
    	
    	else if(window.isKeyPressed(GLFW_KEY_UP)){
    		//rotation by manipulating mesh
    		if(positionLock.canMove()&&(!rotationLock.isRotating())){
    			if(angle>89 && angle<91 && canMoveRight){
    				camera.setPosition(camera.getPosition().x+TRANSLATE_STEP, camera.getPosition().y, camera.getPosition().z);
    	    		camera.setTarget(new Vector3f(camera.getTarget().x+TRANSLATE_STEP,camera.getTarget().y,camera.getTarget().z));
    	    		System.out.println(camera.getPosition().x);
    	    		System.out.println("camera x target is: "+camera.getTarget().x);
    	    		positionLock.increment(TRANSLATE_STEP);
    			}
    			else if(angle>179 && angle<181 && canMoveDown){
    				camera.setPosition(camera.getPosition().x, camera.getPosition().y-TRANSLATE_STEP, camera.getPosition().z);
    	    		camera.setTarget(new Vector3f(camera.getTarget().x,camera.getTarget().y-TRANSLATE_STEP,camera.getTarget().z));
    	    		System.out.println(camera.getPosition().y);
    	    		System.out.println("camera y target is: "+camera.getTarget().y);
    	    		positionLock.increment(-TRANSLATE_STEP);
    			}
    			else if(angle>269 && angle<271 && canMoveLeft){
    				camera.setPosition(camera.getPosition().x-TRANSLATE_STEP, camera.getPosition().y, camera.getPosition().z);
    	    		camera.setTarget(new Vector3f(camera.getTarget().x-TRANSLATE_STEP,camera.getTarget().y,camera.getTarget().z));
    	    		System.out.println(camera.getPosition().x);
    	    		System.out.println("camera x target is: "+camera.getTarget().x);
    	    		positionLock.increment(-TRANSLATE_STEP);
    			}
    			else if((angle>359 || angle<1) && canMoveUp){
    				camera.setPosition(camera.getPosition().x, camera.getPosition().y+TRANSLATE_STEP, camera.getPosition().z);
    	    		camera.setTarget(new Vector3f(camera.getTarget().x,camera.getTarget().y+TRANSLATE_STEP,camera.getTarget().z));
    	    		System.out.println(camera.getPosition().y);
    	    		System.out.println("camera y target is: "+camera.getTarget().y);
    	    		positionLock.increment(TRANSLATE_STEP);
    			}
    		}
    	}
    	else if(window.isKeyPressed(GLFW_KEY_DOWN)){
    		//rotation by manipulating mesh
    		if(positionLock.canMove()&&(!rotationLock.isRotating())){
    			if(angle>89 && angle<91 && canMoveLeft){
    				camera.setPosition(camera.getPosition().x-TRANSLATE_STEP, camera.getPosition().y, camera.getPosition().z);
    	    		camera.setTarget(new Vector3f(camera.getTarget().x-TRANSLATE_STEP,camera.getTarget().y,camera.getTarget().z));
    	    		System.out.println(camera.getPosition().x);
    	    		System.out.println("camera x target is: "+camera.getTarget().x);
    	    		positionLock.increment(-TRANSLATE_STEP);
    			}
    			else if(angle>179 && angle<181 && canMoveUp){
    				camera.setPosition(camera.getPosition().x, camera.getPosition().y+TRANSLATE_STEP, camera.getPosition().z);
    	    		camera.setTarget(new Vector3f(camera.getTarget().x,camera.getTarget().y+TRANSLATE_STEP,camera.getTarget().z));
    	    		System.out.println(camera.getPosition().y);
    	    		System.out.println("camera y target is: "+camera.getTarget().y);
    	    		positionLock.increment(TRANSLATE_STEP);
    			}
    			else if(angle>269 && angle<271 && canMoveRight){
    				camera.setPosition(camera.getPosition().x+TRANSLATE_STEP, camera.getPosition().y, camera.getPosition().z);
    	    		camera.setTarget(new Vector3f(camera.getTarget().x+TRANSLATE_STEP,camera.getTarget().y,camera.getTarget().z));
    	    		System.out.println(camera.getPosition().x);
    	    		System.out.println("camera x target is: "+camera.getTarget().x);
    	    		positionLock.increment(TRANSLATE_STEP);
    			}
    			else if((angle>359 || angle<1) && canMoveDown){
    				camera.setPosition(camera.getPosition().x, camera.getPosition().y-TRANSLATE_STEP, camera.getPosition().z);
    	    		camera.setTarget(new Vector3f(camera.getTarget().x,camera.getTarget().y-TRANSLATE_STEP,camera.getTarget().z));
    	    		System.out.println(camera.getPosition().y);
    	    		System.out.println("camera y target is: "+camera.getTarget().y);
    	    		positionLock.increment(-TRANSLATE_STEP);
    			}
    		}
    	}
    	
    	
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {
        // Update camera position
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);

        // Update camera based on mouse            
        if (mouseInput.isLeftButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            System.out.println(rotVec);
            Vector3f curr = gameItems[0].getRotation();
            gameItems[0].setRotation(curr.x+ rotVec.x * MOUSE_SENSITIVITY, curr.y+rotVec.y * MOUSE_SENSITIVITY, 0);
        }

        // Update directional light direction, intensity and colour
        lightAngle += 1.1f;
        
        if (lightAngle > 90) {
            directionalLight.setIntensity(0);
            if (lightAngle >= 90) {
                lightAngle = -90;
            }
        } else if (lightAngle <= -80 || lightAngle >= 80) {
            float factor = 1 - (float) (Math.abs(lightAngle) - 80) / 10.0f;
            directionalLight.setIntensity(factor);
            directionalLight.getColor().y = Math.max(factor, 0.9f);
            directionalLight.getColor().z = Math.max(factor, 0.5f);
        } else {
            directionalLight.setIntensity(1);
            directionalLight.getColor().x = 1;
            directionalLight.getColor().y = 1;
            directionalLight.getColor().z = 1;
        }
        double angRad = Math.toRadians(lightAngle);
        directionalLight.getDirection().x = (float) Math.sin(angRad);
        directionalLight.getDirection().y = (float) Math.cos(angRad);
    }

    @Override
    public void render(Window window) {
        renderer.render(window, camera, gameItems, ambientLight, pointLight, directionalLight);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        for (GameItem gameItem : gameItems) {
            gameItem.getMesh().cleanUp();
        }
    }

}
