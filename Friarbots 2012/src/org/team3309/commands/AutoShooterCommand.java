package org.team3309.commands;

import org.team3309.OI;
import org.team3309.subsystems.ShooterSubsystem;
import org.team3309.subsystems.VisionSubsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

public class AutoShooterCommand extends Command{

	private ShooterSubsystem shooter;
	private VisionSubsystem vision;

	public AutoShooterCommand(){
		vision = VisionSubsystem.getInstance();
		shooter = ShooterSubsystem.getInstance();
		requires(shooter);
		new JoystickButton(OI.getInstance().getJoystick(2), 1).whenPressed(ButtonCommands.shoot);
	}

	protected void initialize() {}

	protected void execute() {
		shooter.rotateTurret(vision.getOffAngle());
	}

	protected boolean isFinished(){return false;}
	protected void end(){}
	protected void interrupted(){}
}