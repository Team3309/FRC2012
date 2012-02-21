package org.team3309.commands;

import org.team3309.OI;
import org.team3309.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ManualShooterCommand extends Command{
	
	Joystick stick;
	ShooterSubsystem shooter;
	
	public ManualShooterCommand(){
		shooter = ShooterSubsystem.getInstance();
		requires(shooter);
		
		stick = OI.getInstance().getJoystick(2);
	}

	protected void initialize() {
		
	}
	
	protected void execute() {
		shooter.manualElevate(stick.getY());
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}

}
