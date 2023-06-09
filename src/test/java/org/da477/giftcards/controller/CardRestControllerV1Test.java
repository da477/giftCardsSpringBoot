package org.da477.giftcards.controller;

import org.da477.giftcards.model.Card;
import org.da477.giftcards.service.CardService;
import org.da477.giftcards.utils.JsonUtil;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.da477.giftcards.controller.CardRestControllerV1.REST_URL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"ADMIN"})
class CardRestControllerV1Test {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CardService service;

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {

        MvcResult result = this.mockMvc.perform(get(REST_URL + "/lastOne"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(service.getLastOne().getId()))
                .andExpect(jsonPath("$.number").value(service.getLastOne().getNumber()))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), containsString("\"id\":"));
    }

    @Ignore
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string(containsString("For Admin Only")));
    }

    @Test
    public void contextLoads() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Ignore
    void registerInvalid() throws Exception {
        Card newCard = new Card();
        newCard.setAmount(1F);
        newCard.setWithdrawal(0F);
        String body = JsonUtil.writeValue(newCard);
        mockMvc.perform(post(REST_URL)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
//                .andExpect(jsonPath("$.type").value(ErrorType.VALIDATION_ERROR.name()));
    }

    @Ignore
    void getAllCard() {
    }

    @Ignore
    void getLastOne() {
    }

    @Ignore
    void getCardByNumber() {
    }

    @Ignore
    void saveCard() {
    }

    @Ignore
    void delete() {
    }
}