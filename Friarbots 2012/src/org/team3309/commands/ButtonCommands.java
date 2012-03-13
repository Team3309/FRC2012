package org.team3309.commands;

import org.team3309.OI;
import org.team3309.subsystems.ElevatorSubsystem;
import org.team3309.subsystems.PneumaticsSubsystem;
import org.team3309.subsystems.ShooterSubsystem;
import org.team3309.subsystems.VisionSubsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ButtonCommands {
	//public static Command autoTurret	= new AutoTurretCommand();
	public static Command manualTurret 	= new ManualTurretCommand();
	public static Command deployUbar	= new DeployUbarCommand();
	public static Command retractUbar	= new RetractUbarCommand();
	//public static Command autoShooter	= new AutoShooterCommand();*/
	public static Command autoElevate	= new AutoElevateCommand();
}

class AutoTurretCommand extends Command{
	ShooterSubsystem rotator;
	VisionSubsystem	vision;
	
	public AutoTurretCommand(){
		rotator = ShooterSubsystem.getInstance();
		//vision 	= VisionSubsystem.getInstance();
	}
	
	protected void end(){}
	protected void execute(){
		rotator.rotateTurret(vision.getOffAngle());
	}
	protected void initialize(){}
	protected void interrupted(){}
	protected boolean isFinished(){return false;}
}

class ManualTurretCommand extends Command{
	
	private ShooterSubsystem shooter;
	private Joystick shootStick;
	
	public ManualTurretCommand(){
		shooter = ShooterSubsystem.getInstance();
		shootStick = OI.getInstance().getJoystick(2);
	}
	protected void end(){}
	protected void execute() {
		if(shootStick.getRawButton(4)){
			shooter.rotateTurret(1);
			System.out.println("Button 4");
		}
		if(shootStick.getRawButton(3)){
			shooter.setTurretAngle(0);
			System.out.println("Button 3");
		}
		if(shootStick.getRawButton(5)){
			shooter.rotateTurret(-1);
			System.out.println("Button 5");
		}
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	protected void initialize(){}
	protected void interrupted(){}
	protected boolean isFinished(){return false;}	
}

class DeployUbarCommand extends Command{
	
	private PneumaticsSubsystem pneumatics;
	private boolean finished = false;
	
	public DeployUbarCommand(){
		pneumatics = PneumaticsSubsystem.getInstance();
		requires(pneumatics);
	}
	protected void initialize(){}
	protected void execute() {
		pneumatics.deployUbar();
		finished = true;
	}
	protected boolean isFinished() {
		return finished;
	}
	protected void end(){}
	protected void interrupted(){}
}

class RetractUbarCommand extends Command{
	
	private PneumaticsSubsystem pneumatics;
	private boolean finished = false;
	
	public RetractUbarCommand(){
		pneumatics = PneumaticsSubsystem.getInstance();
		requires(pneumatics);
	}
	protected void initialize(){}
	protected void execute() {
		pneumatics.retractUbar();
		finished = true;
	}
	protected boolean isFinished() {return finished;}
	protected void end(){}
	protected void interrupted(){}
}

class AutoElevateCommand extends Command{

	private ElevatorSubsystem elevator;
	
	public AutoElevateCommand(){
		elevator = ElevatorSubsystem.getInstance();
	}
	protected void end() {}
	protected void execute() {
		if(elevator.ballAtBot())
			elevator.elevateBall();
		else if(elevator.ballAtTop())
			elevator.brake();
		else
			elevator.brake();
		
	}
	protected void initialize(){}
	protected void interrupted(){}
	protected boolean isFinished() {return false;}	
}
class ManualElevateCommand extends Command{
	private Joystick shootStick;
	private ElevatorSubsystem elevator;
	
	protected void end(){}
	protected void initialize(){}
	protected void interrupted(){}
	protected boolean isFinished(){return false;}
	
	public ManualElevateCommand(){
		elevator 	= ElevatorSubsystem.getInstance();
		shootStick 	= OI.getInstance().getJoystick(2);
		requires(elevator);
	}
	protected void execute(){
		if(shootStick.getRawButton(6))
			ElevatorSubsystem.getInstance().manualElevate(1);
		else if(shootStick.getRawButton(7))
			ElevatorSubsystem.getInstance().manualElevate(-1);
		else
			ElevatorSubsystem.getInstance().manualElevate(0);
	}	
}
class ShootCommand extends Command{
	ShooterSubsystem shooter = null;
	
	protected void end(){}
	protected void initialize(){}
	protected void interrupted(){}
	protected boolean isFinished(){return false;}
	
	public void ShooterSubsystem(){
		shooter = ShooterSubsystem.getInstance();
		requires(shooter);
	}
	
	protected void execute(){
		shooter.shootBall();
	}
}

class PrepShootCommand extends Command{
	ElevatorSubsystem elevator= null;
	
	protected void end(){}
	protected void initialize(){}
	protected void interrupted(){}
	protected boolean isFinished(){
		return elevator.ballAtTop();
	}
	
	public void ShooterSubsystem(){
		elevator = ElevatorSubsystem.getInstance();
		requires(elevator);
	}
	
	protected void execute(){
		elevator.elevateBall();
	}
}

