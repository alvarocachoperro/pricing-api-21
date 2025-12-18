package es.ecommerce.demo.app.service.impl;

import es.ecommerce.demo.app.repository.PriceRepository;
import es.ecommerce.demo.app.service.exception.IErrorMessages;
import es.ecommerce.demo.app.service.exception.PriceNotFoundException;
import es.ecommerce.demo.domain.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PriceServiceImplTest {

    private PriceRepository priceRepository;
    private PriceServiceImpl priceService;

    @BeforeEach
    void setUp() {
        priceRepository = mock(PriceRepository.class);
        priceService = new PriceServiceImpl(priceRepository);
    }

    @Test
    @DisplayName("El puerto nos devuelve una lista con 0 elementos -> lanza PriceNotFoundException")
    void givenEmptyList_whenGetPrice_thenThrowsNotFound() {
        when(priceRepository.findByDateProductAndBrand(Mockito.any(LocalDateTime.class), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(List.of());

        PriceNotFoundException ex = assertThrows(PriceNotFoundException.class, () ->
                priceService.getPrice(LocalDateTime.now(), 35455L, 1L)
        );

        assertEquals(IErrorMessages.ERROR_NOTFOUND, ex.getMessage());
    }

    @Test
    @DisplayName("El puerto nos devuelve una lista con 1 elemento -> devuelve ese elemento")
    void givenSingleElement_whenGetPrice_thenReturnsThatElement() {
        Price only = mock(Price.class);
        when(only.getPriority()).thenReturn(0);

        when(priceRepository.findByDateProductAndBrand(Mockito.any(LocalDateTime.class), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(List.of(only));

        Price result = priceService.getPrice(LocalDateTime.now(), 35455L, 1L);
        assertSame(only, result, "Debe devolver el único elemento de la lista");
    }

    @Test
    @DisplayName("El puerto nos devuelve una lista con 2 o más elementos -> devuelve el de mayor prioridad")
    void givenMultipleElements_whenGetPrice_thenReturnsHighestPriority() {
        Price low = mock(Price.class);
        Price high = mock(Price.class);
        Price mid = mock(Price.class);

        when(low.getPriority()).thenReturn(0);
        when(mid.getPriority()).thenReturn(5);
        when(high.getPriority()).thenReturn(10);

        when(priceRepository.findByDateProductAndBrand(Mockito.any(LocalDateTime.class), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(List.of(low, high, mid));

        Price result = priceService.getPrice(LocalDateTime.now(), 35455L, 1L);
        assertSame(high, result, "Debe devolver el elemento con mayor prioridad");
    }
}
