package com.store.electronicsStore.controllers;

import com.store.electronicsStore.service.Authorization;
import com.store.electronicsStore.service.WorkWithProduct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.List;

@Controller
public class MainController {

    @GetMapping("/home")
    public String greeting(Model model){
        model.addAttribute("title", "Главная страница");
        return "home";
    }
    @GetMapping("/authorization")
    public String authorization(Model model){
        model.addAttribute("title", "Главная страница");
        return "authorization";
    }
    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("title", "Главная страница");
        return "registration";
    }
    @GetMapping("/product")
    public String product(Model model) throws SQLException{
        WorkWithProduct workWithProduct = new WorkWithProduct();
        List<String> allProduct = workWithProduct.allProduct();
        model.addAttribute("allProduct", allProduct);
        return "product";
    }
    @GetMapping("/product/{nameProduct}")
    public String storeSessions(@PathVariable(value="nameProduct") String nameProduct, Model model) throws SQLException{
        WorkWithProduct workWithProduct = new WorkWithProduct();
        List<String> allDate = workWithProduct.allDate(nameProduct);
        model.addAttribute("nameProduct", nameProduct);
        model.addAttribute("allDate", allDate);
        return "productStoreDate";
    }

    @GetMapping("/product/{nameProduct}/{date}")
    public String storeSessions(@PathVariable(value="nameProduct") String nameProduct,
                                  @PathVariable(value="date") String date, Model model) throws SQLException{
        WorkWithProduct workWithProduct = new WorkWithProduct();
        List<String> allTime = workWithProduct.allTime(nameProduct,date);
        model.addAttribute("nameProduct", nameProduct);
        model.addAttribute("date",date);
        model.addAttribute("allTime", allTime);
        return "productStoreTime";
    }

    @GetMapping("/product/{nameProduct}/{date}/{time}/buy")
    public String storeSessions(@PathVariable(value="nameProduct") String nameProduct,
                                  @PathVariable(value="date") String date,
                                  @PathVariable(value="time") String time, Model model) throws SQLException{
        WorkWithProduct workWithProduct = new WorkWithProduct();
        Authorization authorization = new Authorization();
        if (workWithProduct.storeProduct(authorization.getLogin(),workWithProduct. getIdProduct(nameProduct,date,time))){
            return "redirect:product";
        }
        return "redirect:s";
    }


    @PostMapping("/registration")
    public String registrationAdd(@RequestParam String login,@RequestParam String password, Model model) throws SQLException, ClassNotFoundException {
        Authorization authorization = new Authorization();
        authorization.createNewAccount(login,password);
        return "redirect:home";
    }

    @PostMapping("/authorization")
    public String authorizationAdd(@RequestParam String login, @RequestParam String password, Model model) throws SQLException, ClassNotFoundException {
        Authorization authorization = new Authorization();
        if (!authorization.loginUser(login,password)){
            return "redirect:home";
        }
        return "redirect:product";
    }
}
