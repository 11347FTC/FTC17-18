package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;




//Defining all of the variables her
@TeleOp(name="Zippy:TeleOp1", group="Zippy")
public class ZippyTeleOp extends OpMode {
    //DcMotor frontLeftMotor = null;
    //DcMotor frontRightMotor = null;
    //For Mechanum
    DcMotor backLeft = null;
    DcMotor backRight = null;
    Servo leftPoker = null;
    Servo rightPoker = null;
    //Low Goal Shooter Motor
    DcMotor lowGoal = null;
    @Override
    //Finding everything on the hardware map when we have the means to do so
    public void init() {
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
        leftPoker.setPosition(0);
        rightPoker.setPosition(1);
        //Low Goal Shooter
        lowGoal = hardwareMap.dcMotor.get("lowGoal");
    }
    @Override
    public void loop() {
        //determines what power the motors get
        double powery = -gamepad1.left_stick_y;
       //float powerx = gamepad1.left_stick_x;
        //float powerz = gamepad1.right_stick_x;
        //For tank drive
        double powerv = -gamepad1.right_stick_y;
        //tells the position of the servos

        if (gamepad2.left_bumper){
            //poking for beacons
            leftPoker.setPosition(0.75);
            rightPoker.setPosition(1);}

        if (gamepad2.right_bumper){
            rightPoker.setPosition(0.25);
            leftPoker.setPosition(0);}
        if (gamepad2.y){
            rightPoker.setPosition(1);
            leftPoker.setPosition(0);
        }


//Looked at the sample code

        //Low Goal Shooter
        float goalPower = -gamepad2.right_stick_y;
        //range clip
        Range.clip(powery, -1, 1);
        //range.clip(powerx, -0.25, 0.25);
        //range.clip(powerz, -0.25, 0.25);
        Range.clip(powerv, -1, 1);
        Range.clip(goalPower, -0.75, 0.75);
        //The range of the servo that we want to work with is till halfway of where it can turn
        //Tank Drive with only two motors
        backLeft.setPower(powery/4);
        backRight.setPower(powerv/4);


        /*if (gamepad2.a) {
            backLeft.setPower(powery/2);
            backRight.setPower(powerv/2.1);
                    }
        if (gamepad2.b) {
            backLeft.setPower(powery/4);
            backRight.setPower(powerv/4.1);
        }*/
        //failed turbo mode
        /*driving for the mechanums
        *frontLeftMotor.setPower(powery - powerx + powerz);
        *frontRightMotor.setPower(powery + powerx - powerz);
        *backRightMotor.setPower(powery - powerx - powerz);
        *backLeft.setPower(powery + powerx + powerz);
        */
//Turbo Mode doesn't seem to work
        //LowGoal
        lowGoal.setPower(goalPower);
        //need to test these asap

    }
}