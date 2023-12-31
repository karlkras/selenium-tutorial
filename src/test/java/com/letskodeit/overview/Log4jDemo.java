package com.letskodeit.overview;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jDemo {
    private static final Logger logger = LogManager.getLogger(Log4jDemo.class);

    public static void main(String[] args) {
        logger.trace("Trace Message Printed");
        logger.debug("Debug Message Printed");
        logger.info("Info Message Printed");
        logger.error("Error Message Printed");
        logger.fatal("Fatal Message Printed");
    }
}
