/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static DriveTrain driveTrain;
  public static AZIMUTH azimuth;
  public static Joystick joystick;
  public static OI oi;
  private static boolean isAzimuthOffsetSetup;

  private static final String optionFrontLeft = "Front Left";
  private static final String optionFrontRight = "Front Right";
  private static final String optionBackLeft = "Back Left";
  private static final String optionBackRight = "Back Right";
  private String motorSelected;

  private final  SendableChooser<String> motorChooser = new SendableChooser<>();

  // private static final String kDefaultAuto = "Default";
  // private static final String kCustomAuto = "My Auto";
  // private String m_autoSelected;

  // private final SendableChooser<String> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    driveTrain = new DriveTrain();
    azimuth = new AZIMUTH();
    joystick = new Joystick(0);
    oi = new OI();
    isAzimuthOffsetSetup = false;

    motorChooser.setDefaultOption("Front Left", optionFrontLeft);
    motorChooser.addOption("Front Right", optionFrontRight);
    motorChooser.addOption("Back Left", optionBackLeft);
    motorChooser.addOption("Back Right", optionBackRight);
    SmartDashboard.putData("Motor Chooser", motorChooser);

    SmartDashboard.putData("Set Offset", new SetOffset());
    // m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    // m_chooser.addOption("My Auto", kCustomAuto);
    // SmartDashboard.putData("Auto choices", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    // m_autoSelected = m_chooser.getSelected();
    // // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    // System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
    // switch (m_autoSelected) {
    //   case kCustomAuto:
    //     // Put custom auto code here
    //     break;
    //   case kDefaultAuto:
    //   default:
    //     // Put default auto code here
    //     break;
    // }
  }

  @Override
  public void teleopInit() {
    motorSelected = motorChooser.getSelected();
    System.out.println("Motor Selected: " + motorSelected);
    switch(motorSelected) {
      case optionFrontLeft:
        driveTrain.setCurrentMotor(RobotMap.DRIVE_FRONT_LEFT);
        azimuth.setCurrentMotor(RobotMap.AZ_FRONT_LEFT);
        break;
      case optionFrontRight:
        driveTrain.setCurrentMotor(RobotMap.DRIVE_FRONT_RIGHT);
        azimuth.setCurrentMotor(RobotMap.AZ_FRONT_RIGHT);
        break;
      case optionBackLeft:
        driveTrain.setCurrentMotor(RobotMap.DRIVE_BACK_LEFT);
        azimuth.setCurrentMotor(RobotMap.AZ_BACK_LEFT); 
        break;
      case optionBackRight:
        driveTrain.setCurrentMotor(RobotMap.DRIVE_BACK_RIGHT);
        azimuth.setCurrentMotor(RobotMap.AZ_BACK_RIGHT);
        break;
     }
    if(!isAzimuthOffsetSetup) {
      Robot.azimuth.setupOffsets();
      isAzimuthOffsetSetup = true;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
