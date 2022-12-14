package ru.netology.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.CardInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    private final SelenideElement failureNotification = $(byText("Ошибка! Банк отказал в проведении операции."));
    private final SelenideElement successNotification = $(byText("Операция одобрена Банком."));
    private SelenideElement headingPayment = $$("h3.heading").find(exactText("Оплата по карте"));
    private SelenideElement cardNumberInput = $("input[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthInput = $("input[placeholder='08']");
    private SelenideElement yearInput = $("input[placeholder='22']");
    private SelenideElement cvcInput = $("input[placeholder='999']");
    private SelenideElement ownerInput = $$(".input__control").get(3);
    private SelenideElement continueButton = $$(".button").find(exactText("Продолжить"));

    public PaymentPage() {
        headingPayment.shouldBe(visible);
    }

    public void getFillCardDetails(CardInfo cardInfo) {
        cardNumberInput.setValue(cardInfo.getCardNumber());
        monthInput.setValue(cardInfo.getMonth());
        yearInput.setValue(cardInfo.getYear());
        ownerInput.setValue(cardInfo.getCardHolder());
        cvcInput.setValue(cardInfo.getCvc());
        continueButton.click();
    }

    public void successfulPaymentDebitCard() {
        $(".notification_status_ok")
                .shouldHave(text("Успешно Операция одобрена Банком."), Duration.ofSeconds(15)).shouldBe(visible);
    }

    public void invalidPaymentDebitCard() {
        $(".notification_status_error .notification__content")
                .shouldHave(text("Ошибка! Банк отказал в проведении операции."), Duration.ofSeconds(20)).shouldBe(visible);
    }

    public void checkInvalidFormat() {
        $(".input__sub").shouldBe(visible).shouldHave(text("Неверный формат"), Duration.ofSeconds(15));
    }

    public void checkInvalidCardValidityPeriod() {
        $(".input__sub").shouldBe(visible)
                .shouldHave(text("Неверно указан срок действия карты"), Duration.ofSeconds(15));
    }

    public void checkCardExpired() {
        $(".input__sub").shouldBe(visible)
                .shouldHave(text("Истёк срок действия карты"), Duration.ofSeconds(15));
    }

    public void checkInvalidOwner() {
        $(".input__sub").shouldBe(visible)
                .shouldHave(text("Введите имя и фамилию, указанные на карте"), Duration.ofSeconds(15));
    }

    public void checkEmptyField() {
        $(".input__sub").shouldBe(visible)
                .shouldHave(text("Поле обязательно для заполнения"), Duration.ofSeconds(15));
    }

    public void incorrectOwner() {
        $(".input__sub").shouldBe(visible)
                .shouldHave(text("Значение поля может содержать только латинские буквы и дефис"), Duration.ofSeconds(15));
    }

    public void checkAllFieldsAreRequired() {
        $$(".input__sub").shouldHave(CollectionCondition.size(5));

    }
}
