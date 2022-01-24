package org.firstinspires.ftc.teamcode.TeleOperated;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp()
@Disabled

public class ServoTeleopTester extends OpMode {
    Servo armServo;
    @Override
    public void init() {
        armServo = hardwareMap.get(Servo.class, "armServo");
    }

    @Override
    public void loop() {
        double armPos = .5 + ((-1 * gamepad1.right_stick_y) / 2);
        telemetry.addData("arm position", armPos);
        armServo.setPosition(armPos);

    }
}
