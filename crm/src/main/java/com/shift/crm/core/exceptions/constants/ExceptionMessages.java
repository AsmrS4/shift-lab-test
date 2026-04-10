package com.shift.crm.core.exceptions.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionMessages {
    public static final String ENTITY_NOT_FOUND_MSG = "Сущность не найдена.";
    public static final String SELLER_NOT_FOUND_MSG = "Сущность Seller c указанным id не найдена.";
    public static final String TRANSACTION_NOT_FOUND_MSG = "Сущность Transaction c указанным id не найдена.";
    public static final String ENTITY_DELETED_MSG = "Сущность с указанным id уже была удалена.";

    public static final String INTERNAL_SERVER_ERROR_MSG = "Произошла ошибка на стороне сервера.";
    public static final String VALIDATION_ERROR_MSG = "Переданы неверные параметры";
}
