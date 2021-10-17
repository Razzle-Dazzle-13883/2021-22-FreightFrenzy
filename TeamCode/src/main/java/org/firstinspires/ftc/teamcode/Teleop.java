package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Anoopteleop", group="Teleop")
public class Teleop extends OpMode {

    private DcMotor leftFront = null;
    private DcMotor rightFront = null;
    private DcMotor leftBack = null;
    private DcMotor rightBack = null;
    private DcMotor spinMotor = null;

    private DcMotor wheelIntake1 = null;
    private DcMotor wheelintake2 = null;

    //servo


    @Override
    public void init() {
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        spinMotor = hardwareMap.get(DcMotor.class, "spinMotor");
        wheelIntake1 = hardwareMap.get(DcMotor.class, "wheelIntake1");
        wheelintake2 = hardwareMap.get(DcMotor.class, "wheelintake2");


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

            if (gamepad1.y == true) {
                turboMode = true;
            } else if (gamepad1.a == true) {
                turboMode = false;
            }


            //ATTACHMENT STUFF

            if (gamepad2.left_bumper == true) {
                spinMotor.setPower(0.25);
            }
            if (gamepad2.right_bumper == true) {
                spinMotor.setPower(-0.25);
            }



            // (TEMP?) WHEEL ATTACHMENT
            if (gamepad2.dpad_up == true) {
                wheelIntake1.setPower(-.5);
            }
            if (gamepad2.dpad_up == true) {
                wheelintake2.setPower(.5);
            }
            if (gamepad2.dpad_down == true) {
                wheelIntake1.setPower(0.0);
            }
            if (gamepad2.dpad_down == true) {
                wheelintake2.setPower(0.0);
            }
            if (gamepad2.dpad_left == true) {
                wheelIntake1.setPower(0.5);
            }
            if (gamepad2.dpad_left == true) {
                wheelintake2.setPower(-0.5);
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

