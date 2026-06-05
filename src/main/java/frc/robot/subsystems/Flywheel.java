// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.SlotConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IOConstants;

import static frc.robot.Constants.IOConstants.*;

public class Flywheel extends SubsystemBase {

    private final TalonFX m_flywheelMotor; 
    private final TalonFX m_flywheelEncoder; // integrated encoder
    private final VoltageOut m_voltageRequest;
    private double m_currentFlywheelTargetRPM = kFlywheelDefaultTargetRPM;
    
    // Feedforward controller to run the shooter wheel in closed-loop, set the constants equal to
    // those calculated by SysId
    private final SimpleMotorFeedforward m_shooterFeedforward =
        new SimpleMotorFeedforward(
            IOConstants.kFlywheel_kS,
            IOConstants.kFlywheel_kV,
            IOConstants.kFlywheel_kA);

    // PID controller to run the shooter wheel in closed-loop, set the constants equal to those
    // calculated by SysId
    private final PIDController m_shooterFeedback = new PIDController(IOConstants.kFlywheel_kP, 0, 0);


    /** Creates a new ExampleSubsystem. */
    public Flywheel() {
        m_flywheelMotor = new TalonFX(kFlywheelMotorID);
        m_flywheelEncoder = m_flywheelMotor;
        m_voltageRequest = new VoltageOut(0.0);

        TalonFXConfiguration flywheelConfig = new TalonFXConfiguration();
        flywheelConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        m_flywheelMotor.getConfigurator().apply(flywheelConfig);

        SlotConfigs slotConfigs = new SlotConfigs(); 
        slotConfigs.kS = kFlywheel_kS;
        slotConfigs.kV = kFlywheel_kV;
        slotConfigs.kA = kFlywheel_kA;

        slotConfigs.kP = kFlywheel_kP;
        slotConfigs.kI = kFlywheel_kI;
        slotConfigs.kD = kFlywheel_kD;

        m_flywheelMotor.getConfigurator().apply(slotConfigs);
    }

    /**
     * Returns a command that runs the shooter at a specifc velocity.
     *
     * @param shooterSpeed The commanded shooter wheel speed in rotations per second
     */
    public Command runShooterCommand() {
        // Run shooter wheel at the current speed using a PID controller and feedforward.
        return run(() -> {
            /* 
            m_flywheelMotor.setVoltage(
                m_shooterFeedback.calculate(m_flywheelEncoder.getVelocity().getValueAsDouble(), 
                                            m_currentFlywheelTargetRPM /60.0)
                    + m_shooterFeedforward.calculate(m_currentFlywheelTargetRPM / 60.0));
            */
            m_flywheelMotor.setControl(m_voltageRequest.withOutput(
                m_shooterFeedback.calculate(m_flywheelEncoder.getVelocity().getValueAsDouble(), 
                                           m_currentFlywheelTargetRPM / 60.0)
                    + m_shooterFeedforward.calculate(m_currentFlywheelTargetRPM / 60.0))); 
        })
        .finallyDo(
            () -> {
                m_flywheelMotor.stopMotor();
            })
        .withName("runShooter");
    }

    public Command stopShooter() {
        return run( () -> m_flywheelMotor.setVoltage(0.0));
    }


    public Command increaseShooterSpeedCommand() {
        return runOnce(() -> {
            if (m_currentFlywheelTargetRPM <= kFlywheelMaxRPM - 1000)
            m_currentFlywheelTargetRPM+=1000;}
        );
    }

    public Command decreaseShooterSpeedCommand() {
        return runOnce(() -> {
                if (m_currentFlywheelTargetRPM >= 1000) 
                    m_currentFlywheelTargetRPM-=1000;
            }
        );
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
