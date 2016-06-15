package com.github.migi_1.Context.score;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ScoreController.class})
public class TestScoreController {

    private ScoreController scoreController;
    private ScoreWriter scoreWriter;
    private ScoreReader scoreReader;

    @Before
    public void setUp() throws Exception {
        scoreWriter = Mockito.mock(ScoreWriter.class);
        scoreReader = Mockito.mock(ScoreReader.class);
        PowerMockito.whenNew(ScoreWriter.class).withNoArguments().thenReturn(scoreWriter);
        PowerMockito.whenNew(ScoreReader.class).withNoArguments().thenReturn(scoreReader);

        scoreController = new ScoreController();
    }

    @Test
    public void addScoreTest() {
        scoreController.addScore(new Score("0", 0));
        try {
            Mockito.verify(scoreWriter).write(Mockito.any(), Mockito.anyString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
