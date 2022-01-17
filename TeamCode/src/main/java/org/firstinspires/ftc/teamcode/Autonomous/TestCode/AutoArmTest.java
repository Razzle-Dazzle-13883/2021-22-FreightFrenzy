package org.firstinspires.ftc.teamcode.Autonomous.TestCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name = "AutoArmTest")
    public class AutoArmTest extends LinearOpMode {
    private DcMotor rightFront;
    private DcMotor rightBack;
    private DcMotor leftFront;
    private DcMotor leftBack;

    private CRServo armServo;


    @Override
    public void runOpMode() throws InterruptedException {
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightBack = hardwareMap.dcMotor.get("rightBack");
        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftBack = hardwareMap.dcMotor.get("leftBack");

        armServo = hardwareMap.get(CRServo.class, "armServo");


        waitForStart();


        rightFront.setPower(0.0);
        rightBack.setPower(0.0);
        leftFront.setPower(0.0);
        leftBack.setPower(0.0);

        sleep(500);

        armServo.setPower(0.1);
        sleep(1000);
        armServo.setPower(0.2);
        sleep(1000);
        armServo.setPower(0.3);
        sleep(1000);
        armServo.setPower(0.4);
        sleep(1000);
        armServo.setPower(0.5);
        sleep(1000);
        armServo.setPower(0.6);
        sleep(1000);
        armServo.setPower(0.7);
        sleep(1000);
        armServo.setPower(0.8);
        sleep(1000);
        armServo.setPower(0.9);
        sleep(1000);
        armServo.setPower(1);
        sleep(1000);



    }
}