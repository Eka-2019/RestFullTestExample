import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestLogger {

    private static final Logger log = LogManager.getLogger(TestApodApi.class);

    public void test() {
        log.info("INFO");
        log.info("DEBUG");
        log.info("ERROR");
        log.info("FATAL");
        log.info("WARN");
        assertEquals(1, 1);
    }

}
