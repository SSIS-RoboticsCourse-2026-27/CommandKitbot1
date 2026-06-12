// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.DriveConstants.*;

import java.util.function.DoubleSupplier;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
//import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.hardware.CANcoder;

@Logged(strategy = Logged.Strategy.OPT_IN)
public class DriveSubsystem extends SubsystemBase {
    // --- motors ---
    private final SparkMax m_leftLeader;
    private final SparkMax m_leftFollower;
    private final SparkMax m_rightLeader;
    private final SparkMax m_rightFollower;

    // --- Differential Drive subsystem 
    private final DifferentialDrive m_differentialDrive;

    // --- Encoders ---
    //private final RelativeEncoder m_leftEncoder;
    //private final RelativeEncoder m_rightEncoder;
    private final CANcoder m_rightEncoder;

    /** Creates a new ExampleSubsystem. */
    public DriveSubsystem() {

        // Create brushed motors for a KitBot-style CIM drivetrain
        m_leftLeader = new SparkMax(kLeftLeaderId, MotorType.kBrushed);
        m_leftFollower = new SparkMax(kLeftFollowerId, MotorType.kBrushed);
        m_rightLeader = new SparkMax(kRightLeaderId, MotorType.kBrushed);
        m_rightFollower = new SparkMax(kRightFollowerId, MotorType.kBrushed);


        // Left leader: invert so that positive values drive both sides forward
        SparkMaxConfig m_leftLeaderConfig = new SparkMaxConfig();
        m_leftLeaderConfig.voltageCompensation(12);
        m_leftLeaderConfig.smartCurrentLimit(kDriveMotorCurrentLimit);
        m_leftLeaderConfig.inverted(true);
        m_leftLeader.configure(m_leftLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // Right leader: not inverted
        SparkMaxConfig m_rightLeaderConfig = new SparkMaxConfig();
        m_rightLeaderConfig.voltageCompensation(12);
        m_rightLeaderConfig.smartCurrentLimit(kDriveMotorCurrentLimit);
        m_rightLeader.configure(m_rightLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // Followers mirror their respective leaders
        SparkMaxConfig m_leftFollowerConfig = new SparkMaxConfig();
        m_leftFollowerConfig.follow(m_leftLeader);
        m_leftFollower.configure(m_leftFollowerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        SparkMaxConfig m_rightFollowerConfig = new SparkMaxConfig();
        m_rightFollowerConfig.follow(m_rightLeader);
        m_rightFollower.configure(m_rightFollowerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // Built-in encoders
        //m_leftEncoder = m_leftLeader.getEncoder();
        //m_rightEncoder = m_rightLeader.getEncoder();
        m_rightEncoder = new CANcoder(kRightEncoderID);
        resetEncoders();

        m_differentialDrive = new DifferentialDrive(m_leftLeader, m_rightLeader);
    }

    
    /**
     * A split-stick arcade command, with forward/backward controlled by the left hand, 
     * and turning controlled by right; values should be negated when sent from controller
     * due to differences in coordinate orientation. 
     * @param fwd 
     * @param rot
     * @return
     */
    public Command arcadeDriveCommand(DoubleSupplier fwd, DoubleSupplier rot) {
        return run(() -> m_differentialDrive.arcadeDrive(fwd.getAsDouble(), rot.getAsDouble()))
            .withName("arcadeDrive");
    }

    public Command stopDriveCommand() {
        return runOnce( () -> this.stopMotors() );
    }

    public Command resetEncoderCommand() {
        return runOnce( () -> this.resetEncoders() );
    }

    /**
     * Returns the distance traveled by the left side in meters.
     * Negated so that forward motion (left motor inverted) gives positive distance.
    public double getLeftDistanceMeters() {
        return -m_leftEncoder.getPosition() * kDistancePerRotationMeters;
    }
     */

    /**
     * Returns the distance traveled by the right side in meters.
     */
    @Logged
    public double getRightDistanceMeters() {
        return m_rightEncoder.getPosition().getValueAsDouble() * kDistancePerRotationMeters;
    }

    /**
     * Returns the average distance traveled by both sides in meters.
     * Convenient for straight-line distance calculations in auto.
     */
    @Logged
    public double getAverageDistanceMeters() {
        //return (getLeftDistanceMeters() + getRightDistanceMeters()) / 2.0;
        return getRightDistanceMeters();
    }

    /** Resets both drive encoders to zero. */
    public void resetEncoders() {
        //m_leftEncoder.setPosition(0);
        m_rightEncoder.setPosition(0);
    }

    /** 
     * Stops drivetrain motors immediately
     */
    public void stopMotors() {
        m_differentialDrive.stopMotor();
    }

    /**
     * An example method querying a boolean state of the subsystem (for example, a digital sensor).
     *
     * @return value of some boolean subsystem state, such as a digital sensor.
     */
    public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
    }

    @Override
    public void periodic() {
    // This method will be called once per scheduler run
    }

    @Override
    public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    }
}


