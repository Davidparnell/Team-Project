package com.moneyapp.wallet;

/*
Animations for all notes and coins buttons
 */

public class BounceInterpolator implements android.view.animation.Interpolator
{
    private double amplitude = 1;
    private double frequency = 10; //speed

    public BounceInterpolator(double amplitude, double frequency)
    {
        amplitude = amplitude;
        frequency = frequency;
    }

    @Override
    public float getInterpolation(float time)
    {
        return((float) (-1 * Math.pow(Math.E, -time/ amplitude) * Math.cos(frequency * time) + 1));
    }
}
