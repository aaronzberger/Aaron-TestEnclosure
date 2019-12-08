/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetOffset extends Command {

  private int beginningPosition;
  private int deviceID;
  private int change;

  public SetOffset() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.azimuth);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.teleopDrive.setSettingOffset(true);
    beginningPosition = Robot.azimuth.getCurrentPosition();
    deviceID = Robot.azimuth.getCurrentMotor();
    Robot.azimuth.setToCoastMode();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    change = (Robot.azimuth.getCurrentPosition() - beginningPosition);
    Robot.azimuth.addToOffset(deviceID, change);
    Robot.azimuth.setToBrakeMode();
    Robot.teleopDrive.setSettingOffset(false);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
