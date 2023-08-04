package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryPopupsTest {

    // тесты на выбор города
    @Test
    void shouldPassWhenAllDataIsCorrectTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=name] .input__control").setValue("Васильев Андрей");
        form.$("[data-test-id=phone] .input__control").setValue("+79234567890");
        form.$("[data-test-id=city] .input__control").setValue("ка");
        form.$("[data-test-id=city] .input__control").sendKeys(Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ENTER);
        form.$(".checkbox").click();
        form.$(".button").click();
        $("[data-test-id=city] .input__control").shouldHave(value("Екатеринбург"));
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(12));
        $(".notification__title").shouldHave(exactText("Успешно!"));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + defaultDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldPassWhenCityIsAloneTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=name] .input__control").setValue("Васильев Андрей");
        form.$("[data-test-id=phone] .input__control").setValue("+79234567890");
        form.$("[data-test-id=city] .input__control").setValue("ду");
        form.$("[data-test-id=city] .input__control").sendKeys(Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ENTER);
        form.$(".checkbox").click();
        form.$(".button").click();
        $("[data-test-id=city] .input__control").shouldHave(value("Ростов-на-Дону"));
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(12));
        $(".notification__title").shouldHave(exactText("Успешно!"));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + defaultDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldPassWhenCityClickedByMouseTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=name] .input__control").setValue("Васильев Андрей");
        form.$("[data-test-id=phone] .input__control").setValue("+79234567890");
        form.$("[data-test-id=city] .input__control").setValue("ду");
        form.$("[data-test-id=city] .input__control").sendKeys(Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN);
        $(".menu-item").click();
        form.$(".checkbox").click();
        form.$(".button").click();
        $("[data-test-id=city] .input__control").shouldHave(value("Ростов-на-Дону"));
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(12));
        $(".notification__title").shouldHave(exactText("Успешно!"));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + defaultDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldNotPassWhenThereIsNoCityTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=name] .input__control").setValue("Васильев Андрей");
        form.$("[data-test-id=phone] .input__control").setValue("+79234567890");
        form.$("[data-test-id=city] .input__control").setValue("юз");
        form.$("[data-test-id=city] .input__control")
                .sendKeys(Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ENTER);
        form.$(".checkbox").click();
        form.$(".button").click();
        $("[data-test-id=city] .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    // тесты на виджет календаря

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
    String planningDate = generateDate(17);
    String defaultDate = generateDate(3);

    @Test
    void shouldPassWhenDateTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=city] .input__control").setValue("Калуга");
        form.$("[data-test-id=name] .input__control").setValue("Васильев Андрей");
        form.$("[data-test-id=phone] .input__control").setValue("+79234567890");
        form.$(".icon").click();
        $(".calendar").sendKeys(Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ENTER);
        form.$(".checkbox").click();
        form.$(".button").click();
        form.$("[data-test-id=date] .input__control").shouldBe(value(planningDate));
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(12));
        $(".notification__title").shouldHave(exactText("Успешно!"));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}
