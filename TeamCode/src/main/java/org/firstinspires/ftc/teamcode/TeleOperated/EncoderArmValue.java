package org.firstinspires.ftc.teamcode.TeleOperated;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp()
public class EncoderArmValue extends OpMode {
    DcMotor movingClaw = null;
    DcMotor spinMotor = null;
    @Override
    public void init() {
        movingClaw = hardwareMap.get(DcMotor.class, "movingClaw");
        movingClaw.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        spinMotor = hardwareMap.get(DcMotor.class, "movingClaw");
        spinMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    int currentPosition = 0;
    int carasouelTicks = 0;

    @Override
    public void loop() {
        movingClaw.getCurrentPosition();
        currentPosition = movingClaw.getCurrentPosition();
        telemetry.addData("arm ticks", movingClaw.getCurrentPosition() );
        spinMotor.getCurrentPosition();
        carasouelTicks = spinMotor.getCurrentPosition();
        telemetry.addData("Carasouel", spinMotor.getCurrentPosition() );


    }
}
