package com.solo.toycommentservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName(value = "passport 변환 및 활용 테스트")
public class PassportUtilTest {

    @Test
    @DisplayName(value = "passport에서 username 추출 테스트")
    public void usernameTest() throws JsonProcessingException {

        final PassportUtil passportUtil = new PassportUtil();
        final String testJson = "{ \"username\": \"test\", \"role\": \"ROLE_ADMIN\" }";

        final String username = passportUtil.getUsername(testJson);

        Assertions.assertEquals("test", username);
    }
}
