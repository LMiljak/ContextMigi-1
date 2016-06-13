package com.github.migi_1.Context.audio;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioKey;
import com.jme3.audio.AudioNode;

/**
 * This class handles all audio features.
 *
 * @author Marcel
 *
 */
public class AudioController {

    private boolean isPlaying;

    private AudioNode backgroundMusic;

    /**
     * Constructor. Initialises the background music and starts playing it.
     * @param app The running application
     */
    public AudioController(Application app) {
        AssetManager assetManager = ProjectAssetManager.getInstance().getAssetManager();
        AudioData data = assetManager.loadAudio("Music/POL-mushroom-trail-short.wav");
        AudioKey key = new AudioKey("Music/POL-mushroom-trail-short.wav", true);
        backgroundMusic = new AudioNode(data, key);
        backgroundMusic.setPositional(false);
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(1);
        ((Main) app).getRootNode().attachChild(backgroundMusic);
        isPlaying = false;

    }

    /**
     * Getter for the background music.
     * @return The background music
     */
    public AudioNode getBackgroundMusic() {
        return backgroundMusic;
    }

    /**
     * Mute or unmute the background music.
     */
    public void mute() {
        if (!isPlaying) {
            isPlaying = true;
            backgroundMusic.play();
        }
        else {
            isPlaying = false;
            backgroundMusic.pause();
        }
    }

    /**
     * True when background music is playing.
     * @return whether background music is playing
     */
    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * Set whether the background is playing.
     * @param isPlaying Whether background music is playing.
     */
    public void setPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }



}
