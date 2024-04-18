package ru.fsv67.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.fsv67.IssuanceTransform;
import ru.fsv67.services.WebService;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ui/reader")
public class ReaderViewController {
    private final WebService webService;

    @GetMapping
    public String getReaderList(Model model) {
        model.addAttribute("readerList", webService.getReaderList());
        return "reader-list";
    }

    @GetMapping("/{id}")
    public String getReader(@PathVariable long id, Model model) {
        model.addAttribute("reader", webService.getReaderById(id));
        List<IssuanceTransform> issuanceTransforms = webService.getIssuanceByIdReader(id);
        model.addAttribute("bookListReader", Objects.requireNonNullElse(issuanceTransforms, ""));
        return "reader";
    }
}