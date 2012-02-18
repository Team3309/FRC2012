package org.team3309.commands;

import org.team3309.subsystems.PneumaticsSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class RetractUbarCommand extends Command{
	
	private PneumaticsSubsystem pneumatics;
	
	private boolean finished = false;
	
	public RetractUbarCommand(){
		pneumatics = PneumaticsSubsystem.getInstance();
		requires(pneumatics);
	}

	protected void initialize() {
		
	}

	protected void execute() {
		pneumatics.retractUbar();
		finished = true;
	}

	protected boolean isFinished() {
		return finished;
	}

	protected void end() {
		
	}

	protected void interrupted() {
		
	}

}
