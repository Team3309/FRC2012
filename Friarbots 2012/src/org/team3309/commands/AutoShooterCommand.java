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
	private boolean temp1;
	private boolean temp2;
	
	public AutoShooterCommand(){
		autoTargetting 	= true;
		vision = VisionSubsystem.getInstance();
		shooter = ShooterSubsystem.getInstance();
		requires(shooter);
		requires(elevator);
		shootStick = OI.getInstance().getJoystick(2);
	}

	protected void initialize() {}

	protected void execute() {

	}

	protected boolean isFinished(){return false;}
	protected void end(){}
	protected void interrupted(){}
}