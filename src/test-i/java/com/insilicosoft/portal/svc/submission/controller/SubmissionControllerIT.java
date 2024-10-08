package com.insilicosoft.portal.svc.submission.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;

import com.insilicosoft.portal.svc.submission.SubmissionIdentifiers;
import com.insilicosoft.portal.svc.submission.config.SecurityConfig;
import com.insilicosoft.portal.svc.submission.service.InputProcessorService;
import com.insilicosoft.portal.svc.submission.service.SubmissionService;

@WebMvcTest(SubmissionController.class)
@Import(SecurityConfig.class)
public class SubmissionControllerIT {

  private static final MediaType textWithCharset = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);
  private static final GrantedAuthority userRole = new SimpleGrantedAuthority("ROLE_".concat(SubmissionIdentifiers.ROLE_USER));

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private InputProcessorService mockInputProcessorService;
  @MockBean
  private JwtDecoder mockJwtDecoder;
  @MockBean
  private SubmissionService mockSubmissionService;

  @DisplayName("Test GET method(s)")
  @Nested
  class GetMethods {
    @DisplayName("Success")
    @Test
    void testGet() throws Exception {
      final String getMessage = "Message from get!";

      when(mockInputProcessorService.get()).thenReturn(getMessage);

      mockMvc.perform(get(SubmissionIdentifiers.REQUEST_MAPPING_SUBMISSION).with(jwt().authorities(userRole)))
             //.andDo(print())
             .andExpect(status().isOk())
             .andExpect(content().contentType(textWithCharset))
             .andExpect(content().string(getMessage));

      verify(mockInputProcessorService).get();
    }
  }

}