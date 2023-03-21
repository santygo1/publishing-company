package ru.danilspirin.publishingcompany.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CustomerProfitReport {

    Customer customer;

    Map<Book, AbsoluteBookRecord> booksWithProfit;


    public CustomerProfitReport(Customer customer, Map<Book, AbsoluteBookRecord> booksWithProfit) {
        this.customer = customer;
        this.booksWithProfit = booksWithProfit;
    }

    public long calculateAbsoluteProfit() {
        assert booksWithProfit != null : "booksWithProfit couldn't be null";

        int absoluteProfit = 0;
        for (Book book : booksWithProfit.keySet()) {
            absoluteProfit += booksWithProfit.get(book).getAbsoluteBooksProfit();
        }

        return absoluteProfit;
    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append("Заказчик : ").append(customer.getOwnerFullName().initials()).append("\s");
        for (Book book : booksWithProfit.keySet()) {
            AbsoluteBookRecord absoluteBookRecord = booksWithProfit.get(book);
            build.append("\t")
                    .append(book.getTitle())
                    .append(" - ")
                    .append("Количество: ").append(absoluteBookRecord.getAbsoluteBooksCount()).append(";")
                    .append("Выручка: ").append(absoluteBookRecord.getAbsoluteBooksProfit())
                    .append("\s");
        }
        build.append("Общая сумма: ").append(calculateAbsoluteProfit());
        return build.toString();
    }

    @Setter
    @Getter
    public static class AbsoluteBookRecord {
        private long absoluteBooksCount;
        private long absoluteBooksProfit;

        public AbsoluteBookRecord(long absoluteBooksCount, long absoluteBooksProfit) {
            this.absoluteBooksCount = absoluteBooksCount;
            this.absoluteBooksProfit = absoluteBooksProfit;
        }
    }
}
