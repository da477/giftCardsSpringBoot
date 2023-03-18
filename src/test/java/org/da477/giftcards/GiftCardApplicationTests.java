package org.da477.giftcards;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.da477.giftcards.controller.CardRestControllerV1;
import org.da477.giftcards.model.Card;
import org.da477.giftcards.service.CardService;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class GiftCardApplicationTests {

    private static final String REST_URL = CardRestControllerV1.REST_URL + '/';

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CardService cardService;

    @Test
    void contextLoads() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    public void testCsrf() throws Exception {
        mockMvc
                .perform(get(REST_URL)
                        .with(csrf()));
    }

    @Ignore //not ready
    public void loginPage() throws Exception {
        mockMvc.perform(formLogin("/auth/login").user("user").password("admin"))
                .andExpect(redirectedUrl("/auth/login?error"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void shouldLoginPageForAnonymousUser() throws Exception {
                mockMvc.perform(
                        get("/cards/"))
                .andExpect(status().is3xxRedirection())
                .andDo(print())
                .andExpect(redirectedUrl("http://localhost/auth/login"));
    }

    @Test
    public void shouldAllowGetAllWhenUserIsAdmin() throws Exception {
        this.mockMvc.perform(get(REST_URL)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Ignore
    @WithMockUser
    public void getForbiddenForUser() throws Exception {
        mockMvc.perform(get(REST_URL + "lastOne"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    public void getLastOne_OverRestWhenUserIsAdmin() throws Exception {

        Card card = cardService.getLastOne();
        System.out.println(card.getNumber());

        mockMvc.perform(get(REST_URL + "lastOne")
//                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
//                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.number").value(card.getNumber()));

    }

    @Test
    public void givenNumber_whenGetNotExistingCard_thenStatus404() throws Exception {

        mockMvc.perform(get(REST_URL + "1")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                        .with(csrf())
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenCard_whenGetCard_thenStatus200() throws Exception {
        Card card = cardService.getLastOne();
        mockMvc.perform(
                        get(REST_URL + "{id}", card.getNumber())
                                .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(card)));
    }

    @Test
    public void giveCard_whenUpdate_thenStatus200andUpdatedReturns() throws Exception {
        Card card = cardService.getLastOne();
        Float curWithdrawal = card.getWithdrawal();
        card.setWithdrawal(curWithdrawal+1); //do not createOrUpdate this here

        mockMvc.perform(
                        post(REST_URL, card)
                                .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(card))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.withdrawal").value(curWithdrawal+1))
                .andExpect(jsonPath("$.number").value(card.getNumber()));
    }

    @Test
    public void testGetAllCardsWithAdmin() throws Exception {

        MvcResult result = mockMvc.perform(
                        get(REST_URL)
                                .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(200, result.getResponse().getStatus());

    }

}
