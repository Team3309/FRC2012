package org.team3309;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

public class XboxController extends GenericHID{

	private Joystick stick;
	
	public XboxController(int num){
		stick = new Joystick(num);
	}

	public double getX(Hand hand) {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getY(Hand hand) {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getZ(Hand hand) {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getTwist() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getThrottle() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getRawAxis(int which) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean getTrigger(Hand hand) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean getTop(Hand hand) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean getBumper(Hand hand) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean getRawButton(int button) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
