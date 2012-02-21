package org.team3309.commands;

import org.team3309.OI;
import org.team3309.subsystems.ShooterSubsystem;
import org.team3309.subsystems.VisionSubsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class AutoShooterCommand extends Command{

	private ShooterSubsystem shooter;
	private Joystick shootStick;
	private VisionSubsystem vision;

	public static double ANGLE_TOLERANCE = 3;
	
	private boolean autoTargetting;
	private boolean autoElevator;

	public AutoShooterCommand(){
		autoTargetting 	= true;
		autoElevator	= true;
		
		shooter = ShooterSubsystem.getInstance();
		requires(shooter);
		
		shootStick = OI.getInstance().getJoystick(2);
		
		vision = VisionSubsystem.getInstance();
	}

	protected void initialize() {
	}

	/*
	 * Elevate Algorithm
	 * 
	 * if the target is in sight and there is a ball at the top
	 * 		shoot the ball
	 * 
	 * else, if there is a ball in the feeder
	 * 		elevate the ball to the top
	 */
	protected void execute() {
		
		//Code for Elevator
				
		if(shooter.ballAtTop() && vision.canShoot() && shootStick.getTrigger())
			shooter.shootBall(); 
		
		else if(autoElevator && shooter.getBallInFeeder())
			shooter.elevateBall();
		
		//Manual Override which overthrows the topSensor break... can manually put it into shooter
		else if(!autoElevator)
			shooter.manualElevate(shootStick.getY());
			
		/*
		 * Auto Targeting
		 * Turns 5 degrees either way when off
		 * 
		 * .... Probably doesn't work.....
		 */
		if(autoTargetting){	
			if(vision.getOffAngle() < -ANGLE_TOLERANCE)
				shooter.rotateTurret(5);
			else if(Math.abs(vision.getOffAngle()) > ANGLE_TOLERANCE)
				shooter.rotateTurret(-5);
		}
		//Manual Turret Override
		else{
			shooter.rotateTurret(shootStick.getX());
		}	
	
		/*
		 * Button 2 turns on auto targeting
		 * Button 3 turns off auto targeting
		 * Button 8 turns on auto Elevator
		 * Button 9 turns off auto Elevator
		 */
		if(shootStick.getRawButton(2))
			autoTargetting = true;
		if(shootStick.getRawButton(3))
			autoTargetting = false;
		if(shootStick.getRawButton(8))
			autoElevator = true;
		if(shootStick.getRawButton(9))
			autoElevator = false;
	}

	protected boolean isFinished() {
		return false;
	}
	protected void end() {
	}
	protected void interrupted() {
	}
}