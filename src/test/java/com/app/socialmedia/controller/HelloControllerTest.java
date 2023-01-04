package com.app.socialmedia.controller;

import com.app.socialmedia.service.AlgoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(HelloController.class)
@MockBean(JpaMetamodelMappingContext.class)
class HelloControllerTest {

    @Autowired
    private AlgoService algoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    @DisplayName("자릿수 합")
    void sumOfDigit() throws Exception {
        // when 여기에서는 Service는 검증 대상이 아님
        when(algoService.sum(687L)) // 값을 지정할 수 있으나 의미가 없다.
                .thenReturn(21L);

        mockMvc.perform(get("/api/v1/hello/687")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("21"));
    }
}