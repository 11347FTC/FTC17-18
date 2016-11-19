package org.firstinspires.ftc.teamcode;

import android.media.MediaCodecInfo;

import com.ftdi.j2xx.FT_EEPROM_232H;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by Ganondorf on 11/16/2016.
 */
@Autonomous (name="ZippyAutoRed1", group="Zippy")
public class ZippyAutoRedAlliance1 extends LinearOpMode {
    //DcMotor frontLeftMotor = null;
    //DcMotor frontRightMotor = null;
    //For Mechanum
    DcMotor backLeft = null;
    DcMotor backRight = null;
    Servo leftPoker = null;
    Servo rightPoker = null;
    //Low Goal Shooter Motor
    DcMotor lowGoal = null;
    private ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() {
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0", "Starting at %7d :%7d",
                backLeft.getCurrentPosition(),
                backRight.getCurrentPosition());
        telemetry.update();

        idle();
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        encoderDrive(0.6, 48, 48, 5.0);
    }

    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = backLeft.getCurrentPosition() + (int) (leftInches * 200);
            newRightTarget = backRight.getCurrentPosition() + (int) (rightInches * 200);
            backLeft.setTargetPosition(newLeftTarget);
            backRight.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            backLeft.setPower(Math.abs(speed));
            backRight.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (backLeft.isBusy() && backRight.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        backLeft.getCurrentPosition(),
                        backRight.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            backLeft.setPower(0);
            backRight.setPower(0);

            // Turn off RUN_TO_POSITION
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
}

