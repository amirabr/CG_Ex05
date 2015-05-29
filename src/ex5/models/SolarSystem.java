package ex5.models;

import javax.media.opengl.GL;

import ex5.models.Planet.Planets;

public class SolarSystem implements IRenderable {
	
	Planet planet;
	
	public SolarSystem() {
		
	}

	@Override
	public void render(GL gl) {
		
		// Turn on the lights
		renderLights(gl);
		
		// Render the planet
		planet.render(gl);
		
	}

	@Override
	public void init(GL gl) {

		planet = new Planet(Planets.Venus);
		
	}

	@Override
	public void control(int type, Object params) {
		
		// TODO: send command to ALL the planets
		planet.control(IRenderable.TOGGLE_AXES, null);
		
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
		float[] light1Diffuse 	= {1.0f, 0.0f, 0.0f, 1.0f};
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
		
		// Enable lighting
		gl.glEnable(GL.GL_LIGHTING);
		
	}

}
