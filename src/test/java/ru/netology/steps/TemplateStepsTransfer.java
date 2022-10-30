package ru.netology.steps;

import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Тогда;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertEquals;

public class TemplateStepsTransfer {
    private static LoginPage loginPage;
    private static DashboardPage dashboardPage;
    private static TransferPage transferPage;

    @Пусть("Пользователь залогинен с именем {string} и паролем {string}")
    public void loginInProfile(String login, String password) {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var verificationPage = loginPage.validLogin(login, password);
        dashboardPage = verificationPage.validVerify(DataHelper.getVerificationCode());
    }

    @Когда("Пользователь переводит {string} рублей с карты с номером {string} на свою {string} карту с главной страницы")
    public void transferMoneyToSecondCard(String amount, String topUpCardNumber, String fromCardNumber) {
        var transferPage = dashboardPage.clickButtonToTransfer(fromCardNumber);
        dashboardPage = transferPage.validTransferMoney(amount, topUpCardNumber);
    }

    @Тогда("Баланс его {string} карты из списка на главной странице должен стать {string} рублей")
    public void checkCardBalance(String fromCardNumber, String expectedBalance) {
        var actualCardBalance = dashboardPage.getCardBalance(fromCardNumber);
        assertEquals(expectedBalance, actualCardBalance);
    }
}
