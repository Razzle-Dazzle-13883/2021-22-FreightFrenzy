package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="Teleop", group="Teleop")
public class TeleopTest extends OpMode {

     DcMotor leftFront;
     DcMotor rightFront;
     DcMotor leftBack;
     DcMotor rightBack;
     DcMotor spinMotor;
     DcMotor wheelIntake1;
     DcMotor wheelIntake2;

    //servo

    Servo claw;



    @Override
    public void init() {
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        spinMotor = hardwareMap.get(DcMotor.class, "spinMotor");
        wheelIntake1 = hardwareMap.get(DcMotor.class, "wheelIntake1");
        wheelIntake2 = hardwareMap.get(DcMotor.class, "wheelintake2");

        claw = hardwareMap.get(Servo.class, "claw");
        claw.setPosition(0.0);

        leftFront.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        leftBack.setPower(0);
        spinMotor.setPower(0);

    }


    public boolean turboMode = true;


    @Override
    public void loop() {

        //strafing stuff + Turbomode stuff
        double x = gamepad1.left_stick_y;
        double y = -gamepad1.left_stick_x;
        double r = -gamepad1.right_stick_x;
        if (turboMode) {
            leftFront.setPower(x + y - r);
            leftBack.setPower(x - y - r);
            rightFront.setPower(x - y + r);
            rightBack.setPower(x + y + r);
        } else {
            leftFront.setPower((x + y + r) / 3.7);
            leftBack.setPower((x - y + r) / 3.7);
            rightFront.setPower((x - y - r) / 3.7);
            rightBack.setPower((x + y - r) / 3.7);




            //Fastmode and slowmode
            if (gamepad1.y == true) {
                turboMode = true;
            } else if (gamepad1.a == true) {
                turboMode = false;
            }

            //ATTACHMENT STUFF

            //Carasol mover
            if (gamepad2.left_bumper == true) {
                spinMotor.setPower(0);
            }
            if (gamepad2.right_bumper == true) {
                spinMotor.setPower(1);
            }


            //CLAW
            if (gamepad1.dpad_up == true) {
                claw.setPosition(1.0);
            }
            if (gamepad1.dpad_down == true) {
                claw.setPosition(-1.0);
            }
            if (gamepad1.dpad_left == true) {
                claw.setPosition(0);
            }
            if (gamepad2.dpad_right == true) {
                claw.setPosition(0.5);
            }


            // (TEMP?) WHEEL ATTACHMENT
            if (gamepad2.dpad_up == true) {
                wheelIntake1.setPower(-.5);
            }
            if (gamepad2.dpad_up == true) {
                wheelIntake2.setPower(.5);
            }
            if (gamepad2.dpad_down == true) {
                wheelIntake1.setPower(0.0);
            }
            if (gamepad2.dpad_down == true) {
                wheelIntake2.setPower(0.0);
            }
            if (gamepad2.dpad_left == true) {
                wheelIntake1.setPower(0.5);
            }
            if (gamepad2.dpad_left == true) {
                wheelIntake2.setPower(-0.5);
            }

            if (gamepad2.right_bumper == true) {
                leftBack.setPower(1.0);
            }
            if (gamepad2.left_bumper == true) {
                leftBack.setPower(-1.0);
            }
        }
    }

}
