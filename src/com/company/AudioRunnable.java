package com.company;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class AudioRunnable implements Runnable{

    public static String sound = "";

    public void setSound(String audio){
        sound = audio;
    }

    public static void playaudio()
    {
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(sound)));
            clip.start();
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }

    @Override
    public void run() {
        playaudio();
    }
}
