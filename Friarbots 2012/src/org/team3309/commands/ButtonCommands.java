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
	/*public static Command deployUbar	= new DeployUbarCommand();
	public static Command retractUbar	= new RetractUbarCommand();
	public static Command autoShooter	= new AutoShooterCommand();*/
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
			shooter.setTurretAngle(135);
			System.out.println("Button 4");
		}
		if(shootStick.getRawButton(3)){
			shooter.setTurretAngle(0);
			System.out.println("Button 3");
		}
		if(shootStick.getRawButton(5)){
			shooter.setTurretAngle(-135);
			System.out.println("Button 5");
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
	private boolean temp1 = false;
	private boolean temp2 = false;
	
	public AutoElevateCommand(){
		elevator = ElevatorSubsystem.getInstance();
		requires(elevator);
	}
	protected void end() {}
	protected void execute() {
		//Just info stuff
		if(elevator.ballAtTop() != temp1 || elevator.ballInFeeder() != temp2){
			System.out.println("Top: " + elevator.ballAtTop() + "\t" + "Bottom: " + elevator.ballInFeeder());
			temp1 = elevator.ballAtTop();
			temp2 = elevator.ballInFeeder();
		}
		//Actual execution of stuff
		if(elevator.ballInFeeder())
			elevator.elevateBall();
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
		elevator.manualElevate(shootStick.getY());
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
