package com.recruiters;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.recruiters.entities.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/")
public class HelloController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView vacanciesSearch() {

        ModelAndView mav = new ModelAndView("vacancies-search");

        // List<Book> books = new ArrayList<Book>();
        // books.add(new Book("The Hitchhiker's Guide to the Galaxy", 5.70, true));
        // books.add(new Book("Life, the Universe and Everything", 5.60, false));
        // books.add(new Book("The Restaurant at the End of the Universe", 5.40, true));

        // Map<String, Object> model = new HashMap<String, Object>();
        // model.put("books", books);
        // model.put("pageName", "My Bookshelf");
        // model.put("message", "Jade is working!");
        // model.put("newBook", new Book("The Restaurant at the End of the Universe", 5.40, true));

        // mav.addAllObjects(model);

        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/vacancy-show")
    public ModelAndView vacancyShow() {
        return new ModelAndView("vacancy-show");
    }

    @ResponseBody
    @RequestMapping(value = "/vacancies-list")
    public ModelAndView vacanciesList() {
        return new ModelAndView("vacancies-list");
    }

    @ResponseBody
    @RequestMapping(value = "/customer-vacancies-list")
    public ModelAndView customerVacanciesList() {
        return new ModelAndView("customer-vacancies-list");
    }
}