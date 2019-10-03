/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class AZIMUTH extends Subsystem {

  private TalonSRX talon;

  //offsets for each azimuth wheel (absolute positions where all wheels are pointed forward)
	private int AZ_FRONT_LEFT_OFFSET = 2297;
	private int AZ_FRONT_RIGHT_OFFSET = 2283;
	private int AZ_BACK_LEFT_OFFSET = 2172;
	private int AZ_BACK_RIGHT_OFFSET = 2412;

  public AZIMUTH() {
  }

  public void addToOffset(int deviceID, int addition) {
    switch(deviceID) {
      case RobotMap.AZ_FRONT_LEFT:
        AZ_FRONT_LEFT_OFFSET += addition;
        setupOffset(talon);
        break;
      case RobotMap.AZ_FRONT_RIGHT:
        AZ_FRONT_RIGHT_OFFSET += addition;
        setupOffset(talon);
        break;
      case RobotMap.AZ_BACK_LEFT:
        AZ_BACK_LEFT_OFFSET += addition;
        setupOffset(talon);
        break;
      case RobotMap.AZ_BACK_RIGHT:
        AZ_BACK_RIGHT_OFFSET += addition;
        setupOffset(talon);
        break;
      }
  }

  public void setCurrentMotor(int deviceID) {
    switch(deviceID) {
      case RobotMap.AZ_FRONT_LEFT:
        talon = new TalonSRX(RobotMap.AZ_FRONT_LEFT);      
        break;
      case RobotMap.AZ_FRONT_RIGHT:
        talon = new TalonSRX(RobotMap.AZ_FRONT_RIGHT);      
        break;
      case RobotMap.AZ_BACK_LEFT:
        talon = new TalonSRX(RobotMap.AZ_BACK_LEFT);      
        break;
      case RobotMap.AZ_BACK_RIGHT:
        talon = new TalonSRX(RobotMap.AZ_BACK_RIGHT);      
        break;
      }
      setupTalon(talon);
  }

  public void setupOffsets() {
    setupOffset(talon);
  }

  public void setWheelsToDegree(double goal) {
    setWheelToDegree(talon, goal);
  }

  private void setWheelToDegree(TalonSRX motor, double goalPosition) {
    double currentRelativePosition = convertTicksToDegrees(motor.getSelectedSensorPosition());
    double currentAbsolutePosition = ((currentRelativePosition % 360.0) + 360.0) % 360.0;
    double goalMove = goalPosition - currentAbsolutePosition;
    if(goalMove < -180) {
      goalMove += 360;
    }
    if(goalMove > 180) {
      goalMove -= 360;
    }
    motor.set(ControlMode.Position, convertDegreesToTicks(goalMove) + convertDegreesToTicks(currentRelativePosition));
  }

  private double convertDegreesToTicks(double degreeValue) {
    return (degreeValue * (1024.0/90.0));
  }
  private double convertTicksToDegrees(double ticks) {
    return (ticks / (1024.0/90.0));
  }

  private void setupTalon(TalonSRX talon) {
    //talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0 , 10);
    talon.setSensorPhase(false);
    talon.config_kP(0, 3);
    talon.config_kI(0, 0);
    talon.config_kD(0, 30);
    talon.config_kF(0, 0);
    talon.setInverted(true);
    talon.setNeutralMode(NeutralMode.Brake);
    System.out.println("in setupTalon method");
  }

  private void setupOffset(TalonSRX talon) {
    talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0 , 10);
    int currentEncoderValue = talon.getSelectedSensorPosition();
    int currentActualPosition = -10;
    switch(talon.getDeviceID()) {
      case RobotMap.AZ_FRONT_LEFT:
        currentActualPosition = currentEncoderValue + AZ_FRONT_LEFT_OFFSET;
        System.out.println("front left (AA) actual pos: " + currentActualPosition + "\nfront left encoder val: " + currentEncoderValue);
        break;
      case RobotMap.AZ_FRONT_RIGHT:
        currentActualPosition = currentEncoderValue + AZ_FRONT_RIGHT_OFFSET;
        System.out.println("front right (DA) actual pos: " + currentActualPosition + "\nfront right encoder val: " + currentEncoderValue);
        break;
      case RobotMap.AZ_BACK_LEFT:
        currentActualPosition = currentEncoderValue + AZ_BACK_LEFT_OFFSET;
        System.out.println("back left (BA) actual pos: " + currentActualPosition + "\nback left encoder val: " + currentEncoderValue);
        break;
      case RobotMap.AZ_BACK_RIGHT:
        currentActualPosition = currentEncoderValue + AZ_BACK_RIGHT_OFFSET;
        System.out.println("back right (CA) actual pos: " + currentActualPosition + "\nback right encoder val: " + currentEncoderValue);
        break;
    }
    // if(currentActualPosition < 0) {
    //   currentActualPosition += 4096;
    // }
    // if(currentActualPosition > 4095) {
    //   currentActualPosition -= 4096;
    // }
    talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0 , 10);
    talon.setSelectedSensorPosition(currentActualPosition);
    talon.set(ControlMode.Position, 0);

    //System.out.println("before timer delay in setupOffset");
    /**
     * method needs to pause to let the motor reach the zero position before setting the current encoder value to 0.
     * We could use:
     * Timer.delay(5)
     * Scheduler.getInstance().add(new WaitCommand(10));
     */
    // System.out.println("after timer delay in setupOffset");
    // talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    // talon.setSelectedSensorPosition(0);
  }

  public int getCurrentPosition() {
    return talon.getSelectedSensorPosition();
  }

  public void setSpeed(double speed) {
    talon.set(ControlMode.PercentOutput, speed);
  }

  public int getCurrentMotor() {
    return talon.getDeviceID();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
