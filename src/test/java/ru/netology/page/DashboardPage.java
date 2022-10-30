package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement buttonReload = $("[data-test-id='action-reload']");


    public void verifyIsDashboardPage() {
        heading.shouldBe(visible).shouldHave(exactText("  Личный кабинет"));
    }

    public TransferPage clickButtonToTransfer(String cardNumber) {
        cards.findBy(text("**** **** **** 000" + cardNumber)).$("button").click();
        return new TransferPage();
    }

    public ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public String getCardBalance(String cardSelector) {
        var text = cards.findBy(text("**** **** **** 000" + cardSelector)).getText();
        return extractBalance(text);
    }

    private String extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return value;
    }

    public void clickAtReloadButton() {
        buttonReload.click();
    }
}
