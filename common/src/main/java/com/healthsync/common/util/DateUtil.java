// common/src/main/java/com/healthsync/common/util/DateUtil.java
package com.healthsync.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 날짜 관련 유틸리티 클래스입니다.
 *
 * @author healthsync-team
 * @version 1.0
 */
public class DateUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 현재 날짜를 문자열로 반환합니다.
     *
     * @return 현재 날짜 (yyyy-MM-dd 형식)
     */
    public static String getCurrentDate() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    /**
     * 현재 날짜시간을 문자열로 반환합니다.
     *
     * @return 현재 날짜시간 (yyyy-MM-dd HH:mm:ss 형식)
     */
    public static String getCurrentDateTime() {
        return LocalDateTime.now().format(DATETIME_FORMATTER);
    }

    /**
     * 생년월일로부터 나이를 계산합니다.
     *
     * @param birthDate 생년월일 (yyyy-MM-dd 형식)
     * @return 나이
     */
    public static int calculateAge(String birthDate) {
        LocalDate birth = LocalDate.parse(birthDate, DATE_FORMATTER);
        return (int) ChronoUnit.YEARS.between(birth, LocalDate.now());
    }

    /**
     * LocalDate 생년월일로부터 나이를 계산합니다.
     *
     * @param birthDate 생년월일
     * @return 나이
     */
    public static int calculateAge(LocalDate birthDate) {
        return (int) ChronoUnit.YEARS.between(birthDate, LocalDate.now());
    }

    /**
     * 두 날짜 사이의 일수를 계산합니다.
     *
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @return 일수
     */
    public static long getDaysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * 문자열을 LocalDate로 변환합니다.
     *
     * @param dateString 날짜 문자열 (yyyy-MM-dd 형식)
     * @return LocalDate
     */
    public static LocalDate parseDate(String dateString) {
        return LocalDate.parse(dateString, DATE_FORMATTER);
    }

    /**
     * LocalDate를 문자열로 변환합니다.
     *
     * @param date LocalDate
     * @return 날짜 문자열 (yyyy-MM-dd 형식)
     */
    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }
}