package ex5.models;

import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.sun.opengl.util.GLUT;

import ex5.models.Planet.Planets;

public class SolarSystem implements IRenderable {
	
	private Planet[] planets; 		// Planets array
	private boolean isLights; 		// Show light spheres?
	private boolean isBonus;
	
	/**
	 * Constructor.
	 */
	public SolarSystem() {
		
		isLights = false;
		isBonus = false;
		initPlanets();
		
	}
	
	/**
	 * Populate the planets array with actual planets.
	 */
	private void initPlanets() {
		
		planets = new Planet[10];
		planets[0] = new Planet(Planets.Sun);
		planets[1] = new Planet(Planets.Mercury);
		planets[2] = new Planet(Planets.Venus);
		planets[3] = new Planet(Planets.Earth);
		planets[4] = new Planet(Planets.Mars);
		planets[5] = new Planet(Planets.Jupiter);
		planets[6] = new Planet(Planets.Saturn);
		planets[7] = new Planet(Planets.Uranus);
		planets[8] = new Planet(Planets.Neptune);
		planets[9] = new Planet(Planets.Pluto);
		
	}

	@Override
	public void render(GL gl) {
		
		GLU glu = new GLU();
		GLUquadric quad = glu.gluNewQuadric();
		
		// Render the lights
		renderLights(gl, glu, quad);
		
		// Render the planets
		for (Planet p : planets) {
			p.render(gl);
		}
		
		if (isBonus) {
			mazalTov(gl);
		}
		
	}

	@Override
	public void init(GL gl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void control(int type, Object params) {
			
		// Which command was given?
		switch (type) {
		
    		case IRenderable.TOGGLE_LIGHT_SPHERES: 
    			isLights = !isLights;
    			break;
    			
    		case IRenderable.TOGGLE_AXES:
    			for (Planet p : planets) {
    				p.control(IRenderable.TOGGLE_AXES, null);
    			}
    			break;
    			
    		case IRenderable.TOGGLE_BONUS:
    			isBonus = !isBonus;
    			break;
    		
		}
		
	}

	@Override
	public boolean isAnimated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCamera(GL gl) {
		// TODO Auto-generated method stub
		
	}
	
	public String toString() {
		return "Solar System";
	}
	
	/**
	 * Render the lights in the scene, and add light spheres if requested.
	 * @param gl
	 * @param glu
	 * @param quad
	 */
	private void renderLights(GL gl, GLU glu, GLUquadric quad) {
		
		// Light positions
		float[] light0Position 	= {+10.0f, +10.0f, +10.0f, 1.0f};
		float[] light1Position 	= {-10.0f, -10.0f, -10.0f, 1.0f};
		
		// Light properties
		float[] lightAmbient 	= {0.0f, 0.0f, 0.0f, 1.0f};
		float[] lightDiffuse 	= {1.0f, 1.0f, 1.0f, 1.0f};
		float[] lightSpecular 	= {1.0f, 1.0f, 1.0f, 1.0f};
		
		// Initialize light0
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, 	light0Position, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, 	lightAmbient, 	0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, 	lightDiffuse, 	0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, 	lightSpecular,  0);
		
		// Initialize light1
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, 	light1Position, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, 	lightAmbient, 	0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, 	lightDiffuse, 	0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, 	lightSpecular,  0);
		
		// Show light spheres?
		if (isLights) {
			
			gl.glDisable(GL.GL_LIGHTING);
		
			// Draw light0
			gl.glPushMatrix();
			gl.glTranslated(light0Position[0], light0Position[1], light0Position[2]);
			gl.glColor3fv(lightDiffuse, 0);
			glu.gluSphere(quad, 0.2, 50, 50);
			gl.glPopMatrix();
			
			// Draw light1
			gl.glPushMatrix();
			gl.glTranslated(light1Position[0], light1Position[1], light1Position[2]);
			gl.glColor3fv(lightDiffuse, 0);
			glu.gluSphere(quad, 0.2, 50, 50);
			gl.glPopMatrix();
			
		}

		// Enable lighting
		gl.glEnable(GL.GL_LIGHT0);
		gl.glEnable(GL.GL_LIGHT1);
		gl.glEnable(GL.GL_LIGHTING);
		
	}
	
	/**
	 * This method does absolutely nothing.... maybe.....
	 * @param gl
	 */
	private void mazalTov(GL gl) {
		
		GLUT glut = new GLUT();
		Random rand = new Random();
		
		gl.glDisable(GL.GL_DEPTH_TEST); 
		gl.glDisable(GL.GL_LIGHTING);
		
		for (int i=0; i<100; i++) {
			gl.glColor3d(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
			gl.glWindowPos2d(rand.nextInt(601) - 100, rand.nextInt(601) - 100);
			glut.glutBitmapString(rand.nextInt(5) + 4, "MAZAL TOV ALMOG :-)");
		}
		
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_DEPTH_TEST);
		
	}
	
}
