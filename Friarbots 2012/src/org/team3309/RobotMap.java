package org.team3309;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static final int leftMotor = 1;
	// public static final int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static final int rangefinderPort = 1;
	// public static final int rangefinderModule = 1;

	public static final int JAG_BACK_RIGHT = 2;
	public static final int JAG_FRONT_RIGHT = 3;
	public static final int JAG_FRONT_LEFT = 4;
	public static final int JAG_BACK_LEFT = 5;

	public static final int ENCODER_BACK_LEFT_A = 1;
	public static final int ENCODER_BACK_LEFT_B = 2;
	public static final int ENCODER_BACK_RIGHT_A = 7;
	public static final int ENCODER_BACK_RIGHT_B = 8;
	public static final int ENCODER_FRONT_LEFT_A = 3;
	public static final int ENCODER_FRONT_LEFT_B = 4;
	public static final int ENCODER_FRONT_RIGHT_A = 5;
	public static final int ENCODER_FRONT_RIGHT_B = 6;

	public static final int PNEUMATIC_COMPRESSOR_RELAY = 1;
	public static final int PNEUMATIC_PRESSURE_SWITCH = 9;
	public static final int PNEUMATIC_SOLENOID_FORWARD = 1;
	public static final int PNEUMATIC_SOLENOID_REVERSE = 2;
}
