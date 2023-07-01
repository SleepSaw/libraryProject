package by.sleptsov.library.controllers;

import by.sleptsov.library.dao.BookDAO;
import by.sleptsov.library.dao.PersonDAO;
import by.sleptsov.library.models.Book;
import by.sleptsov.library.models.Person;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }
    @GetMapping()
    public String index(Model model){
        model.addAttribute("books",bookDAO.index());
        return "books/index";

    }
    @GetMapping("/{id}")
    public String show(Model model, @ModelAttribute("person") Person person,
                       @PathVariable("id") int id){
        model.addAttribute("book", bookDAO.show(id));
        Person owner = bookDAO.getOwnerById(id);
        if (owner == null)
            model.addAttribute("people", personDAO.index());
        else{
            model.addAttribute("owner", owner);
        }
        return "books/show";

    }
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        bookDAO.release(id);
        return "redirect:/books/"+id;
    }
    @PostMapping("/{id}")
    public String addReader(@ModelAttribute("person") Person person,
                            @ModelAttribute("book") Book book,
                            BindingResult bindingResult,
                            @PathVariable("id") int id){
        if (bindingResult.hasErrors()) return "books/show";
        bookDAO.assignBook(id, person.getId());
        return "redirect:/books";
    }
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @GetMapping("/search")
    public String showSearchedBooks(Model model, @ModelAttribute("str") String str){
        model.addAttribute("searchedBooks", bookDAO.findBooks(str));
        return "books/show";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult result){
        if (result.hasErrors()) return "books/new";
        bookDAO.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         @PathVariable("id") int id, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "books/edit";
        bookDAO.update(id, book);
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }
}
