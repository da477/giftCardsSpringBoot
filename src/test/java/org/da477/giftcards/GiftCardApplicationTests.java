package org.da477.giftcards;

import org.da477.giftcards.controller.CardRestControllerV1;
import org.da477.giftcards.model.Card;
import org.da477.giftcards.model.Status;
import org.da477.giftcards.model.TypeCard;
import org.hamcrest.Matchers;
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
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @WithAnonymousUser
    @Test
    public void shouldLoginPageForAnonymousUser() throws Exception {
        MvcResult result = mockMvc.perform(
                        get("/cards/"))
                .andExpect(status().is3xxRedirection()).andReturn();

        assertEquals("http://localhost/auth/login", result.getResponse().getRedirectedUrl());

    }

    @WithMockUser("admin@admin.com")
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

    @Test
    public void getLastOne() throws Exception {
        mockMvc.perform(get(REST_URL + "lastOne")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetAllCardsWithAdmin() throws Exception {

        MvcResult result = mockMvc.perform(
                        get(REST_URL)
                                .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertEquals(200, result.getResponse().getStatus());

    }

    @Ignore
    public void testGetExample() throws Exception {

        Long CARD1_ID = 123456789L;

        List<Card> cards = new ArrayList<>();
        Card card = new Card();

        card.setNumber(CARD1_ID);
        card.setGenerated(true);

        card.setTypeCard(TypeCard.SIMPLE);
        card.setOwner_id("215");
        card.setAmount(100F);
        card.setWithdrawal(0.0F);
        card.setStatus(Status.NEW);
        card.setPrint(false);

//        Mockito.when(cardService.getAll()).thenReturn(cards);
        mockMvc
                .perform(get(REST_URL)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                        .with(csrf()))
//                .andExpect(status().is3xxRedirection());
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$[0].number", Matchers.equalTo(CARD1_ID)))
                .andExpect((ResultMatcher) jsonPath("$[0].amount", Matchers.equalTo(100F)));
    }

}
