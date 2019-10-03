/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 * Add your docs here.
 */
public class DriveTrain extends Subsystem {  
  
  private CANSparkMax spark;

  public DriveTrain() {
    }

  public void setCurrentMotor(int devieID) {
    switch(devieID) {
      case RobotMap.DRIVE_FRONT_LEFT:
        spark = new CANSparkMax(RobotMap.DRIVE_FRONT_LEFT, MotorType.kBrushless);      
        break;
      case RobotMap.DRIVE_FRONT_RIGHT:
        spark = new CANSparkMax(RobotMap.DRIVE_FRONT_RIGHT, MotorType.kBrushless); 
        break;
      case RobotMap.DRIVE_BACK_LEFT:
        spark = new CANSparkMax(RobotMap.DRIVE_BACK_LEFT, MotorType.kBrushless);
        break;
      case RobotMap.DRIVE_BACK_RIGHT:
        spark = new CANSparkMax(RobotMap.DRIVE_BACK_RIGHT, MotorType.kBrushless);
        break;
      }
  }

  public void setSpeed(double speed) {
    spark.set(speed);
  }

  public void stop() {
    setSpeed(0.0);
  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new TeleopDrive());
  }
}
