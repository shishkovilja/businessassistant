package io.khasang.ba.controller;

import io.khasang.ba.entity.Cat;
import io.khasang.ba.service.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO We should think about profiles, or for properties file, for example, to eliminate hard coding CORS address
@Controller
@RequestMapping("/cat")
public class CatController {

    @Autowired
    private CatService catService;

    /*
     * test javadoc
     * */
    @CrossOrigin(origins = "http://localhost:8888")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Cat addCat(@RequestBody Cat cat){
        catService.addCat(cat);
        return cat;
    }

    @CrossOrigin(origins = "http://localhost:8888")
    @ResponseBody
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Cat getCatById(@PathVariable(value = "id") long id){
        return catService.getCatById(id);
    }

    @CrossOrigin(origins = "http://localhost:8888")
    @ResponseBody
    @RequestMapping(value = "/get/all", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public List<Cat> getAllCats(){
        return catService.getAllCats();
    }

    @CrossOrigin(origins = "http://localhost:8888")
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    public Cat updateCat(@RequestBody Cat cat){
        return catService.updateCat(cat);
    }

    @CrossOrigin(origins = "http://localhost:8888")
    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
    public Cat deleteCat(@PathVariable(value = "id") long id){
        return catService.deleteCat(id);
    }
}
