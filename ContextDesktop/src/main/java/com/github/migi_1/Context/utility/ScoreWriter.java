package com.github.migi_1.Context.utility;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import com.jme3.export.JmeExporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;

public class ScoreWriter implements JmeExporter{

    @Override
    public void save(Savable object, OutputStream f) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void save(Savable object, File f) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public OutputCapsule getCapsule(Savable object) {
        // TODO Auto-generated method stub
        return null;
    }

}
