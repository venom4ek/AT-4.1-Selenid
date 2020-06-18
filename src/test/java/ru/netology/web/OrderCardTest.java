package ru.netology.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderCardTest {

    // заранее получаем дату
    String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    String dateOver = LocalDate.now().plusDays(10).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    String dateExpired = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    String datePast = LocalDate.now().plusDays(-2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    @Nested
    @DisplayName("Тесты по полю 'Город'")
    public class FiledCityTest {
        @Test
        void shouldSubmitRequestFieldCity() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Москва");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue(date);
            $("[data-test-id='name'] [type='text']").setValue("Иванов Андрей");
            $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='notification'] div.notification__title").waitUntil(visible, 12000).getText();
            assertEquals("Успешно!", getText);
        }

        @Test
        void shouldRequestWhenCityAcrossLine() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Улан-Удэ");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue("");
            $("[data-test-id='date'] [type='tel']").setValue(date);
            $("[data-test-id='name'] [type='text']").setValue("Иванов Андрей");
            $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='notification'] div.notification__title").waitUntil(visible, 12000).getText();
            assertEquals("Успешно!", getText);
        }

        @Test
        void shouldRequestWhenCitySeparateBySpace() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Улан-Удэ");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue(date);
            $("[data-test-id='name'] [type='text']").setValue("Иванов Андрей");
            $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='notification'] div.notification__title").waitUntil(visible, 12000).getText();
            assertEquals("Успешно!", getText);
        }

        @Test
        void shouldRequestWhenCityConsistOfThreeWord() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Ростов-на-Дону");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue(date);
            $("[data-test-id='name'] [type='text']").setValue("Иванов Андрей");
            $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='notification'] div.notification__title").waitUntil(visible, 12000).getText();
            assertEquals("Успешно!", getText);
        }

        @Test
        void shouldRequestWhenNonExistentCity() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("No City");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue(date);
            $("[data-test-id='name'] [type='text']").setValue("Иванов Андрей");
            $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='city'] span.input__sub").waitUntil(visible, 12000).getText();
            assertEquals("Доставка в выбранный город недоступна", getText);
        }

        @Test
        void shouldRequestWhenCityIsEmpty() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue(date);
            $("[data-test-id='name'] [type='text']").setValue("Иванов Андрей");
            $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='city'] span.input__sub").waitUntil(visible, 12000).getText();
            assertEquals("Поле обязательно для заполнения", getText);
        }
    }

    @Nested
    @DisplayName("Тесты по полю 'Дата'")
    public class FiledDateTest {

        @Test
        void shouldRequestWhenDateAfterThreeDays() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Москва");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue(date);
            $("[data-test-id='name'] [type='text']").setValue("Иванов Андрей");
            $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='notification'] div.notification__title").waitUntil(visible, 12000).getText();
            assertEquals("Успешно!", getText);
        }

        @Test
        void shouldRequestWhenDateOverThreeDays() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Москва");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue(dateOver);
            $("[data-test-id='name'] [type='text']").setValue("Иванов Андрей");
            $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='notification'] div.notification__title").waitUntil(visible, 12000).getText();
            assertEquals("Успешно!", getText);
        }

        @Test
        void shouldRequestWhenDateLesTreeDays() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Москва");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue(dateExpired);
            $("[data-test-id='name'] [type='text']").setValue("Иванов Андрей");
            $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='date'] span.input__sub").getText();
            assertEquals("Заказ на выбранную дату невозможен", getText);
        }

        @Test
        void shouldRequestWhenDateExpired() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Москва");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue(datePast);
            $("[data-test-id='name'] [type='text']").setValue("Иванов Андрей");
            $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='date'] span.input__sub").getText();
            assertEquals("Заказ на выбранную дату невозможен", getText);
        }

        @Test
        void shouldRequestWhenNonExistentDate() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Москва");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue("35.15.2020");
            $("[data-test-id='name'] [type='text']").setValue("Иванов Андрей");
            $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='date'] span.input__sub").getText();
            assertEquals("Неверно введена дата", getText);
        }

        @Test
        void shouldRequestWhenDateIsEmpty() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Москва");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue("");
            $("[data-test-id='name'] [type='text']").setValue("Иванов Андрей");
            $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='date'] span.input__sub").getText();
            assertEquals("Неверно введена дата", getText);
        }
    }

    @Nested
    @DisplayName("Тесты по полю 'Фамилия Имя'")
    public class FiledNameTest {

        @Test
        void shouldSubmitRequestFieldName() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Москва");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue(date);
            $("[data-test-id='name'] [type='text']").setValue("Иванов Андрей");
            $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='notification'] div.notification__title").waitUntil(visible, 12000).getText();
            assertEquals("Успешно!", getText);
        }

        @Test
        void shouldRequestWhenDoubleName() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Москва");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue(date);
            $("[data-test-id='name'] [type='text']").setValue("Иванов Андрей-Иван");
            $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='notification'] div.notification__title").waitUntil(visible, 12000).getText();
            assertEquals("Успешно!", getText);
        }

        @Test
        void shouldRequestWhenDoubleName2() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Москва");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue(date);
            $("[data-test-id='name'] [type='text']").setValue("Иванов Андрей Иван");
            $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='notification'] div.notification__title").waitUntil(visible, 12000).getText();
            assertEquals("Успешно!", getText);
        }

        @Test
        void shouldRequestWhenDoubleSurname() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Москва");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue(date);
            $("[data-test-id='name'] [type='text']").setValue("Иванов-Сидоров Андрей");
            $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='notification'] div.notification__title").waitUntil(visible, 12000).getText();
            assertEquals("Успешно!", getText);
        }

        @Test
        void shouldRequestWhenEnglishName() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Москва");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue(date);
            $("[data-test-id='name'] [type='text']").setValue("Vasiliy");
            $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='name'] span.input__sub").getText();
            assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", getText);
        }

        @Test
        void shouldRequestWhenNameIsEmpty() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Москва");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue(date);
            $("[data-test-id='name'] [type='text']").setValue("");
            $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='name'] span.input__sub").getText();
            assertEquals("Поле обязательно для заполнения", getText);
        }


        //этот тест падает, т.к. форма допускает ввод только имени или фамилии, а так же любой абракадары.
        @Test
        void shouldRequestWhenOnlyOneWord() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Москва");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue(date);
            $("[data-test-id='name'] [type='text']").setValue("Иван");
            $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='name'].input_invalid").getCssValue("color");
            assertEquals("rgba(255, 92, 92, 1)", getText);
        }
    }

    @Nested
    @DisplayName("Тесты по полю 'Телефон'")
    public class FiledPhoneTest {

        @Test
        void shouldSubmitRequestFieldPhone() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Москва");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue(date);
            $("[data-test-id='name'] [type='text']").setValue("Иванов Андрей");
            $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='notification'] div.notification__title").waitUntil(visible, 12000).getText();
            assertEquals("Успешно!", getText);
        }

        @Test
        void shouldRequestWhenNotCorrectPhone() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Москва");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue(date);
            $("[data-test-id='name'] [type='text']").setValue("Иванов Андрей");
            $("[data-test-id='phone'] [type='tel']").setValue("79256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='phone'] span.input__sub").getText();
            assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", getText);
        }

        @Test
        void shouldRequestWhenPhoneIsEmpty() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Москва");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue(date);
            $("[data-test-id='name'] [type='text']").setValue("Иванов Андрей");
            $("[data-test-id='phone'] [type='tel']").setValue("");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='phone'] span.input__sub").getText();
            assertEquals("Поле обязательно для заполнения", getText);
        }


        // данный тест так же падает, т.к. нет проверки правильного ввода номера.
        // форма должна учитывать начало номера телефона с "+7", по факту только с "+"
        @Test
        void shouldRequestWhenNotCorrectPhone2() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Москва");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue(date);
            $("[data-test-id='name'] [type='text']").setValue("Иванов Андрей");
            $("[data-test-id='phone'] [type='tel']").setValue("+89256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='phone'] span.input__sub").getText();
            assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", getText);
        }
    }

    @Nested
    @DisplayName("Тесты по полю 'Чекбокс(проверка согласия)'")
    public class FiledCheckboxTest {

        @Test
        void shouldRequestCheckboxSelected() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Москва");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue(date);
            $("[data-test-id='name'] [type='text']").setValue("Иванов Андрей");
            $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='notification'] div.notification__title").waitUntil(visible, 12000).getText();
            assertEquals("Успешно!", getText);
        }

        @Test
        void shouldRequestCheckboxUnSelected() {
            open("http://127.0.0.1:9999/");
            $("[data-test-id='city'] [type='text']").setValue("Москва");
            $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            $("[data-test-id='date'] [type='tel']").setValue(date);
            $("[data-test-id='name'] [type='text']").setValue("Иванов Андрей");
            $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
            $$("button").find(exactText("Забронировать")).click();
            String getText = $("[data-test-id='agreement'].input_invalid").getCssValue("color");
            assertEquals("rgba(255, 92, 92, 1)", getText);
        }
    }
}
