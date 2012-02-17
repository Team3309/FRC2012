/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team3309.subsystems;

import org.team3309.RobotMap;
import org.team3309.commands.JoystickDrive;
import org.team3309.pid.SpeedJaguar;
import org.team3309.subsystems.SpeedDrive.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 * @author vmagro
 */
public class DriveSubsystem extends Subsystem {

	private static DriveSubsystem instance = null;
	private static JoystickDrive command = new JoystickDrive(1);
	public SpeedDrive mDrive = null;

	private SpeedJaguar lFront, lBack, rFront, rBack;

	// put right side negative to correct polarity

	/*
	 * Method used to get the singleton instance of this class Follows singleton
	 * pattern so as to not have multiple instances of this class and it's
	 * corresponding members
	 * 
	 * @return
	 */
	public static DriveSubsystem getInstance() {
		if (instance == null) {
			instance = new DriveSubsystem();
			// Set default command here, like this:
			// instance.setDefaultCommand(new CommandIWantToRun());
			instance.setDefaultCommand(command);
		}
		return instance;
	}

	/*
	 * Initializes the subsystem
	 */
	private DriveSubsystem() {
		lFront = new SpeedJaguar(RobotMap.JAG_FRONT_LEFT,
				RobotMap.ENCODER_FRONT_LEFT_A, RobotMap.ENCODER_FRONT_LEFT_B);
		lBack = new SpeedJaguar(RobotMap.JAG_BACK_LEFT,
				RobotMap.ENCODER_BACK_LEFT_A, RobotMap.ENCODER_BACK_LEFT_B);
		rFront = new SpeedJaguar(RobotMap.JAG_FRONT_RIGHT,
				RobotMap.ENCODER_FRONT_RIGHT_A, RobotMap.ENCODER_FRONT_RIGHT_B);
		rBack = new SpeedJaguar(RobotMap.JAG_BACK_RIGHT,
				RobotMap.ENCODER_BACK_RIGHT_A, RobotMap.ENCODER_BACK_RIGHT_B);
		mDrive = new SpeedDrive(lFront, lBack, rFront, rBack);
		mDrive.setSafetyEnabled(false);
		// set the left side motors to inverted to keep correct wiring polarity
		mDrive.setInvertedMotor(MotorType.kFrontLeft, true);
		mDrive.setInvertedMotor(MotorType.kRearLeft, true);
	}

	public void mecanumDrive(double x, double y, double twist, double g) {
		mDrive.mecanumDrive_Cartesian(-x, -y, -twist, g);
	}

	public void mecanumDrive(double x, double y, double twist) {
		mecanumDrive(x, y, twist, 0);
	}

	protected void initDefaultCommand() {

	}

	public void brake() {
		mDrive.stopMotor();
	}
}
