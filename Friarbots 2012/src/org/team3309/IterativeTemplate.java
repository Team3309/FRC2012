/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3309;

import org.team3309.commands.ButtonCommands;
import org.team3309.commands.JoystickDrive;
import org.team3309.properties.Properties;
import org.team3309.subsystems.DriveSubsystem;
import org.team3309.subsystems.ElevatorSubsystem;
import org.team3309.subsystems.Gyro;
import org.team3309.subsystems.PneumaticsSubsystem;
import org.team3309.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class IterativeTemplate extends IterativeRobot {

	Joystick stick;
	Joystick shooterStick;

	//Autonomous Stuff
	EZ0 uSonic;
	AnalogChannel jumperHigh, jumperMiddle, jumperDisableAuto;
	private final static double DISTANCE_FROM_BOARD = 21; //21 for top basket
	private static final double SHOOT_VOLTAGE_MIDDLE= 6;
	private static final double SHOOT_VOLTAGE_TOP	= 7.3;

	// Declare Subsystems
	DriveSubsystem drive;
	PneumaticsSubsystem pneumatics;
	ShooterSubsystem shooter;


	// Declare Commands
	Command autonomousCommand;

	JoystickDrive driveCommand;

	// Declare Buttons
	JoystickButton deployUbarButton;
	JoystickButton retractUbarButton;
	JoystickButton autoElevateButton;
	JoystickButton manualElevateButton;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {

		// instantiate the command used for the autonomous period

		// create the instance of the operator interface class
		// this will associate all the buttons with the appropriate commands
		OI.getInstance();
		Properties.getInstance();
		PneumaticsSubsystem.getInstance();


		// initialize all subsystems here.
		drive = DriveSubsystem.getInstance();
		shooter = ShooterSubsystem.getInstance();
		stick = OI.getInstance().getJoystick(1);
		shooterStick = OI.getInstance().getJoystick(2);

		// initialize commands
		driveCommand = new JoystickDrive(1);

		//Buttons for joystick
		//deployUbarButton = new JoystickButton(stick, 6);
		//retractUbarButton = new JoystickButton(stick, 4);	
		deployUbarButton = new JoystickButton(stick, XboxMap.B_X);
		retractUbarButton = new JoystickButton(stick, XboxMap.B_B);	
		
		autoElevateButton = new JoystickButton(shooterStick, 8);
		manualElevateButton = new JoystickButton(shooterStick, 9);

		uSonic = new EZ0(RobotMap.ANALOGINPUT_ULTRASONIC);
		jumperHigh = new AnalogChannel(RobotMap.ANALOGINPUT_JUMPER_HIGH);
		jumperMiddle = new AnalogChannel(RobotMap.ANALOGINPUT_JUMPER_MIDDLE);
		jumperDisableAuto = new AnalogChannel(RobotMap.ANALOGINPUT_JUMPER_DISABLE_AUTO);
	}

	public void disabledInit() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		Gyro.getInstance(1, RobotMap.DRIVE_GYRO).reset();
		//PneumaticsSubsystem.getInstance().retractUbar();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		if(jumperDisableAuto.getVoltage() > 4)//disable autonomous
			return;

		if(jumperHigh.getVoltage() > 4){ //shooting in top
			drive.mecanumDrive(0, .65, 0);
			shooter.setPercentVbus(SHOOT_VOLTAGE_TOP);
			Timer.delay(10);
			ElevatorSubsystem.getInstance().manualElevate(1);
		}
		System.out.println(uSonic.getInches());

	}

	public void teleopInit() {		
		System.out.println("In teleop Init");

		driveCommand.start();
		System.out.println("Started Drive");

		deployUbarButton.whenPressed(ButtonCommands.deployUbar);
		retractUbarButton.whenPressed(ButtonCommands.retractUbar);	
		System.out.println("Started Ubar");

		ButtonCommands.manualTurret.start();
		System.out.println("Started Manual Turret");

		//ButtonCommands.autoElevate.start();
		ButtonCommands.manualElevate.start();
		//System.out.println("Started Automatic Elevation");
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic(){
		Scheduler.getInstance().run();
		//System.out.println("teleopPeriodic");

		SmartDashboard.putDouble("CurrentVoltage", shooter.getVoltage());

		//ShooterSubsystem.getInstance().setRPM(SmartDashboard.getDouble("RPM",0));
		//System.out.println(ShooterSubsystem.getInstance().getRPM());

		double voltage = SHOOT_VOLTAGE_TOP;
		if(jumperMiddle.getVoltage() > 4)
			voltage = SHOOT_VOLTAGE_MIDDLE;
		if(shooterStick.getTrigger()){
			shooter.setVoltage(voltage);
			System.out.println("Shooter trigger on");
		}
		else if(shooterStick.getRawButton(2)){
			shooter.setVoltage(voltage + -shooterStick.getY());
			System.out.println("Shooter = "+voltage + -shooterStick.getY());
		}
		else
			shooter.setVoltage(0);
		SmartDashboard.putDouble("Elevator Position", ElevatorSubsystem.getInstance().getPosition());
	}
}
