package com.github.migi_1.Context.score;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Tests everything that has to with the ScoreWriter class.
 * @author Nils
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ScoreWriter.class)
public class TestScoreWriter {

    private ScoreWriter scoreWriter;
    private ArrayList<Score> scoreList;
    private ArrayList<String> writeToFileList;
    
    /**
     * This method starts every time a new test case starts.
     * @throws Exception exception that is thrown.
     */
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        scoreList = new ArrayList<Score>();
        writeToFileList = Mockito.mock(ArrayList.class);
        PowerMockito.mockStatic(Files.class);
        BDDMockito.given(Files.exists(Mockito.any())).willReturn(true);
        PowerMockito.whenNew(ArrayList.class).withNoArguments().thenReturn(writeToFileList);

        scoreList.add(new Score("name", 42));
        scoreWriter = new ScoreWriter();
    }
    
    /**
     * Tests if an empty  array can be written to a file.
     * @throws IOException when an exception occurs during writing.
     */
    @Test
    public void write_emptyArrayList_Test() throws IOException {
        scoreWriter.write(new ArrayList<Score>(), "invalid");
        Mockito.verify(writeToFileList, Mockito.times(2)).add(Mockito.any());
    }
    
    /**
     * Tests if an non-empty  array can be written to a file.
     * @throws IOException when an exception occurs during writing.
     */
    @Test
    public void write_NonEmptyArrayList_Test() throws IOException {
        scoreWriter.write(scoreList, "invalid");
        Mockito.verify(writeToFileList, Mockito.times(10)).add(Mockito.any());

    }

}
