package org.team3309.commands;

import org.team3309.OI;
import org.team3309.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ManualShooterCommand extends Command{
	
	Joystick stick;
	Joystick driveStick;
	ShooterSubsystem shooter;
	boolean auto;
	boolean temp1;
	boolean temp2;
	
	public ManualShooterCommand(){
		shooter = ShooterSubsystem.getInstance();
		requires(shooter);
		
		auto = false;
		driveStick = OI.getInstance().getJoystick(1);
		stick = OI.getInstance().getJoystick(2);
		
	}

	protected void initialize() {
		temp1 = shooter.ballAtTop();
		temp2 = shooter.ballInFeeder();
		System.out.println("Top: " + shooter.ballAtTop() + "\t" + "Bottom: " + shooter.ballInFeeder());
	}
	
	protected void execute() {
		
		if(shooter.ballAtTop() != temp1 || shooter.ballInFeeder() != temp2){
			System.out.println("Top: " + shooter.ballAtTop() + "\t" + "Bottom: " + shooter.ballInFeeder());
			temp1 = shooter.ballAtTop();
			temp2 = shooter.ballInFeeder();
		}
		
		//if(shooter.ballInFeeder())
			//shooter.elevateBall();
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}

}
