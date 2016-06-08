package com.github.migi_1.Context.audio;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.Context.utility.ProjectAssetManager;
import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioKey;
import com.jme3.audio.AudioNode;

public class AudioController {

    private AudioNode backgroundMusic;



    public AudioController(Application app) {
        AssetManager assetManager = ProjectAssetManager.getInstance().getAssetManager();
        AudioData data = assetManager.loadAudio("Music/POL-mushroom-trail-short.wav");
        AudioKey key = new AudioKey("Music/POL-mushroom-trail-short.wav", true);
        backgroundMusic = new AudioNode(data, key);
        backgroundMusic.setPositional(false);
        backgroundMusic.setLooping(false);
        backgroundMusic.setVolume(2);
        ((Main) app).getRootNode().attachChild(backgroundMusic);


    }

}
