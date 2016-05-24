package com.github.migi_1.Context.model;

import java.io.InputStream;
import java.util.EnumSet;
import java.util.List;

import com.jme3.asset.AssetEventListener;
import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetKey;
import com.jme3.asset.AssetLoader;
import com.jme3.asset.AssetLocator;
import com.jme3.asset.AssetManager;
import com.jme3.asset.FilterKey;
import com.jme3.asset.ModelKey;
import com.jme3.asset.TextureKey;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioKey;
import com.jme3.font.BitmapFont;
import com.jme3.material.Material;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Caps;
import com.jme3.scene.Spatial;
import com.jme3.shader.ShaderGenerator;
import com.jme3.texture.Texture;

public final class AssetManagerCreator {

    private static final AssetManager INSTANCE = new AssetManager() {

        @Override
        public void unregisterLocator(String rootPath,
                Class<? extends AssetLocator> locatorClass) {
            // TODO Auto-generated method stub

        }

        @Override
        public void unregisterLoader(Class<? extends AssetLoader> loaderClass) {
            // TODO Auto-generated method stub

        }

        @Override
        public void setShaderGenerator(ShaderGenerator generator) {
            // TODO Auto-generated method stub

        }

        @Override
        public void removeClassLoader(ClassLoader loader) {
            // TODO Auto-generated method stub

        }

        @Override
        public void removeAssetEventListener(AssetEventListener listener) {
            // TODO Auto-generated method stub

        }

        @Override
        public void registerLocator(String rootPath,
                Class<? extends AssetLocator> locatorClass) {
            // TODO Auto-generated method stub

        }

        @Override
        public void registerLoader(Class<? extends AssetLoader> loaderClass,
                String... extensions) {
            // TODO Auto-generated method stub

        }

        @Override
        public AssetInfo locateAsset(AssetKey<?> key) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Texture loadTexture(String name) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Texture loadTexture(TextureKey key) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Spatial loadModel(String name) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Spatial loadModel(ModelKey key) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Material loadMaterial(String name) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public BitmapFont loadFont(String name) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public FilterPostProcessor loadFilter(String name) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public FilterPostProcessor loadFilter(FilterKey key) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public AudioData loadAudio(String name) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public AudioData loadAudio(AudioKey key) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public <T> T loadAssetFromStream(AssetKey<T> key, InputStream inputStream) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Object loadAsset(String name) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public <T> T loadAsset(AssetKey<T> key) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public ShaderGenerator getShaderGenerator(EnumSet<Caps> caps) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public <T> T getFromCache(AssetKey<T> key) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<ClassLoader> getClassLoaders() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public <T> boolean deleteFromCache(AssetKey<T> key) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void clearCache() {
            // TODO Auto-generated method stub

        }

        @Override
        public void clearAssetEventListeners() {
            // TODO Auto-generated method stub

        }

        @Override
        public <T> void addToCache(AssetKey<T> key, T asset) {
            // TODO Auto-generated method stub

        }

        @Override
        public void addClassLoader(ClassLoader loader) {
            // TODO Auto-generated method stub

        }

        @Override
        public void addAssetEventListener(AssetEventListener listener) {
            // TODO Auto-generated method stub

        }
    };

    public static AssetManager getInstance() {
        return INSTANCE;

    }
}
