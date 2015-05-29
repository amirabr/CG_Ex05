package ex5.models;

import javax.media.opengl.GL;

import ex5.models.Planet.Planets;

public class SolarSystem implements IRenderable {
	
	Planet planet;

	@Override
	public void render(GL gl) {
		
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

}
