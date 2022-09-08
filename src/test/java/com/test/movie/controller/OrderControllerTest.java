package com.test.movie.controller;

import com.test.movie.dto.OrderDto;
import com.test.movie.exceptions.NotFoundException;
import com.test.movie.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

public class OrderControllerTest {
    private OrderController orderController;
    private OrderService orderServiceMock;

    @BeforeEach
    public void setUp() {
        orderServiceMock = Mockito.mock(OrderService.class);
        orderController = new OrderController(orderServiceMock);
    }

    @Test
    public void shouldCallOrderServiceFindAll() {
        var params = new HashMap<String, String>();

        orderController.findAllOrders(params);

        verify(orderServiceMock).findAllOrders(params);
    }

    @Test
    public void shouldCallOrderServiceFindById() {
        orderController.findById(1);

        verify(orderServiceMock).findById(1);
    }

    @Test
    public void shouldCallCreateOrder() {
        var orderDto = new OrderDto();

        orderController.createOrder(orderDto);

        verify(orderServiceMock).createOrder(orderDto);
    }

    @Test
    public void shouldCallUpdateOrder() {
        var orderDto = new OrderDto();

        orderController.updateOrder(orderDto);

        verify(orderServiceMock).updateOrder(orderDto);
    }

    @Test
    public void shouldCallDeleteOrder() {
        orderController.deleteOrder(1);

        verify(orderServiceMock).deleteOrder(1);
    }

    @Test
    public void shouldReturnApiError() {
        var ex = new NotFoundException("Message");

        var actualResult = orderController.handleException(ex);

        assertEquals("Message", actualResult.getMessage());
        assertNotNull(actualResult.getTimestamp());
    }
}