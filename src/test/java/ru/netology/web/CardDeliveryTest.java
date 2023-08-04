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


class CardDeliveryTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
    String planningDate = generateDate(6);
    String planningDefaultDate = generateDate(3);

    @Test
    void shouldPassWhenAllDataIsCorrectTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=city] .input__control").setValue("Казань");
        form.$("[data-test-id=name] .input__control").setValue("Васильев Андрей");
        form.$("[data-test-id=phone] .input__control").setValue("+79234567890");
        form.$(".checkbox").click();
        form.$(".button").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(12));
        $(".notification__title").shouldHave(exactText("Успешно!"));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDefaultDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldPassWhenNameWithYoTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=city] .input__control").setValue("Москва");
        form.$("[data-test-id=name] .input__control").setValue("Хрущёв Никита");
        form.$("[data-test-id=phone] .input__control").setValue("+79234567890");
        form.$(".checkbox").click();
        form.$(".button").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(12));
        $(".notification__title").shouldHave(exactText("Успешно!"));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDefaultDate), Duration.ofSeconds(12))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldPassWhenNameWithHyphenTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=city] .input__control").setValue("Санкт-Петербург");
        form.$("[data-test-id=name] .input__control").setValue("Салтыков-Щедрин Юрок");
        form.$("[data-test-id=phone] .input__control").setValue("+79234567890");
        form.$(".checkbox").click();
        form.$(".button").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(12));
        $(".notification__title").shouldHave(exactText("Успешно!"));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDefaultDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    // тесты на 1 поле Город
    @Test
    //в названии города дефис
    void shouldPassWhenCityWithHyphenTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=city] .input__control").setValue("Ростов-на-Дону");
        form.$("[data-test-id=name] .input__control").setValue("Васильев Андрей");
        form.$("[data-test-id=phone] .input__control").setValue("+79234567890");
        form.$(".checkbox").click();
        form.$(".button").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(12));
        $(".notification__title").shouldHave(exactText("Успешно!"));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDefaultDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldNotPassWhenCityIsNotFromListTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=city] .input__control").setValue("Белогорск");
        form.$("[data-test-id=name] .input__control").setValue("Васильев Андрей");
        form.$("[data-test-id=phone] .input__control").setValue("+79234567890");
        form.$(".checkbox").click();
        form.$(".button").click();
        $("[data-test-id=city] .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldNotPassWhenCityNotInRussianTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=city] .input__control").setValue("Moscow");
        form.$("[data-test-id=name] .input__control").setValue("Васильев Андрей");
        form.$("[data-test-id=phone] .input__control").setValue("+79234567890");
        form.$(".checkbox").click();
        form.$(".button").click();
        $("[data-test-id=city] .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    // тесты на 2 поле Дата встречи

    DateTimeFormatter myDateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    LocalDateTime dateToday = LocalDateTime.now();

    @Test
    void shouldPassWhenDatePlusThreeDaysTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=city] .input__control").setValue("Благовещенск");
        form.$("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id=date] .input__control").setValue(generateDate(6));
        form.$("[data-test-id=name] .input__control").setValue("Кошечкин");
        form.$("[data-test-id=phone] .input__control").setValue("+79234567890");
        form.$(".checkbox").click();
        form.$(".button").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(12));
        $(".notification__title").shouldHave(exactText("Успешно!"));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldNotPassWhenDateFromThePastTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=city] .input__control").setValue("Москва");
        form.$("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id=date] .input__control").setValue(generateDate(-50));
        form.$("[data-test-id=name] .input__control").setValue("Кошечкин молодец");
        form.$("[data-test-id=phone] .input__control").setValue("+79234567890");
        form.$(".checkbox").click();
        form.$(".button").click();
        $("[data-test-id=date] .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldNotPassWhenDateIsTodayTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=city] .input__control").setValue("Москва");
        form.$("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id=date] .input__control").setValue(generateDate(0));
        form.$("[data-test-id=name] .input__control").setValue("Кошечкин молодец");
        form.$("[data-test-id=phone] .input__control").setValue("+79234567890");
        form.$(".checkbox").click();
        form.$(".button").click();
        $("[data-test-id=date] .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    // тесты на 3 поле Имя и Фамилия
    @Test
    void shouldNotPassWhenNameNotRussianTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=city] .input__control").setValue("Москва");
        form.$("[data-test-id=name] .input__control").setValue("Frank Кошечкин");
        form.$("[data-test-id=phone] .input__control").setValue("+79234567890");
        form.$(".checkbox").click();
        form.$(".button").click();
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotPassWhenNameWithOtherSymbolsTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=city] .input__control").setValue("Москва");
        form.$("[data-test-id=name] .input__control").setValue("Кошечкин молодец.");
        form.$("[data-test-id=phone] .input__control").setValue("+79234567890");
        form.$(".checkbox").click();
        form.$(".button").click();
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue(",");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue("#");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue(";");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue("\'");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue("\"");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue("?");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue("(");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue(")");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue("_");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue("+");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue("=");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue("~");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue("`");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue("#");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue("@");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue("&");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue("/");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue("\\");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue("<");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue(">");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue("*");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue("%");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=name] .input__control").setValue("$");
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    // тесты на 4 поле Телефон

    @Test
    void shouldNotPassWhenPhoneBeginsNotFromPlusTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=city] .input__control").setValue("Абакан");
        form.$("[data-test-id=name] .input__control").setValue("Кошечкин Фрэнк");
        form.$("[data-test-id=phone] .input__control").setValue("79234567890");
        form.$(".checkbox").click();
        form.$(".button").click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotPassWhenPhoneHasSpacesTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=city] .input__control").setValue("Абакан");
        form.$("[data-test-id=name] .input__control").setValue("Кошечкин Фрэнк");
        form.$("[data-test-id=phone] .input__control").setValue("+7 9234567890");
        form.$(".checkbox").click();
        form.$(".button").click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotPassWhenPhoneHasHyphensTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=city] .input__control").setValue("Абакан");
        form.$("[data-test-id=name] .input__control").setValue("Кошечкин Фрэнк");
        form.$("[data-test-id=phone] .input__control").setValue("+7-923-456-7890");
        form.$(".checkbox").click();
        form.$(".button").click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotPassWhenPhoneHasBracketsTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=city] .input__control").setValue("Абакан");
        form.$("[data-test-id=name] .input__control").setValue("Кошечкин Фрэнк");
        form.$("[data-test-id=phone] .input__control").setValue("+7(923)4567890");
        form.$(".checkbox").click();
        form.$(".button").click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    // тест на чекбокс

    @Test
    void shouldNotPassWhenCheckboxDisabledTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=city] .input__control").setValue("Абакан");
        form.$("[data-test-id=name] .input__control").setValue("Кошечкин Фрэнк");
        form.$("[data-test-id=phone] .input__control").setValue("+79234567890");
        form.$(".button").click();
        $("[data-test-id='agreement'].input_invalid").shouldBe(visible);
    }

}