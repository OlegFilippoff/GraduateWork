package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {
    private SelenideElement headingMain = $("h2.heading");
    private SelenideElement paymentButton = $$(".button").find(exactText("Купить"));
    private SelenideElement creditPay = $$(".button").find(exactText("Купить в кредит"));

    public MainPage() {
        headingMain.should(visible);
    }

    public PaymentPage paymentPage() {
        paymentButton.click();
        return new PaymentPage();
    }

    public CreditPage creditPage() {
        creditPay.click();
        return new CreditPage();
    }
}




