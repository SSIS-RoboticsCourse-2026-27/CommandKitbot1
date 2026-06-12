// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static class OperatorConstants {
        public static final int kDriverControllerPort = 0;
    }


    public static class DriveConstants {
        public static final int kLeftLeaderId = 11;
        public static final int kLeftFollowerId = 8;
        public static final int kRightLeaderId = 10;
        public static final int kRightFollowerId = 7;

        public static final int kRightEncoderID = 4;

        public static final boolean kLeftLeaderReversed = true;
        public static final boolean kRighteaderReversed = false;

        public static final int kDriveMotorCurrentLimit = 60;

        /** Wheel diameter in meters (e.g. 6 in ≈ 0.1524 m). */
        public static final double kWheelDiameterMeters = 0.1524;
        /** Gear ratio motor-to-wheel (e.g. 8.45 for KitBot). */
        public static final double kDriveGearRatio = 8.45; //10.71;
        public static final double kDistancePerRotationMeters = 
            Math.PI * kWheelDiameterMeters; // encoder directly on wheel shaft
        
        /**
         * Approximate track width (meters) used for encoder-only in-place turns.
         * Measure center-to-center distance between left and right wheels for best
         * results.
         */    
        public static final double kDriveTrackWidthMeters = 0.55;

        /**
         * Scalar applied to encoder-only turn distance calculations.
         * If the robot over-rotates, decrease this; if it under-rotates, increase it.
         */
        public static final double kAutoTurnDistanceScalar = 0.125;
    }

    public static class IOConstants {
        public static final int kFlywheelMotorID = 9;
        public static final int kIntakeMotorID = 12;
        public static final int kLoaderMotorID = 19;

        // ======= flywheel constants ==========
        public static final int kFlywheelDefaultTargetRPM = 3000;
        public static final int kFlywheelMaxRPM = 7000;
        public static final int kFlywheelMotorCurrentLimit = 60;
        // feedforward gains
        public static final double kFlywheel_kS = 0.06;
        public static final double kFlywheel_kV = 0.10;
        public static final double kFlywheel_kA = 0.025;

        // feedback PID controller parameters
        public static final double kFlywheel_kP = 0.13;
        public static final double kFlywheel_kI = 0.0;
        public static final double kFlywheel_kD = 0.0;

        // ======= intake constants ==========
        public static final int kIntakeDefaultTargetRPM = 600; 
        
        // =========== loader constants =========
        public static final int kLoaderIntakeTargetRPM = 800; 
        public static final int kLoaderToFlywheelTargetRPM = 800;
        public static final int kLoaderMotorCurrentLimit = 60;
    }

    public static class AutoConstants {
        public static final double kDistanceTargetMeters = 2.0;
    }
}
