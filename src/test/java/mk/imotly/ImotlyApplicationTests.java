package mk.imotly;

import mk.imotly.model.Ad;
import mk.imotly.model.SavedSearch;
import mk.imotly.service.SupabaseService;
import mk.imotly.service.impl.SupabaseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import mk.imotly.model.Ad;
import mk.imotly.model.SavedSearch;
import mk.imotly.repository.SupabaseClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImotlyApplicationTests {

    @Test
    void contextLoads() {
    }

    private SupabaseClient supabaseClient;
    private JavaMailSender mailSender;
    private RestTemplate restTemplate;
    private SupabaseServiceImpl service;

    @BeforeEach
    void setUp() {
        supabaseClient = mock(SupabaseClient.class);
        mailSender = mock(JavaMailSender.class);
        restTemplate = mock(RestTemplate.class);

        service = new SupabaseServiceImpl(restTemplate, supabaseClient, mailSender);
    }

    @Test
    void testAddAd_sendsEmailWhenAdMatchesSubscription() {

        Ad ad = new Ad();
        ad.setForSale(true);
        ad.setPrice(150000);
        ad.setLocation("Скопје");
        ad.setNumRooms(3);
        ad.setFloor(2);
        ad.setNumFloors(5);
        ad.setSize(80);
        ad.setHeating("Централно");
        ad.setTypeOfObj("Стан");
        ad.setTerrace(true);
        ad.setParking(true);
        ad.setFurnished(true);
        ad.setBasement(true);
        ad.setNewBuilding(true);
        ad.setDuplex(false);
        ad.setRenovated(false);
        ad.setLift(true);
        ad.setState("Нов");
        ad.setUrl("http://example.com/ad/123");

        SavedSearch subscription = new SavedSearch();
        subscription.setUserId(1L);
        subscription.setForSale(true);
        subscription.setMinPrice(100000);
        subscription.setMaxPrice(200000);
        subscription.setLocation("Скопје");
        subscription.setNumRooms(3);
        subscription.setFloor(2);
        subscription.setNumFloors(5);
        subscription.setSize(80);
        subscription.setHeating("Централно");
        subscription.setTypeOfObj("Стан");
        subscription.setTerrace(true);
        subscription.setParking(true);
        subscription.setFurnished(true);
        subscription.setBasement(true);
        subscription.setNewBuilding(true);
        subscription.setDuplex(false);
        subscription.setRenovated(false);
        subscription.setLift(true);
        subscription.setState("Нов");

        when(supabaseClient.getAllSubscriptions()).thenReturn(List.of(subscription));
        when(supabaseClient.getUserEmailById(1L)).thenReturn("user@example.com");

        service.addAd(ad);

        ArgumentCaptor<SimpleMailMessage> mailMessageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(mailMessageCaptor.capture());

        SimpleMailMessage sentMessage = mailMessageCaptor.getValue();
        assertEquals("user@example.com", sentMessage.getTo()[0]);
        assertEquals("New Ad Matching Your Preferences", sentMessage.getSubject());
        assertTrue(sentMessage.getText().contains(ad.getUrl()));

        verify(supabaseClient, times(1)).addAd(ad);
    }

    @Test
    void testAddAd_noEmailWhenAdDoesNotMatchSubscription() {
        Ad ad = new Ad();
        ad.setForSale(false);

        SavedSearch subscription = new SavedSearch();
        subscription.setUserId(1L);
        subscription.setForSale(true);

        when(supabaseClient.getAllSubscriptions()).thenReturn(List.of(subscription));
        when(supabaseClient.getUserEmailById(1L)).thenReturn("user@example.com");

        service.addAd(ad);

        verify(mailSender, never()).send(any(SimpleMailMessage.class));
        verify(supabaseClient, times(1)).addAd(ad);

}}
