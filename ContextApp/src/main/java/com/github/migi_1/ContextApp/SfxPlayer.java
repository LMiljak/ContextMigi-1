package com.github.migi_1.ContextApp;

import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Contains a SoundPool, which plays certain sound effects.
 */
public class SfxPlayer {
    
    private MainActivity main;
    private SoundPool soundPool;
    private AudioManager audioManager;
    private int[] soundIds;
    
    /**
     * Creates an SfxPlayer.
     * @param main
     *          the instance of the activity that calls this function
     * @param audioManager 
     *          the audiomanager from the main activity which is used for
     *          the soundpool which plays the sound effects.
     */
    public SfxPlayer(MainActivity main, AudioManager audioManager) {
        
        this.main = main;
        this.audioManager = audioManager;
        
        soundPool = new SoundPool(10, audioManager.STREAM_MUSIC, 0);
        main.setVolumeControlStream(audioManager.STREAM_MUSIC);
        
        soundIds = new int[3];
        soundIds[0] = soundPool.load(main, R.raw.gethit, 1);
        soundIds[1] = soundPool.load(main, R.raw.miss, 1);
        soundIds[2] = soundPool.load(main, R.raw.hit, 1);
        
    }
    
    /**
     * Plays a sound effect with the SoundPool.
     * @param sfx 
     *          the position of the sound effect in the soundIds array
     */
    public void play(int sfx) {
        float volume = (float) audioManager.getStreamVolume(audioManager.STREAM_MUSIC);
        if (soundPool != null) {
            soundPool.play(soundIds[sfx], volume, volume, 1, 0, 1.f);
        }
    }
    
    /**
     * Releases the SoundPool.
     */
    public void release() {
        
        // release the soundPool
        if (soundPool != null) {
            soundPool.release();
        }
        
    }
    
}
