package org.team3309.commands;

import org.team3309.subsystems.PneumaticsSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class RetractUbarCommand extends Command{
	
	private PneumaticsSubsystem pneumatics;
	
	public RetractUbarCommand(){
		pneumatics = PneumaticsSubsystem.getInstance();
		requires(pneumatics);
	}

	protected void initialize() {
		
	}

	protected void execute() {
		pneumatics.retractUbar();
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {
		
	}

	protected void interrupted() {
		
	}

}
