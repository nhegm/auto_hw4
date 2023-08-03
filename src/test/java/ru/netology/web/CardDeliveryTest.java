package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


class CardDeliveryTest {

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

    @Test
    void shouldNotPassWhenDateFromThePastTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=city] .input__control").setValue("Москва");
        form.$("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE);
        form.$("[data-test-id=date] .input__control").setValue("22.05.2020");
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
        $(".checkbox").shouldNotHave(Condition.attribute("checkbox_checked"));
    }

}