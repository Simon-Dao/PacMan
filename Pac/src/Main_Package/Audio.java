/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main_Package;

import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Simon
 */
public class Audio {
    
    private Media BEGINNING_SOUND = new Media(new File("pacman_beginning.wav").toURI().toString());
    private MediaPlayer beginning_sound = new MediaPlayer(BEGINNING_SOUND);
    
    private Media EAT_SOUND = new Media(new File("pacman_chomp.wav").toURI().toString());
    private MediaPlayer eat_sound = new MediaPlayer(EAT_SOUND);
    
    private Media WALK_SOUND = new Media(new File("pacman_chomp.wav").toURI().toString());
    private MediaPlayer walk_sound = new MediaPlayer(EAT_SOUND);
    
    public void beginning()
    {
        beginning_sound.setVolume(.07);
        beginning_sound.play();
        beginning_sound.stop();
        beginning_sound.play();
    }
    
    public void play_walk()
    {
        TimerTask task = new TimerTask() 
        {
            @Override
            public void run() 
            {
                beginning_sound.setVolume(.07);
                beginning_sound.play();
                beginning_sound.stop();
                beginning_sound.play();
            }
    };
    Timer timer = new Timer("Timer");
     
    long delay = 1000L;
    long period = 4000L;
    timer.scheduleAtFixedRate(task, delay, period);
    }
    
    public void play_eat()
    {
        TimerTask task = new TimerTask() 
        {
            @Override
            public void run() 
            {
                eat_sound.setVolume(.07);
                eat_sound.play();
                eat_sound.stop();
                eat_sound.play();
            }
    };
    Timer timer = new Timer("Timer");
     
    long delay = 1L;
    timer.schedule(task, delay);
    }
    
    public void stop()
    {
        eat_sound.stop();
    }
}
