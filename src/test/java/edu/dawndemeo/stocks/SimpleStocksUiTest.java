package edu.dawndemeo.stocks;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

/**
 * @author dawndemeo
 */
public class SimpleStocksUiTest {

    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Test
    public void testMainShouldTakeUserInput() {
        systemInMock.provideLines("2", "goog", "0");
        SimpleStocksUi.main(null);
    }
}