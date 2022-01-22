package org.firstinspires.ftc.teamcode.TeleOperated;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="MAINTeleop")
public class MechanumTeleop extends OpMode {

    //motors
    DcMotor leftFront = null;
    DcMotor rightFront = null;
    DcMotor leftBack = null;
    DcMotor rightBack = null;

    DcMotor spinMotor = null;

    DcMotor movingClaw = null;

    //servo
     Servo claw = null;
     Servo magnetArm = null;

    public boolean turboMode = false;

    @Override
    public void init() {

        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        spinMotor = hardwareMap.get(DcMotor.class, "spinMotor");
        movingClaw = hardwareMap.get(DcMotor.class, "movingClaw");




        leftFront.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        leftBack.setPower(0);
        spinMotor.setPower(0);

        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);

        claw = hardwareMap.get(Servo.class, "claw");
        claw.setPosition(0.0);
        magnetArm = hardwareMap.get(Servo.class, "magnetArm");

    }

    @Override
    public void loop() {

        //strafing stuff + Turbomode stuff
        double x = gamepad1.left_stick_y;
        double y = -gamepad1.left_stick_x;
        double r = -gamepad1.right_stick_x;
        if (turboMode) {
            leftFront.setPower((x + y + r) / 1.5);
            leftBack.setPower((x - y - r) / 1.5);
            rightFront.setPower((x - y + r) / 1.5);
            rightBack.setPower((x + y + r) / 1.5);
        } else {
            leftFront.setPower((x + y + r) / 3.7);
            leftBack.setPower((x - y + r) / 3.7);
            rightFront.setPower((x - y - r) / 3.7);
            rightBack.setPower((x + y - r) / 3.7);
        }

        //Fastmode and slowmode
        if (gamepad1.y == true) {
            turboMode = true;
        } else if (gamepad1.a == true) {
            turboMode = false;
        }

        //Carasol mover
        spinMotor.setPower(gamepad2.right_trigger - gamepad2.left_trigger);

        if (gamepad2.x == true) {
            claw.setPosition(9.0);
        } else if (gamepad2.b == true) {
            claw.setPosition(0.0);
        }

        if (gamepad2.dpad_up) {
            movingClaw.setTargetPosition(-130);
            movingClaw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            movingClaw.setPower(.4);
        } else if (gamepad2.dpad_down) {
            movingClaw.setPower(0);
        } else if (gamepad2.dpad_left) {
            movingClaw.setTargetPosition(73);
            movingClaw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            movingClaw.setPower(.4);
        } else if (gamepad2.dpad_right) {
            movingClaw.setTargetPosition(20);
            movingClaw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            movingClaw.setPower(.4);


        }else {
            movingClaw.setPower(0);
        }

    }}