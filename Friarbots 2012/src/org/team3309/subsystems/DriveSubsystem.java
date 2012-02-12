/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team3309.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.team3309.*;
import org.team3309.commands.JoystickDrive;
/**
 *
 * @author vmagro
 */
public class DriveSubsystem extends Subsystem {

    private static DriveSubsystem instance	= null;
    private static JoystickDrive command 	= new JoystickDrive(1);
    public RobotDrive mDrive 				= null;
    
    //<editor-fold desc="CANJaguars">
    private CANJaguar lFront, lBack, rFront, rBack;
    //put right side negative to correct polarity

    //</editor-fold>
    
    //<editor-fold desc="CAN Initialization variables">
    private static final int MAX_CAN_ATTEMPTS 	= 5;
    private int canInitAttempts 				= 0;
    private boolean canSuccesful 				= false;
    //</editor-fold>
    
    /*
     * Method used to get the singleton instance of this class
     * Follows singleton pattern so as to not have multiple instances of this class and it's corresponding members
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
        initCAN();
        mDrive = new RobotDrive(lFront, lBack, rFront, rBack);
        mDrive.setSafetyEnabled(false);
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
        else canSuccesful = false;
    }
    
    public void mecanumDrive(double x, double y, double twist, double g){
    	mDrive.mecanumDrive_Cartesian(-x, -y, -twist, g);
    }
    
    public void mecanumDrive(double x, double y, double twist){
        mecanumDrive(x, y, twist, 0);
    }
    
	protected void initDefaultCommand() {
		
	}
	
	public void brake(){
		mDrive.stopMotor();
	}
	
	public CANJaguar[] getMotors(){
		return new CANJaguar[]{lFront, lBack, rFront, rBack};
	}
}
