package com.llts.test.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertFalse;

public class CheckPoint {
    private static final Logger logger = LogManager.getLogger(CheckPoint.class.getName());

    public static Map<String, StatusEnum> resultMap = new HashMap<>();

    public static void clear() {
        resultMap.clear();
    }

    private static void setStatus(String mapKey, StatusEnum status){
        resultMap.put(mapKey, status);
        logger.info(String.format("%s :-> %s", mapKey, resultMap.get(mapKey)));
    }

    public static void mark (String testName, boolean result, String resultMessage) {
        String mapKey = String.format("%s.%s", testName.toLowerCase(), resultMessage.trim());
        try {
            setStatus(mapKey, result ? StatusEnum.PASS : StatusEnum.FAIL);
        } catch (Exception ex) {
            logger.fatal(String.format("An exception occurred: %s", ex.getMessage()));
        }
    }

    public static void markFinal (String testName, boolean result, String resultMessage) {
        String mapKey = String.format("%s.%s", testName.toLowerCase(), resultMessage.trim());
        try {
            setStatus(mapKey, result ? StatusEnum.PASS : StatusEnum.FAIL);
        } catch (Exception ex) {
            logger.fatal(String.format("An exception occurred: %s", ex.getMessage()));
        }
        List<StatusEnum> resultList = new ArrayList<>(resultMap.values());
        if(resultList.contains(StatusEnum.FAIL)) {
            logger.error(String.format("%s has failed", testName));
        }

        if(!resultList.contains(StatusEnum.FAIL)) {
            logger.info(String.format("%s has passed", testName));
        }
        assertFalse(resultList.contains(StatusEnum.FAIL));
        CheckPoint.clear();
    }

    enum StatusEnum {
        PASS{
            @Override
            public String toString() {
                return "Pass";
            }
        },
        FAIL{
            @Override
            public String toString() {
                return "Fail";
            }
        }

    }
}
