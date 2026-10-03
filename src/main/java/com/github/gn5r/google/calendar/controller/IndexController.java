package com.github.gn5r.google.calendar.controller;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.gn5r.google.calendar.form.CalendarForm;

@Controller
public class IndexController {

  private static final DateTimeFormatter GOOGLE_CALENDAR_DATE_FORMAT = DateTimeFormatter
      .ofPattern("yyyyMMdd'T'HHmmss");

  @GetMapping({ "", "/" })
  public String index(@ModelAttribute CalendarForm form) {
    return "index";
  }

  @PostMapping({ "", "/" })
  public String post(@ModelAttribute CalendarForm form) {
    String startUtc = formatDateTime(form.getStartDateTime());
    String endUtc = formatDateTime(form.getEndDateTime());

    String url = UriComponentsBuilder
        .fromUriString("https://calendar.google.com/calendar/render")
        .queryParam("action", "TEMPLATE")
        .queryParam("text", form.getTitle())
        .queryParam("dates", startUtc + "/" + endUtc)
        .queryParam("details", form.getDetails())
        .encode(StandardCharsets.UTF_8)
        .toUriString();

    return "redirect:" + url;
  }

  public String formatDateTime(LocalDateTime value) {
    return value.format(GOOGLE_CALENDAR_DATE_FORMAT);
  }

}
