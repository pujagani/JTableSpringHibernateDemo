package controller;

import model.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

// Redirecting the user to Registration.jsp.
// Once the data is entered, entering the user to the database.
//User can then login.
@Controller
@RequestMapping(value = "/Registration")
public class RegistrationController extends BaseController {

    @RequestMapping(value = "")
    public String index(ModelMap model) {
        return "/jsp/Registration.jsp";
    }


    @ResponseBody
    @RequestMapping(value = "Save")
    public JTableResult Save(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return toError(bindingResult);
        return insert(user);
    }
}
