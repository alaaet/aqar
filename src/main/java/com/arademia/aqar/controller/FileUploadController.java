package com.arademia.aqar.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import com.arademia.aqar.util.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.arademia.aqar.storage.StorageFileNotFoundException;
import com.arademia.aqar.storage.StorageService;

@Controller
@RequestMapping(value = Mappings.FILES)
@CrossOrigin
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }



    //////////////////// PUBLIC METHODS ////////////////////
    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    //////////////////// USER/ADMIN METHODS ////////////////////
    //@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @PostMapping
    public String handleFileUpload(@RequestParam("file") MultipartFile file,RedirectAttributes redirectAttributes) {
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:"+Mappings.FILES;
    }

    //////////////////// ADMIN METHODS ////////////////////
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }


    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}