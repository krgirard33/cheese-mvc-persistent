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
    @RequestMapping(value="category/{categoryId}", method = RequestMethod.GET)
    public String displayByCategory(@PathVariable("categoryId") int categoryId, Model model) {

        model.addAttribute("title", "Cheese in Category");
        model.addAttribute("cheeses", cheeseDao.findByCategory_Id(categoryId));

        return "cheese/index";
    }

    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.GET)
    public String displayEditCheeseForm(Model model, @PathVariable int cheeseId) {
        model.addAttribute("title", "Edit Cheese");
        Cheese moo = cheeseDao.findOne(cheeseId);
        model.addAttribute("cheese", moo);
        model.addAttribute("categories", categoryDao.findAll());
        return "cheese/edit";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String processEditCheeseForm(@ModelAttribute  @Valid Cheese moo,
                                       Errors errors, @RequestParam int categoryId,
                                       Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit Cheese");
            model.addAttribute("cheese", moo);
            model.addAttribute("categories", categoryDao.findAll());
            return "cheese/edit";
        }

        Category cat = categoryDao.findOne(categoryId);

        cheeseDao.delete(cheeseId);
        moo.setName(moo.getName());
        moo.setDescription(moo.setDescription());
        moo.setCategory(cat);
        cheeseDao.save(moo);
        return "redirect:";
    }
    **/

}
