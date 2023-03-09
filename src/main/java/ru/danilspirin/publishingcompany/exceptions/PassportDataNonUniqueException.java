package ru.danilspirin.publishingcompany.exceptions;

public class PassportDataNonUniqueException extends IllegalArgumentException{

    // TODO: i18n
    private static String msg = "Паспортные данные добавляемого писателя не уникальны. " +
            "Измените серию или номер паспорта и повторите попытку.";

    public PassportDataNonUniqueException(){
        super(msg);
    }
}
