package org.team3309.pid;

public interface PositionJaguar {

	public void absolute(double pos);
	public void add(double delta);
	public void start();
	public void stop();
	public void brake();
	public void setInverted();
	
}
