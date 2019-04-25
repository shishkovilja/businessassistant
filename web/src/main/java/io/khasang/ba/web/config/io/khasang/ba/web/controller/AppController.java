package io.khasang.ba.web.config.io.khasang.ba.web.controller;

import io.khasang.ba.web.config.io.khasang.ba.web.Message;
import io.khasang.ba.web.config.io.khasang.ba.web.service.CreateTable;
import io.khasang.ba.web.config.io.khasang.ba.web.service.MyService;
import io.khasang.ba.web.config.io.khasang.ba.web.util.CheckText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class AppController {

    private static final Logger log = LoggerFactory.getLogger(AppController.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Autowired
    private Message message;
    @Autowired
    private CreateTable createTable;
    @Autowired
    private CheckText checkText;
    @Qualifier("myServiceImpl")
    @Autowired
    private MyService myService;

    // localhost:8080/
    @RequestMapping("/")
    public String getHelloPage(Model model) {
        log.info("Current time {}", dateFormat.format(new Date()));
        model.addAttribute("name", myService.getName());
        return "home";
    }

    @RequestMapping("/catinfo")
    public String getHelloPage() {
        return "cat";
    }

    // localhost:8080/name/asdasd
    @RequestMapping("/name/{name}")
    public String getName(@PathVariable("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }

    // localhost:8080/create
    @Secured("ROLE_ADMIN")
    @RequestMapping("/create")
    public String createTable(Model model) {
        model.addAttribute("status", createTable.getTableCreationStatus());
        return "create";
    }

    @RequestMapping(value = "/password/{password}", method = RequestMethod.GET)
    public String getCryptPassword(@PathVariable("password") String password, Model model) {
        model.addAttribute("password", password);
        model.addAttribute("encodePassword", new BCryptPasswordEncoder().encode(password));
        return "password";
    }

    @ResponseBody
    @RequestMapping(value = "/check/{text}", method = RequestMethod.GET)
    public String checkText(@PathVariable("text") String text) {
        return checkText.checkWord(text);
    }
}
