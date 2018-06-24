package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.Cheese;
import org.launchcode.models.data.CategoryDao;
import org.launchcode.models.data.CheeseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("cheese")
public class CheeseController {

    @Autowired
    CheeseDao cheeseDao;

    @Autowired
    CategoryDao categoryDao;

    // Request path: /cheese
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "My Cheeses");

        return "cheese/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model) {
        model.addAttribute("title", "Add Cheese");
        model.addAttribute(new Cheese());
        model.addAttribute("categories", categoryDao.findAll());
        return "cheese/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheeseForm(@ModelAttribute  @Valid Cheese newCheese,
                                       Errors errors, @RequestParam int categoryId,
                                       Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Cheese");
            model.addAttribute("categories", categoryDao.findAll());
            return "cheese/add";
        }

        Category cat = categoryDao.findOne(categoryId);
        newCheese.setCategory(cat);
        cheeseDao.save(newCheese);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model) {
        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "Remove Cheese");
        return "cheese/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@RequestParam int[] cheeseIds) {

        for (int cheeseId : cheeseIds) {
            cheeseDao.delete(cheeseId);
        }

        return "redirect:";
    }


    /**
    Display by Category
    **/
    @RequestMapping(value="category/{categoryId}", method = RequestMethod.GET)
    public String displayCheeseByCategory(@PathVariable int categoryId, Model model) {

        Category cat = categoryDao.findOne(categoryId);
        List<Cheese> cheeses = cat.getCheeses();
        model.addAttribute("title", "Cheeses in Category" + cat.getName());
        model.addAttribute("cheeses", cheeses);

        return "cheese/index";
    }

    /**
     * Edit Cheese
    **/
    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.GET)
    public String displayEditCheeseForm(Model model, @PathVariable int cheeseId) {
        Cheese editCheese = cheeseDao.findOne(cheeseId);
        Iterable<Category> cat = categoryDao.findAll();
        model.addAttribute("cheese", editCheese);
        model.addAttribute("categories", cat);
        model.addAttribute("title", "Edit Cheese");
        return "cheese/edit";
    }

    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.POST)
    public String processEditCheeseForm(int cheeseId,
                                       int categoryId, String name,
                                        String description) {

        /**
        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit Cheese");
            model.addAttribute("cheese", editCheese);
            model.addAttribute("categories", categoryDao.findAll());
            return "cheese/edit";
        }**/

        Cheese editCheese = cheeseDao.findOne(cheeseId);
        Category cat = categoryDao.findOne(categoryId);

        //cheeseDao.delete(cheeseId);
        editCheese.setName(name);
        editCheese.setDescription(description);
        editCheese.setCategory(cat);
        cheeseDao.save(editCheese);
        return "redirect:..";
    }


}
