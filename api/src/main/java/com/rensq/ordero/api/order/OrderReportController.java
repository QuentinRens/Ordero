package com.rensq.ordero.api.order;


import com.rensq.ordero.service.order.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/order_report")
public class OrderReportController {

    private OrderService orderService;
    private OrderReportMapper orderReportMapper;

    @Inject
    public OrderReportController(OrderService orderService, OrderReportMapper orderReportMapper) {
        this.orderService = orderService;
        this.orderReportMapper = orderReportMapper;
    }

    @PostMapping (consumes = APPLICATION_JSON_VALUE, produces =APPLICATION_JSON_VALUE )
    @ResponseStatus(HttpStatus.OK)
    public OrderReportDto getOrderReport (@RequestBody  UUID customerID){
        return orderReportMapper.toDto(orderService.getOrderReport(customerID));
    }
}
