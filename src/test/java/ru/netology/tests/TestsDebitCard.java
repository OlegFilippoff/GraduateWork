package ru.netology.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.CardInfo;
import ru.netology.data.DataBase;
import ru.netology.pages.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;

public class TestsDebitCard {
    String url = System.getProperty("sut.url");


    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide().screenshots(true).savePageSource(false));
    }

    @SneakyThrows
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
//        DataBase.clearTables();
    }

    @BeforeEach
    public void setUp() {
        Configuration.headless = true;
        open(url);
    }

    @Test
    void shouldPaymentWithApprovedCard() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(1), plusYears(2), getValidHolderName(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.successfulPaymentDebitCard();
        String actual = DataBase.getStatusPayment();
        assertEquals("APPROVED", actual);
    }

    @Test
    void shouldCheckApprovedCardExpired() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getZeroMonth(), getZeroYear(), getValidHolderName(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkCardExpired();
        String actual = DataBase.getStatusPayment();
        assertEquals("APPROVED", actual);
    }

    @Test
    void shouldPaymentWithDeclinedCard() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getSecondCard(), getCurrentMonth(), plusYears(5), getValidHolderName(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.invalidPaymentDebitCard();
        String actual = DataBase.getStatusPayment();
        assertEquals("DECLINED", actual);
    }

    @Test
    void shouldRefuseDeclinedCardExpired() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getSecondCard(), getValidMonth(1), getZeroYear(), getValidHolderName(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkCardExpired();
        String actual = DataBase.getStatusPayment();
        assertEquals("DECLINED", actual);
    }

    @Test
    void shouldPaymentWithInvalidCardNumber() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                invalidCard(), getValidMonth(2), plusYears(3), getValidHolderName(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.invalidPaymentDebitCard();
    }

    @Test
    void shouldPaymentWithInvalidCardNumberShort() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                invalidCardOneMinus(), getValidMonth(5), plusYears(3), getValidHolderName(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldCheckInvalidCardZero() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                invalidCardZero(), getValidMonth(3), plusYears(1), getValidHolderName(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldCheckInvalidFullZeroCard() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                fullZeroCard(), getValidMonth(3), plusYears(1), getValidHolderName(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.invalidPaymentDebitCard();
    }

    @Test
    void shouldCheckInvalidCardOneDigit() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                invalidCardOneDigit(), getValidMonth(3), plusYears(1), getValidHolderName(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldCheckPaymentWithExpiredCard() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(10), plusYears(-1), getValidHolderName(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkCardExpired();
    }

    @Test
    void shouldCheckPaymentIncorrectCardExpired() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(-1), getCurrentYear(), getValidHolderName(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidCardValidityPeriod();
    }

    @Test
    void shouldPaymentCardValidMoreThanFiveYears() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getCurrentMonth(), plusYears(6), getValidHolderName(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidCardValidityPeriod();
    }

    @Test
    void shouldCheckCardInvalidYearOneDigit() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(2), oneDigitYear(), getValidHolderName(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidFormat();

    }

    @Test
    void shouldCheckNameInChinese() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(1), plusYears(3), getInvalidHolderName(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidOwner();
    }

    @Test
    void shouldCheckInvalidLongName() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(1), plusYears(3), getLongName(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidOwner();
    }

    @Test
    void shouldPaymentInvalidOwnerCardWithNumbers() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(4), plusYears(3),
                getHolderNamePlusDigits(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.incorrectOwner();
    }

    @Test
    void shouldPaymentInvalidOwnerCardOneLetterName() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(9), plusYears(3),
                getOneLetterName(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.incorrectOwner();
    }

    @Test
    void shouldPaymentInvalidOwnerCardWithSymbols() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(1), plusYears(1),
                getHolderNamePlusSymbols(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.incorrectOwner();
    }

    @Test
    void shouldCheckCardInvalidMonthInvalidPeriod() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getInvalidMonth(), plusYears(2), getValidHolderName(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidCardValidityPeriod();
    }

    @Test
    void shouldPaymentCardInvalidZeroMonth() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getZeroMonth(), plusYears(4), getValidHolderName(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidCardValidityPeriod();
    }

    @Test
    void shouldPaymentEmptyFieldNumberCard() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                null, getValidMonth(1), plusYears(2), getValidHolderName(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldPaymentEmptyFieldMonth() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), null, plusYears(2), getValidHolderName(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldPaymentEmptyFieldYears() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(2), null, getValidHolderName(), getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldPaymentEmptyFieldOwner() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(2), plusYears(3), null, getValidCVC());
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkEmptyField();
    }

    @Test
    void shouldPaymentEmptyFieldCvc() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(2), plusYears(3), getValidHolderName(), null);
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkEmptyField();
    }

    @Test
    void shouldPaymentEmptyAllField() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                null, null, null, null, null);
        var paymentPage = mainPage.paymentPage();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkAllFieldsAreRequired();
    }
}
