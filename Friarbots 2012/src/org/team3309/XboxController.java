package org.team3309;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

public class XboxController extends GenericHID{
	
	public static final int BUTTON_START		= 8;
	public static final int BUTTON_BACK			= 7;
	public static final int BUTTON_LEFT_STICK	= 9;
	public static final int BUTTON_RIGHT_STICK	= 10;
	public static final int BUTTON_LEFT_BUMPER	= 5;
	public static final int BUTTON_RIGHT_BUMPER	= 6;
	public static final int BUTTON_X_HOME		= 15;
	public static final int BUTTON_A			= 1;
	public static final int BUTTON_B			= 2;
	public static final int BUTTON_X			= 3;
	public static final int BUTTON_Y			= 4;
	
	public static final int AXIS_LEFT_X			= 1;
	public static final int AXIS_LEFT_Y			= 2;
	public static final int AXIS_LEFT_TRIGGER	= 3;
	public static final int AXIS_RIGHT_TRIGGER	= 4;
	public static final int AXIS_RIGHT_X		= 4;
	public static final int AXIS_RIGHT_Y		= 5;

	private DriverStation mDs;
	private int mPort;
	
	public XboxController(int port){
		mPort = port;
		mDs = DriverStation.getInstance();
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
		if(hand == Hand.kLeft)
			return getRawButton(AXIS_LEFT_TRIGGER);
		if(hand == Hand.kRight)
			return getRawButton(AXIS_RIGHT_TRIGGER);
		else
			return false;
	}
	
	public boolean getRawButton(int button){
		return ((0x1 << (button - 1)) & mDs.getStickButtons(mPort)) != 0;
	}
	
	
}
