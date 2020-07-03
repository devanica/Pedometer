package com.application.pedometer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mStepCounter: StepCounter? = null
    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null

    private var steps = 0

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mStepCounter = StepCounter(object : StepCounter.StepDetector {
            override fun onStepDetected() {
                steps += 1
                tv_steps.text = steps.toString() + " Steps"
                tv_kilometers.text = Util.calculateKilometers(steps) + " Km"
                tv_calories.text = Util.calculateCalories(steps) + " Kcal"
                registerMotionSensorListener()
            }
        })
        registerMotionSensorListener()
    }

    override fun onDestroy() {
        unregisterMotionSensorListener()
        super.onDestroy()
    }

    private fun registerMotionSensorListener() {
        mSensorManager!!.registerListener(mStepCounter, mAccelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    private fun unregisterMotionSensorListener() {
        mSensorManager!!.unregisterListener(mStepCounter)
    }
}
