package org.example.exception;


import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ExceptionSampleTest {

    ExceptionSample service = new ExceptionSample();

    // 1. Prefer Specific Exception
    @Test
    public void testParseNumberThrowsSpecificException() {
        assertThrows(NumberFormatException.class, () -> service.parseNumber("abc"));
    }

    // 2. Catch order test
    @Test
    public void testReadFileNotFound() {
        assertEquals("FILE_NOT_FOUND", service.readFile("not_exist.txt"));
    }

    // 3. Catch non-empty
    @Test
    public void testSafeDivide() {
        assertEquals(0, service.safeDivide(10, 0));
    }

    // 4. Try-with-resource fallback
    @Test
    public void testCountBytesFileNotFound() {
        assertEquals(-1, service.countBytes("notfound.txt"));
    }

    // 5. Finally return hides exception
    @Test
    public void testBadFinallyReturnHidesException() {
        // Method đáng lẽ phải ném IllegalArgumentException
        // nhưng bị che mất → trả về 999
        assertEquals(999, service.badFinallyReturn());
    }

    // 6. Finally throw masks original exception
    @Test
    public void testBadFinallyThrowMasksOriginal() {
        RuntimeException e =
                assertThrows(RuntimeException.class, () -> service.badFinallyThrow());

        // Không thấy IOException("original") nữa
        assertEquals("MASKED", e.getMessage());
    }
}
