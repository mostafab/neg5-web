package neg5.gson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LocalDateTypeAdapterTest {

    private Gson gson;
    private TestClass testObj;

    @BeforeEach
    public void setup() {
        gson =
                new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                        .create();
        testObj = new TestClass();
    }

    @Test
    public void testToJsonNullInput() {
        testObj.a = null;
        assertEquals("{}", gson.toJson(testObj));
    }

    @Test
    public void testToJsonLocalDate() {
        testObj.a = LocalDate.of(2020, 5, 30);
        assertEquals("{\"a\":\"2020-05-30\"}", gson.toJson(testObj));
    }

    @Test
    public void testFromJsonNull() {
        String input = "{\"a\":null}";
        assertNull(gson.fromJson(input, TestClass.class).a);

        input = "{}";
        assertNull(gson.fromJson(input, TestClass.class).a);
    }

    @Test
    public void testFromJsonLocalDate() {
        String input = "{\"a\":\"2020-05-30\"}";
        assertEquals(LocalDate.of(2020, 5, 30), gson.fromJson(input, TestClass.class).a);
    }

    static class TestClass {
        private LocalDate a;
    }
}
