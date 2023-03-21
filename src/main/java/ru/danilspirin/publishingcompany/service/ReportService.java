package ru.danilspirin.publishingcompany.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.danilspirin.publishingcompany.models.Book;
import ru.danilspirin.publishingcompany.models.Customer;
import ru.danilspirin.publishingcompany.models.CustomerProfitReport;
import ru.danilspirin.publishingcompany.models.Order;
import ru.danilspirin.publishingcompany.repository.OrderRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class ReportService {

    OrderRepository orderRepository;

    public List<Order> getRangeDateOrders(LocalDate dateFrom, LocalDate dateTo) {
        return orderRepository.findByFinishDateIsBetween(dateFrom, dateTo);
    }

    public List<CustomerProfitReport> getCustomersProfitReport(List<Order> orders) {
        List<CustomerProfitReport> customersProfitReport = new ArrayList<>();

        Map<Customer, List<Order>> customerWithBooks = orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer));

        for (Customer customer : customerWithBooks.keySet()) {
            CustomerProfitReport report = new CustomerProfitReport(customer, new HashMap<>());

            Map<Book, List<Order>> bookOrderMap =
                    customerWithBooks.get(customer).stream()
                            .collect(Collectors.groupingBy(Order::getBook));

            for (Book book : bookOrderMap.keySet()) {
                long absoluteBooksCount = bookOrderMap.get(book).stream()
                        .map(Order::getBooksCount)
                        .reduce(0, Integer::sum);
                /*
                    Общий профит с книги для данного заказчика высчитываем по формуле
                    (Цена продажи - Себестоимость) * количество экземпляров
                 */
                long absoluteBookProfit = (book.getSellingPrice() - book.getCostPrice()) * absoluteBooksCount;
                report.getBooksWithProfit().put(
                        book,
                        new CustomerProfitReport.AbsoluteBookRecord(absoluteBooksCount, absoluteBookProfit)
                );
            }

            customersProfitReport.add(report);
        }
        return customersProfitReport;
    }

    public long getAbsoluteProfit(List<CustomerProfitReport> reports){
        return reports.stream()
                .map(CustomerProfitReport::calculateAbsoluteProfit)
                .reduce(0L, Long::sum);
    }

}
