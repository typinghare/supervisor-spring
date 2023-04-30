package us.jameschan.supervisor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import us.jameschan.supervisor.common.Encoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {
    us.jameschan.supervisor.config.SupervisorConfiguration.class,
    us.jameschan.supervisor.config.PropertyConfiguration.class,
    us.jameschan.supervisor.common.Encoder.class
})
public class EncoderTest {
    @Autowired
    private Encoder encoder;

    @Test
    void encodingTest() {
        final String rawPassword = "123456Abc";
        final String encodedString = encoder.encode(rawPassword);

        assertEquals(encodedString.length(), 80);
        assertTrue(encoder.matches(rawPassword, encodedString));
    }
}
