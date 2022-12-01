package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
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

}
