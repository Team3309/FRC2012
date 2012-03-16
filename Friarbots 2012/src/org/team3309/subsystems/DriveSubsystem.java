/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team3309.subsystems;

import org.team3309.RobotMap;
import org.team3309.commands.JoystickDrive;

import com.sun.squawk.util.MathUtils;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 * @author vmagro
 */
public class DriveSubsystem extends Subsystem {

	private static DriveSubsystem instance = null;
	private static JoystickDrive command = new JoystickDrive(1);
	//public SpeedDrive mDrive = null;
	public RobotDrive mDrive = null;
	
	private CANJaguar lFront, lBack, rFront, rBack;
	//private SpeedJaguar lFront, lBack, rFront, rBack;
	
	private static final int MAX_CAN_ATTEMPTS 	= 5;
    private int canInitAttempts 				= 0;

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
		/*lFront = new SpeedJaguar(RobotMap.JAG_FRONT_LEFT,
				RobotMap.ENCODER_FRONT_LEFT_A, RobotMap.ENCODER_FRONT_LEFT_B);
		lBack = new SpeedJaguar(RobotMap.JAG_BACK_LEFT,
				RobotMap.ENCODER_BACK_LEFT_A, RobotMap.ENCODER_BACK_LEFT_B);
		rFront = new SpeedJaguar(RobotMap.JAG_FRONT_RIGHT,
				RobotMap.ENCODER_FRONT_RIGHT_A, RobotMap.ENCODER_FRONT_RIGHT_B);
		rBack = new SpeedJaguar(RobotMap.JAG_BACK_RIGHT,
				RobotMap.ENCODER_BACK_RIGHT_A, RobotMap.ENCODER_BACK_RIGHT_B);
		mDrive = new SpeedDrive(lFront, lBack, rFront, rBack);*/
		initCAN();
		mDrive = new RobotDrive(lFront, lBack, rFront, rBack);
		mDrive.setSafetyEnabled(false);
		// set the left side motors to inverted to keep correct wiring polarity
		mDrive.setInvertedMotor(MotorType.kFrontLeft, true);
		mDrive.setInvertedMotor(MotorType.kRearLeft, true);
	}
	
	/**
     * Attempts to initialize the CANJaguars
     * Tries MAX_CAN_ATTEMPTS to initialize the Jaguars in case of CANTimeoutException being thrown
     * This method recalls itself up to MAX_CAN_ATTEMPTS to attempt to correct temporary CAN timeouts that may occur when the robot boots
     */
    private void initCAN(){
        if(canInitAttempts < MAX_CAN_ATTEMPTS)
            try {
                lFront  = new CANJaguar(RobotMap.JAG_FRONT_LEFT);
                lBack   = new CANJaguar(RobotMap.JAG_BACK_LEFT);
                rFront  = new CANJaguar(RobotMap.JAG_FRONT_RIGHT);
                rBack   = new CANJaguar(RobotMap.JAG_BACK_RIGHT);
                
                lFront.configNeutralMode(CANJaguar.NeutralMode.kBrake);
        		lBack.configNeutralMode(CANJaguar.NeutralMode.kBrake);
        		rFront.configNeutralMode(CANJaguar.NeutralMode.kBrake);
        		rBack.configNeutralMode(CANJaguar.NeutralMode.kBrake);
        		
                canInitAttempts++;
            } catch (CANTimeoutException ctex) {
                ctex.printStackTrace();
                initCAN();
            }
    }

	public void mecanumDrive(double x, double y, double twist, double g) {
		mDrive.mecanumDrive_Cartesian(MathUtils.pow(x,3), MathUtils.pow(y, 3), MathUtils.pow(-twist,3), g);
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
