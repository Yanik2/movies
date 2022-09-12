package com.test.movie.controller;

import com.test.movie.dto.OrderDto;
import com.test.movie.exceptions.NotFoundException;
import com.test.movie.model.ApiError;
import com.test.movie.model.Order;
import com.test.movie.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> findAllOrders(@RequestParam(required = false) String search,
                                     @RequestParam Integer pageNumber,
                                     @RequestParam Integer pageSize) {
        return orderService.findAllOrders(search, pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public Order findById(@PathVariable Integer id) {
        return orderService.findById(id);
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderDto order) {
        return orderService.createOrder(order);
    }

    @PutMapping
    public Order updateOrder(@RequestBody OrderDto order) {
        return orderService.updateOrder(order);
    }

    @DeleteMapping("/{id}")
    public Integer deleteOrder(@PathVariable Integer id) {
        return orderService.deleteOrder(id);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiError handleException(NotFoundException ex) {
        ApiError apiError = new ApiError();
        apiError.setMessage(ex.getMessage());
        apiError.setTimestamp(LocalDateTime.now());
        return apiError;
    }
}
