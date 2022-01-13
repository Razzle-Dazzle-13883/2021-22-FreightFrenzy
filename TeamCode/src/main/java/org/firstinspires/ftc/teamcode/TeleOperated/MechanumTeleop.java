package org.firstinspires.ftc.teamcode.TeleOperated;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
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
    DcMotor slideMotor = null;

    DcMotor movingClaw = null;

    //servo
     Servo claw = null;

    public boolean turboMode = false;

    @Override
    public void init() {

        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        spinMotor = hardwareMap.get(DcMotor.class, "spinMotor");
        movingClaw = hardwareMap.get(DcMotor.class, "movingClaw");

        slideMotor = hardwareMap.get(DcMotor.class, "slideMotor");

        leftFront.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        leftBack.setPower(0);
        spinMotor.setPower(0);

        slideMotor.setPower(0);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);

        claw = hardwareMap.get(Servo.class, "claw");
        claw.setPosition(0.0);
    }

    @Override
    public void loop() {

        //strafing stuff + Turbomode stuff
        double x = gamepad1.left_stick_y;
        double y = -gamepad1.left_stick_x;
        double r = -gamepad1.right_stick_x;
        if (turboMode) {
            leftFront.setPower((x + y + r) / 1.5);
            leftBack.setPower((x - y - r)  / 1.5);
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
        spinMotor.setPower(gamepad2.left_trigger - gamepad2.right_trigger);





                /*standard
            if (gamepad2.left_bumper == true) {
                spinMotor.setPower(0);
            }
            if (gamepad2.right_bumper == true) {
                spinMotor.setPower(.35);
            }
            if (gamepad2.dpad_left == true) {
                spinMotor.setPower(-.35);
            }
            */

        //pressurebased


        /*
        if (gamepad2.left_bumper == true) {
            spinMotor.setPower(0);
        }
        else if (gamepad2.right_bumper == true) {
            spinMotor.setPower(0);
        }
        if (gamepad2.left_trigger == 0.0) {
            spinMotor.setPower(0.0);
        }
        if (gamepad2.left_trigger <= 0.2 ) {
            spinMotor.setPower(0.2);
        }
        if (gamepad2.left_trigger <= 0.5 )  {
            spinMotor.setPower(0.5);
        }
        if (gamepad2.left_trigger <= 0.7 ) {
            spinMotor.setPower(0.7);
        }
        if (gamepad2.left_trigger <= 1 ) {
            spinMotor.setPower(1);
        }

        if (gamepad2.right_trigger == 0.0) {
            spinMotor.setPower(0.0);
        }
        if (gamepad2.right_trigger <= 0.2 ) {
            spinMotor.setPower(-0.2);
        }
        if (gamepad2.right_trigger <= 0.5 )  {
            spinMotor.setPower(-0.5);
        }
        if (gamepad2.right_trigger <= 0.7 ) {
            spinMotor.setPower(-0.7);
        }
        if (gamepad2.right_trigger <= 1 ) {
            spinMotor.setPower(-1);
        }


         */



        //claw


        if (gamepad2.x == true) {
                claw.setPosition(1.0);
        } else if (gamepad2.b == true) {
                claw.setPosition(0.0);
        }

            //movingClaw

        if (gamepad2.dpad_up == true) {
            movingClaw.setPower(0.7);
        }
        if (gamepad2.dpad_down == true) {
            movingClaw.setPower(-0.7);

        }

    }
}