package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
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

public class CreditCardTests {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide().screenshots(true).savePageSource(false));
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
        //DataBase.clearTables();
    }

    @BeforeEach
    public void setUp() {
        // Configuration.headless = true;
        open("http://localhost:8080");
    }

    @Test
    void shouldMakePaymentWithApprovedCard() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(1), plusYears(4), getValidHolderName(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.successfulPaymentCreditCard();
        String actual = DataBase.getStatusCredit();
        assertEquals("APPROVED", actual);
    }

    @Test
    void shouldCheckPaymentWithApprovedCardExpired() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getCurrentMonth(), getCurrentYear(), getValidHolderName(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.successfulPaymentCreditCard();
        String actual = DataBase.getStatusCredit();
        assertEquals("APPROVED", actual);
    }

    @Test
    void shouldIgnorePaymentWithDeclinedCard() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getSecondCard(), getValidMonth(0), plusYears(1), getValidHolderName(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.invalidPaymentCreditCard();
        String actual = DataBase.getStatusCredit();
        assertEquals("DECLINED", actual);
    }

    @Test
    void shouldIgnorePaymentWithDeclinedCardExpired() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getSecondCard(), getCurrentMonth(), getCurrentYear(), getValidHolderName(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.invalidPaymentCreditCard();
        String actual = DataBase.getStatusCredit();
        assertEquals("DECLINED", actual);
    }

    @Test
    void shouldIgnorePaymentWithInvalidCardNumber() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                invalidCard(), getValidMonth(2), plusYears(1), getValidHolderName(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.invalidPaymentCreditCard();
    }

    @Test
    void shouldPaymentWithInvalidCardNumberShort() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                invalidCardOneMinus(), getValidMonth(5), plusYears(3), getValidHolderName(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.checkInvalidFormat();
    }

    @Test
    void shouldCheckInvalidCardZero() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                invalidCardZero(), getValidMonth(3), plusYears(1), getValidHolderName(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.checkInvalidFormat();
    }

    @Test
    void shouldCheckInvalidFullZeroCard() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                fullZeroCard(), getValidMonth(3), plusYears(1), getValidHolderName(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.invalidPaymentCreditCard();
    }

    @Test
    void shouldCheckInvalidCardOneDigit() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                invalidCardOneDigit(), getValidMonth(3), plusYears(1), getValidHolderName(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.checkInvalidFormat();
    }

    @Test
    void shouldCheckPaymentWithExpiredCard() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(10), plusYears(-1), getValidHolderName(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.checkCardExpired();
    }

    @Test
    void shouldCheckPaymentIncorrectCardExpired() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(-1), getCurrentYear(), getValidHolderName(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.checkInvalidCardValidityPeriod();
    }

    @Test
    void shouldPaymentCardValidMoreThanFiveYears() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getCurrentMonth(), plusYears(6), getValidHolderName(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.checkInvalidCardValidityPeriod();
    }

    @Test
    void shouldCheckCardInvalidYearOneDigit() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(2), oneDigitYear(), getValidHolderName(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.checkInvalidFormat();
    }

    @Test
    void shouldCheckNameInChinese() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(1), plusYears(3), getInvalidHolderName(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.checkInvalidHolder();
    }

    @Test
    void shouldCheckInvalidLongName() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(1), plusYears(3), getLongName(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.checkInvalidHolder();
    }

    @Test
    void shouldIgnorePaymentInvalidHolderNameWithNumbers() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(4), plusYears(3),
                getHolderNamePlusDigits(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.incorrectHolder();
    }

    @Test
    void shouldIgnorePaymentInvalidOneLetterName() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(9), plusYears(3),
                getOneLetterName(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.incorrectHolder();
    }

    @Test
    void shouldIgnorePaymentInvalidHolderNameWithSymbols() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(1), plusYears(1),
                getHolderNamePlusSymbols(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.incorrectHolder();
    }

    @Test
    void shouldCheckCardInvalidMonthInvalidPeriod() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getInvalidMonth(), plusYears(2), getValidHolderName(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.checkInvalidCardValidityPeriod();
    }

    @Test
    void shouldIgnoreInvalidZeroMonth() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getZeroMonth(), plusYears(4), getValidHolderName(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.checkInvalidCardValidityPeriod();
    }

    @Test
    void shouldPaymentEmptyFieldNumberCard() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                null, getValidMonth(1), plusYears(2), getValidHolderName(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.checkInvalidFormat();
    }

    @Test
    void shouldPaymentEmptyFieldMonth() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), null, plusYears(2), getValidHolderName(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.checkInvalidFormat();
    }

    @Test
    void shouldPaymentEmptyFieldYears() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(2), null, getValidHolderName(), getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.checkInvalidFormat();
    }

    @Test
    void shouldPaymentEmptyFieldOwner() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(2), plusYears(3), null, getValidCVC());
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.checkEmptyField();
    }

    @Test
    void shouldPaymentEmptyFieldCvc() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                getFirstCard(), getValidMonth(2), plusYears(3), getValidHolderName(), null);
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.checkEmptyField();
    }

    @Test
    void shouldPaymentEmptyAllField() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(
                null, null, null, null, null);
        var creditPage = mainPage.creditPage();
        creditPage.getCardFieldsFilled(card);
        creditPage.checkAllFieldsAreRequired();
    }
}
