# FRC 2026 KitBot - Command-Based Robot Code

## Overview
This repository contains sample code for the 2026 FRC KitBot, built using the WPILib Command-Based framework. The robot features a differential drivebase, a flywheel shooter, an intake, and a loader mechanism.

## Hardware Specifications

| Component | Hardware / Motors | Notes |
| :--- | :--- | :--- |
| **Drivebase** | 4x CIM Motors with REV SparkMax controllers | Configured as a Differential Drive. |
| **Drive Encoder** | 1x CTRE CANcoder | Mounted on the right side in a WCP throughbore encoder housing. |
| **Flywheel** | 1x CTRE Kraken X60 | Utilizes the integrated TalonFX controller. |
| **Intake** | 1x CTRE Kraken X60 | Utilizes the integrated TalonFX controller. |
| **Loader** | 1x REV NEO with SparkMax | Configured as a brushless motor. |

## Subsystems
* **`DriveSubsystem`**: Controls the differential drivebase using a split-stick arcade drive setup. Currently tracks distance using a single right-side CANcoder.
* **`Flywheel`**: Manages the shooter wheel using closed-loop velocity control (PID + Feedforward).
* **`IntakeClass`**: Handles the ingestion of game pieces.
* **`Loader`**: Feeds game pieces from the intake into the flywheel.

## Controller Bindings
The robot is controlled via an Xbox Controller plugged into Port 0. 

| Input | Action |
| :--- | :--- |
| **Left Joystick (Y-Axis)** | Arcade Drive: Forward / Backward |
| **Right Joystick (X-Axis)** | Arcade Drive: Rotation (Turning) |
| **A Button (Hold)** | Run Drivebase Feedforward Test at 1.0 m/s |
| **B Button (Hold)** | Run Drivebase Feedforward Test at 2.0 m/s |
| **X Button (Hold)** | Run Drivebase Feedforward Test at -1.0 m/s |
| **Y Button (Hold)** | Run Drivebase Feedforward Test at -2.0 m/s |
| **Right Trigger (Hold)** | Fires game piece (Runs Flywheel and Loader in parallel) |
| **Left Trigger (Toggle)** | Toggles the Loader motor on/off |
| **Right Bumper (Press)** | Increases Flywheel target speed by 1000 RPM |
| **Left Bumper (Press)** | Decreases Flywheel target speed by 1000 RPM |

## Key Constants & Configuration

### CAN IDs
| Subsystem | Component | CAN ID |
| :--- | :--- | :--- |
| **Drivebase** | Left Leader | 11 |
| | Left Follower | 8 |
| | Right Leader | 10 |
| | Right Follower | 7 |
| | Right CANcoder | 4 |
| **Flywheel** | Flywheel Motor | 9 |
| **Intake** | Intake Motor | 12 |
| **Loader** | Loader Motor | 19 |

### Physical Constants
| Property | Value |
| :--- | :--- |
| **Wheel Diameter** | 0.1524 Meters (approx. 6 inches) |
| **Drive Gear Ratio** | 8.45 |
| **Track Width** | 0.55 Meters |
**Note** encoder (CTRE CANcoder) is mounted directly on the output axle, so gear ratio should not be used for Conversions 

### Drivebase Control Constants (SysId Tuned)
* **Feedforward:** `kDriveBase_kS` = 1.008, `kDriveBase_kV` = 2.575, `kDriveBase_kA` = 0.675
* **Feedback (PID):** `kDriveBase_kP` = 3.21, `kDriveBase_kI` = 0.0, `kDriveBase_kD` = 0.0

### Flywheel Control Constants (SysId Tuned)
* **Feedforward:** `kFlywheel_kS` = 0.06, `kFlywheel_kV` = 0.10, `kFlywheel_kA` = 0.025
* **Feedback (PID):** `kFlywheel_kP` = 0.13, `kFlywheel_kI` = 0.0, `kFlywheel_kD` = 0.0

## Development Status & Next Steps

### Completed
* Implemented core command-based skeleton for all major subsystems.
* Feedforward and feedback constants for the Flywheel have been successfully calculated and implemented using the WPILib System Identification (SysId) tool.
* Configured Drivebase SysId routines, added Epilogue telemetry logging for actual speeds, and created open-loop velocity test commands (`testFeedforwardCommand`).
* Added controller face button bindings (`A`, `B`, `X`, `Y`) to test drivebase velocity setpoints.

### To Do
* **Reorganize Commands:** Move main flywheel command to a command class, so we can specify subsystems used (also uses Loader)
* **Intake Command:** Write basic intake command (also needs loader); recommended speed roughly 2x robot ground speed
* **Drivebase SysId:** Run system identification on the drivebase to determine precise feedforward and feedback gains.
* **Hardware Upgrades:** Install a second drivebase encoder on the left side to improve tracking reliability.
* **Autonomous Tuning:** Calibrate the autonomous turning scalar (`kAutoTurnDistanceScalar` currently set to `0.125`) once the second encoder is installed.
* **Kinematics:** Incorporate WPILib `DifferentialDriveKinematics` for more advanced trajectory following in autonomous modes.
