package peaksoft.house.gadgetariumb9.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import peaksoft.house.gadgetariumb9.config.security.JwtService;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.models.SubProduct;
import peaksoft.house.gadgetariumb9.models.User;
import peaksoft.house.gadgetariumb9.services.serviceImpl.FavoriteServiceImpl;
import peaksoft.house.gadgetariumb9.template.FavoriteTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FavoriteServiceImplTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubProductRepository subProductRepository;

    @InjectMocks
    private FavoriteServiceImpl favoritesService;

    @Mock
    private FavoriteTemplate favoriteTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        User fakeUser = new User();
        when(jwtService.getAuthenticationUser()).thenReturn(fakeUser);
    }

    @Test
    void testAddAndDeleteFavorite() {
        User user = jwtService.getAuthenticationUser();
        List<Long> favorites = new ArrayList<>();
        user.setFavorite(favorites);
        when(jwtService.getAuthenticationUser()).thenReturn(user);

        long subProductId = 123L;
        SubProduct subProduct = new SubProduct();
        when(subProductRepository.findById(subProductId)).thenReturn(Optional.of(subProduct));

        SimpleResponse addResponse = favoritesService.addAndDeleteFavorite(subProductId);
        assertTrue(user.getFavorite().contains(subProductId));
        verify(userRepository, times(1)).save(user);
        assertEquals(HttpStatus.OK, addResponse.getStatus());
        assertEquals("Successfully added or removed from favorites.", addResponse.getMessage());

        SimpleResponse deleteResponse = favoritesService.addAndDeleteFavorite(subProductId);
        assertFalse(user.getFavorite().contains(subProductId));
        verify(userRepository, times(2)).save(user);
        assertEquals(HttpStatus.OK, deleteResponse.getStatus()); // Правильно указываем код состояния
        assertEquals("Successfully added or removed from favorites.", deleteResponse.getMessage());
    }

    @Test
    void clearFavorite() {
        User user = new User();
        List<Long> favorites = new ArrayList<>();
        favorites.add(1L);
        favorites.add(2L);
        favorites.add(3L);
        user.setFavorite(favorites);

        when(jwtService.getAuthenticationUser()).thenReturn(user);
        SimpleResponse response = favoritesService.clearFavorite();
        verify(userRepository).save(user);
        assertTrue(user.getFavorite().isEmpty());
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Successfully cleared favorites.", response.getMessage());
    }

    @Test
    void getAllFavorite() {
        List<SubProductResponse> expectedFavorites = new ArrayList<>();
        expectedFavorites.add(new SubProductResponse());
        when(favoriteTemplate.getAllFavorite()).thenReturn(expectedFavorites);
        List<SubProductResponse> actualFavorites = favoritesService.getAllFavorite();
        assertEquals(expectedFavorites.size(), actualFavorites.size());
        verify(favoriteTemplate, times(1)).getAllFavorite();
    }
}