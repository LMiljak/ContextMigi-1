package com.github.migi_1.Context.score;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ScoreReader.class})
public class TestScoreReader {

    private ScoreReader scoreReader;
    private List<String> lines;
    private Iterator<String> iterator;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        lines = Mockito.mock(List.class);
        PowerMockito.mockStatic(Files.class);
        iterator = Mockito.mock(Iterator.class);

        Mockito.when(lines.iterator()).thenReturn(iterator);

        Mockito.when(iterator.hasNext()).thenReturn(true);
        Mockito.when(iterator.next()).thenAnswer(new Answer() {

            private String tag = "<score>";
            private String number = "0";
            private int count = 0;

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                count++;
                if (count == 2) {
                    return tag;
                } else {
                    return number;
                }
            }

        });

        scoreReader = Mockito.spy(new ScoreReader());
    }

    @Test
    public void read_InvalidFile_Test() {
        try {
            scoreReader.read("InvalidFile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void read_validFileNoLines_Test() throws IOException {
        BDDMockito.given(Files.exists(Mockito.any())).willReturn(true);
        ArrayList<Score> scoreList = scoreReader.read("validFile");
        assertTrue(scoreList.isEmpty());
    }

    @Test
    public void read_validFileHasLines_Test() throws IOException {
        System.out.println("=======");
        BDDMockito.given(Files.exists(Mockito.any())).willReturn(true);
        BDDMockito.given(Files.readAllLines(Mockito.any())).willReturn(lines);
        ArrayList<Score> scoreList = scoreReader.read("validFile");
        assertFalse(scoreList.isEmpty());
    }

}
