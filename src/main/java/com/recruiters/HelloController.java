package com.recruiters;

import com.github.tangzero.haml4j.Template;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HelloController {
    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(final ModelMap model) {

        File f = new File("/home/sevenbits/projects/hello.haml");
        Template template = null;
        try {
            template = new Template(f);
        } catch (IOException e) {

        }

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("title", "Ham4J");
        context.put("message", "Simple message");

        return template.render(context);
    }
}