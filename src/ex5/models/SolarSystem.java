package ex5.models;

import javax.media.opengl.GL;

import ex5.models.Planet.Planets;

public class SolarSystem implements IRenderable {
	
	private Planet[] planets;
	
	private boolean isLights;
	
	public SolarSystem() {
		
		initPlanets();
		
	}
	
	private void initPlanets() {
		
		planets = new Planet[11];
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
		planets[10] = new Planet(Planets.Moon);
		
	}

	@Override
	public void render(GL gl) {
		
		// Setup the lights
		if (isLights) {
			renderLights(gl);
		} else {
			gl.glDisable(GL.GL_LIGHTING);
		}
		
		// Render the planets
		for (Planet p : planets) {
			p.render(gl);
		}
		
	}

	@Override
	public void init(GL gl) {

		isLights = true;
		
	}

	@Override
	public void control(int type, Object params) {
			
		// Which command was given?
		switch (type) {
		
    		case IRenderable.TOGGLE_LIGHTS: 
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
	
	private void renderLights(GL gl) {
		
		// First light (white) on top
		float[] light0Position 	= {0.0f, 5.0f, 0.0f, 1.0f};
		float[] light0Ambient 	= {0.1f, 0.1f, 0.1f, 1.0f};
		float[] light0Diffuse 	= {1.0f, 1.0f, 1.0f, 1.0f};
		float[] light0Specular 	= {0.1f, 0.1f, 0.1f, 1.0f};
		
		// Second light (red) on bottom
		float[] light1Position 	= {0.0f, -5.0f, 0.0f, 1.0f};
		float[] light1Ambient 	= {0.1f, 0.1f, 0.1f, 1.0f};
		float[] light1Diffuse 	= {1.0f, 1.0f, 1.0f, 1.0f};
		float[] light1Specular 	= {0.1f, 0.1f, 0.1f, 1.0f};
		
		// Initialize light0
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, 	light0Position, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, 	light0Ambient, 	0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, 	light0Diffuse, 	0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, 	light0Specular, 0);
		gl.glEnable(GL.GL_LIGHT0);

		// Initialize light1
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, 	light1Position, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, 	light1Ambient, 	0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, 	light1Diffuse, 	0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, 	light1Specular, 0);
		gl.glEnable(GL.GL_LIGHT1);
		
		// Draw light0
		
		// Draw light1
		
		// Enable lighting
		gl.glEnable(GL.GL_LIGHTING);
		
	}

}
