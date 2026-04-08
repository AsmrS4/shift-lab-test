package com.shift.crm.api.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorConstants {
    public static final String MIN_PAGE_ERROR_MSG = "Минимальное значение для параметра page: 0";
    public static final String MIN_ELEMENTS_ON_PAGE_SIZE_ERROR_MSG = "Минимальное значение для параметра size: 1";
    public static final String MIN_AMOUNT_VALUE = "Минимальное значение для параметра amount: 1";
    public static final String MAX_AMOUNT_VALUE = "Передано слишком большое значение параметра amount";
    public static final String MAX_BOUND_VALUE_ERROR = "Передано слишкоом большое значение";
    public static final String POSITIVE_VALUE_ERROR_MSG = "Значение должно быть больше нуля";
    public static final String POSITIVE_OR_ZERO_VALUE_ERROR_MSG = "Значение должно быть больше или равно нулю";
    public static final String NOT_BLANK_MSG = "Поле обязательно к заполнению";
    public static final String AVAILABLE_NAME_PARAMETER_SIZE = "Допустимая длина параметра \"name\": от 3 до 100 символов";
    public static final String AVAILABLE_CONTACT_INFO_PARAMETER_SIZE = "Допустимая длина параметра \"contactInfo\": от 3 до 100 символов";
}
