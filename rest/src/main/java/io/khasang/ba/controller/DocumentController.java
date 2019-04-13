package io.khasang.ba.controller;

import io.khasang.ba.entity.Document;
import io.khasang.ba.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/document")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Document addDocument(@RequestBody Document document) {
        return documentService.addDocument(document);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Document getDocumentById(@PathVariable(name = "id") long id) {
        return documentService.getDocumentById(id);
    }

    @RequestMapping(value = "/get/all", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Document> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Document updateDocument(@RequestBody Document document) {
        return documentService.updateDocument(document);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces =
            "application/json;charset=utf-8")
    @ResponseBody
    public Document deleteDocument(@PathVariable(name = "id") long id) {
        return documentService.deleteDocument(id);
    }
}