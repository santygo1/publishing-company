package ru.danilspirin.publishingcompany.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.danilspirin.publishingcompany.models.CustomerProfitReport;
import ru.danilspirin.publishingcompany.service.ReportService;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Controller
@RequestMapping("/report")
public class ReportController {

    ReportService service;

    @GetMapping()
    public String createReport(
            @RequestParam(value = "createDate",required = false) LocalDate dateFrom,
            @RequestParam(value = "finishDate", required = false) LocalDate dateTo,
            Model model){
        if (dateFrom != null &&
                dateTo != null &&
                (dateFrom.isBefore(dateTo)|| dateFrom.isEqual(dateTo))
        ){
            model.addAttribute("dateFrom", dateFrom);
            model.addAttribute("dateTo", dateTo);
            List<CustomerProfitReport> customersProfitReport = service.getCustomersProfitReport(
                    service.getRangeDateOrders(dateFrom, dateTo)
            );
            model.addAttribute("absoluteProfit", service.getAbsoluteProfit(customersProfitReport));
            model.addAttribute("customersProfitReport", customersProfitReport);
        }
        return "report";
    }
}
