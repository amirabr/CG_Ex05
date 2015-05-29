package ex5.models;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import ex5.models.Planet.Planets;

public class SolarSystem implements IRenderable {
	
	private Planet[] planets;
	
	private boolean isLights;
	
	public SolarSystem() {
		
		isLights = false;
		initPlanets();
		
	}
	
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
		
	}

	@Override
	public void init(GL gl) {

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
	
	private void renderLights(GL gl, GLU glu, GLUquadric quad) {
		
		// First light (white) on top
		float[] light0Position 	= {0.0f, 5.0f, 0.0f, 1.0f};
		float[] light0Ambient 	= {0.1f, 0.1f, 0.1f};
		float[] light0Diffuse 	= {1.0f, 1.0f, 1.0f};
		float[] light0Specular 	= {0.1f, 0.1f, 0.1f};
		
		// Second light (red) on bottom
		float[] light1Position 	= {0.0f, -5.0f, 0.0f, 1.0f};
//		float[] light1Ambient 	= {0.1f, 0.1f, 0.1f};
//		float[] light1Diffuse 	= {1.0f, 0.0f, 0.0f};
//		float[] light1Specular 	= {0.1f, 0.1f, 0.1f};
		
		// Initialize light0
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, 	light0Position, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, 	light0Ambient, 	0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, 	light0Diffuse, 	0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, 	light0Specular, 0);
		gl.glEnable(GL.GL_LIGHT0);

		// Initialize light1
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, 	light1Position, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, 	light0Ambient, 	0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, 	light0Diffuse, 	0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, 	light0Specular, 0);
		gl.glEnable(GL.GL_LIGHT1);
		
		if (isLights) {
			
			gl.glDisable(GL.GL_LIGHTING);
		
			// Draw light0
			gl.glPushMatrix();
			gl.glTranslated(light0Position[0], light0Position[1], light0Position[2]);
			gl.glColor3fv(light0Diffuse, 0);
			glu.gluSphere(quad, 0.2, 50, 50);
			gl.glPopMatrix();
			
			// Draw light1
			gl.glPushMatrix();
			gl.glTranslated(light1Position[0], light1Position[1], light1Position[2]);
			gl.glColor3fv(light0Diffuse, 0);
			glu.gluSphere(quad, 0.2, 50, 50);
			gl.glPopMatrix();
			
		}

		// Enable lighting
		gl.glEnable(GL.GL_LIGHTING);
		
	}

}
