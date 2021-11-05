/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Manipulator drive motor:  "left_arm"
 * Servo channel:  Servo to open left claw:  "left_hand"
 * Servo channel:  Servo to open right claw: "right_hand"
 */
public class Oldteleop {
    /* Public OpMode members. */

    //Define motors and set all to null
    public DcMotor leftFront = null;
    public DcMotor rightFront = null;
    public DcMotor leftBack = null;
    public DcMotor rightBack = null;


    //Define servos and set all to null
    public Servo claw = null;

    //Determine the speed of the wheels, faster to move quickly, slower for precision
    public boolean turboMode = true;

    /* local OpMode members. */
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    /* Constructor */
    public Oldteleop() {

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap, boolean useEncoder) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Initialize Motors
        leftFront = hwMap.get(DcMotor.class, "leftFront");
        rightFront = hwMap.get(DcMotor.class, "rightFront");
        leftBack = hwMap.get(DcMotor.class, "leftBack");
        rightBack = hwMap.get(DcMotor.class, "rightBack");


        // Initialize Servos
        claw = hwMap.get(Servo.class, "claw");

        //Set directions of dc motors
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.REVERSE);


        // Set all motors to zero power
        leftFront.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        leftBack.setPower(0);


        // Set all motors to run without encoders.
        // If use encoder is true than we will use encoders with servos
        if (useEncoder == true) {
            leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else {
            leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

    }

    public void motorMovementTeleOpLoop(Gamepad gamepad1, Gamepad gamepad2) {
        //Define variables to correlate to gamepad input
        double x = gamepad1.left_stick_y;
        double y = -gamepad1.left_stick_x;
        double r = -gamepad1.right_stick_x;

        //Use variables to calculate the power needed to be sent to all 4 motors
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
        }

        //Use turboMode to allow driver to set speed of wheels to fast or slow, which helps with speed or precision respectively
        if (gamepad1.y == true) {
            turboMode = true;
        } else if (gamepad1.a == true) {
            turboMode = false;
        }

        //Move wobble motor up or down depending on input from the left stick up and down.

        //Open and close the wobble servo motor
        if (gamepad2.y == true) {
            claw.setPosition(0.0);
        } else if (gamepad2.a == true) {
            claw.setPosition(1.0);
        }

        //more shoot options



        //Further push it with left bumper

        // Reverse buttons, allows drivers to quickly switch directions as needed.
        if (gamepad1.dpad_up == true) {
            leftFront.setDirection(DcMotor.Direction.REVERSE);
            leftBack.setDirection(DcMotor.Direction.REVERSE);
            rightFront.setDirection(DcMotor.Direction.REVERSE);
            rightBack.setDirection(DcMotor.Direction.FORWARD);
        }
        if (gamepad1.dpad_down == true) {
            leftFront.setDirection(DcMotor.Direction.FORWARD);
            leftBack.setDirection(DcMotor.Direction.FORWARD);
            rightFront.setDirection(DcMotor.Direction.FORWARD);
            rightBack.setDirection(DcMotor.Direction.REVERSE);
        }

    }
}