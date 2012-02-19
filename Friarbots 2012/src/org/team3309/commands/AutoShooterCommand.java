package org.team3309.commands;

import org.team3309.OI;
import org.team3309.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class AutoShooterCommand extends Command{
	
	private ShooterSubsystem shooter;
	private Joystick stick;
	
	public AutoShooterCommand(){
		shooter = ShooterSubsystem.getInstance();
		requires(shooter);
		stick = OI.getInstance().getJoystick(1);
	}

	protected void initialize() {
	}

	protected void execute() {
		if(shooter.ballInFeeder())
			shooter.elevateBall();
		if(shooter.ballInReloader() && stick.getTrigger())
			shooter.feedBall();
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}

}
