package org.firstinspires.ftc.teamcode;

import android.media.MediaCodecInfo;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.ColorSensor;

import java.sql.Time;

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
    Servo leftPoker;
    Servo rightPoker;
    //Low Goal Shooter Motor
    DcMotor lowGoal = null;
    ColorSensor beacon = null;
    private ElapsedTime runtime = new ElapsedTime();


    double DriveSpeed = 0.5;
    double TurnSpeed = 0.6;
    double ShootPower = 0.5;
    //complete

    @Override
    public void runOpMode() {
        //frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        //frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        //For Mechanum
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        leftPoker = hardwareMap.servo.get("leftPoker");
        rightPoker = hardwareMap.servo.get("rightPoker");

        //frontLeftMotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        //frontRightMotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        //For Mechanum

        //Right Motor needs to be reversed
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        //frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        leftPoker.setPosition(1);
        rightPoker.setPosition(0);
        //Low Goal Shooter
        lowGoal = hardwareMap.dcMotor.get("lowGoal");
        //Color Sensor
        beacon = hardwareMap.colorSensor.get("beacon");

        waitForStart();
        beacon.enableLed(false);
        leftPoker.setPosition(1);
        rightPoker.setPosition(0);

        //Move Forward for 2 seconds
        DriveForwardTime(DriveSpeed,2000);

        //Turn for 1 second
        TurnLeftTime(TurnSpeed, 1000);
        //Move Forward for 1 second
        DriveForwardTime(DriveSpeed, 1000);
        //Sense beacons
        SenseRed();
        DriveBackwardTime(DriveSpeed,1500);
        TurnLeftTime(TurnSpeed,500);
        DriveForwardTime(DriveSpeed,2000);
        Shoot(ShootPower,3000);
    }
    //A lot of Methods for Auto
    public void DriveForward(double power){
        backLeft.setPower(power);
        backRight.setPower(-power);
    }
    public void DriveBackward(double power){
        backLeft.setPower(-power);
        backRight.setPower(power);
    }
    public void TurnLeft(double power){
        backLeft.setPower(power);
        backRight.setPower(power);
    }
    public void TurnRight(double power){
        backLeft.setPower(-power);
        backRight.setPower(-power);
    }
    public void TimeElapsed(long time){
        while (opModeIsActive() && (runtime.seconds() < time)) {
            telemetry.addData("Path", "Leg : %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }
    public void DriveForwardTime(double power, long time){
        DriveForward(power);
        TimeElapsed(time);
    }
    public void DriveBackwardTime(double power, long time){
        DriveBackward(power);
        TimeElapsed(time);
    }
    public void TurnLeftTime (double power, long time){
        TurnLeft(power);
        TimeElapsed(time);
    }
    public void TurnRightTime (double power, long time){
        TurnRight(power);
        TimeElapsed(time);
    }
    public void PokeRight (double position, double power, long time){
        rightPoker.setPosition(position);
        DriveForwardTime(power,time);
    }
    public void PokeLeft (double position, double power, long time){
        leftPoker.setPosition(position);
        DriveForwardTime(power,time);
    }
    public void SenseRed (){
        telemetry.addData("3 Red ", beacon.red());
        telemetry.addData("4 Green ", beacon.green());
        telemetry.addData("5 Blue ", beacon.blue());

        if (beacon.red() > beacon.blue() || beacon.red() > beacon.green()) {
            PokeLeft(0.5, DriveSpeed, 500);
        } else if (beacon.blue() > beacon.red() || beacon.blue() > beacon.green()) {
            PokeRight(0.5, DriveSpeed, 500);
        }
        else {
            //Just incase it doesn't sense anything

            DriveForwardTime(DriveSpeed, 500);

        }
    }
    public void SenseBlue (){
        telemetry.addData("3 Red ", beacon.red());
        telemetry.addData("4 Green ", beacon.green());
        telemetry.addData("5 Blue ", beacon.blue());

        if (beacon.blue() > beacon.red() || beacon.blue() > beacon.green()) {
            PokeLeft(0.5, DriveSpeed, 500);
        } else if (beacon.red() > beacon.blue() || beacon.red() > beacon.green()) {
            PokeRight(0.5, DriveSpeed, 500);
        }
        else {
            //Just incase it doesn't sense anything

            DriveForwardTime(DriveSpeed, 500);

        }
    }
    public void Shoot (double power, long time){
        lowGoal.setPower(power);
        TimeElapsed(time);
    }
}