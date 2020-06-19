package ru.netology.web;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComplexElementsTest {

    // заранее получаем дату
    String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    @Test
    void shouldChoiceAfterTwoLetters() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] [type='text']").sendKeys("Аб");
        $("[data-test-id='city'] [type='text']").click();
        $$("span.menu-item__control").findBy(text("Екатеринбург")).click();
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[data-test-id='name'] [type='text']").setValue("Иванов Андрей");
        $("[data-test-id='phone'] [type='tel']").setValue("+79256541122");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        String getText = $("[data-test-id='notification'] div.notification__title").waitUntil(visible, 14000).getText();
        assertEquals("Успешно!", getText);
    }

//    @Test
//    void shouldSelectDateFromInstrument() {
//        open("http://localhost:9999/");
//        $("[data-test-id='city'] [type='text']").setValue("Москва");
//        $("[data-test-id='date'] [type='tel']").click();
//        System.out.println("jjj");
//    }
}
