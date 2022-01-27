package org.firstinspires.ftc.teamcode.TeleOperated;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name="EncoderArm")
public class EncoderArmTeleop extends OpMode {

    //motors
    DcMotor leftFront = null;
    DcMotor rightFront = null;
    DcMotor leftBack = null;
    DcMotor rightBack = null;
    DcMotor spinMotor = null;
    DcMotor slideMotor = null;
    DcMotor movingClaw = null;

    //servo


     //Encoder Positions


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

        movingClaw.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);




    }

    @Override
    public void loop() {

        //strafing stuff + Turbomode stuff
        double x = gamepad1.left_stick_y;
        double y = -gamepad1.left_stick_x;
        double r = -gamepad1.right_stick_x;
        if (turboMode) {
            leftFront.setPower((x + y + r) / 1.4);
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









        //movingClaw
        if (gamepad2.dpad_up) {
            movingClaw.setTargetPosition(-130);
            movingClaw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            movingClaw.setPower(.4);
        } else if (gamepad2.dpad_down) {
            movingClaw.setTargetPosition(5);
            movingClaw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            movingClaw.setPower(.4);
        }



// move arm to position



    }
}