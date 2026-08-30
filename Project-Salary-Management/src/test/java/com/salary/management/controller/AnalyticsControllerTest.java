package com.salary.management.controller;

import com.salary.management.dto.AnalyticsDtos;
import com.salary.management.service.AnalyticsService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AnalyticsControllerTest {

    @Mock
    private AnalyticsService analyticsService;

    @InjectMocks
    private AnalyticsController analyticsController;


    // =========================================================
    // TEST 1: GET SUMMARY
    // =========================================================

    @Test
    void shouldGetSummary() {

        AnalyticsDtos.Summary summary =
                new AnalyticsDtos.Summary(
                        10001L,
                        new BigDecimal("750000.00"),
                        new BigDecimal("300000"),
                        new BigDecimal("1500000")
                );


        when(analyticsService.getSummary())
                .thenReturn(summary);


        AnalyticsDtos.Summary result =
                analyticsController.getSummary();


        assertNotNull(result);


        assertEquals(
                10001L,
                result.totalEmployees()
        );


        assertEquals(
                new BigDecimal("750000.00"),
                result.averageSalary()
        );


        assertEquals(
                new BigDecimal("300000"),
                result.minimumSalary()
        );


        assertEquals(
                new BigDecimal("1500000"),
                result.maximumSalary()
        );


        verify(analyticsService)
                .getSummary();
    }


    // =========================================================
    // TEST 2: EMPTY SUMMARY
    // =========================================================

    @Test
    void shouldReturnZeroSummary() {

        AnalyticsDtos.Summary summary =
                new AnalyticsDtos.Summary(
                        0L,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO
                );


        when(analyticsService.getSummary())
                .thenReturn(summary);


        AnalyticsDtos.Summary result =
                analyticsController.getSummary();


        assertNotNull(result);


        assertEquals(
                0L,
                result.totalEmployees()
        );


        assertEquals(
                BigDecimal.ZERO,
                result.averageSalary()
        );


        assertEquals(
                BigDecimal.ZERO,
                result.minimumSalary()
        );


        assertEquals(
                BigDecimal.ZERO,
                result.maximumSalary()
        );


        verify(analyticsService)
                .getSummary();
    }
}