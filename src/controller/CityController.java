package controller;
//Similar to City controller of JSpringDemo.

import model.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@Controller
@RequestMapping(value = "/City")
public class CityController extends BaseController {

    @RequestMapping(value = "")
    public String index() {
        return "/jsp/City.jsp";
    }

    @ResponseBody
    @RequestMapping(value = "List")
    public JTableResult List(JTableRequest jTableRequest) {
        JTableResult rslt = new JTableResult();
        try {
            createSession();
            return City.retrievePage(session, jTableRequest);
        } catch (Throwable ex) {
            rslt.Result = "Error";
            rslt.Message = exceptionToString(ex);
            return rslt;
        }
    }

    @ResponseBody
    @RequestMapping(value = "Save")
    public JTableResult Save(@Valid @ModelAttribute("City") City city, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return toError(bindingResult);
        int action = Integer.parseInt(request.getParameter("action"));
        if (action == 1) return insert(city);
        else return update(city);
    }

    @ResponseBody
    @RequestMapping(value = "Delete")
    public JTableResult Delete(int id) {
        City city = new City();
        city.id = id;
        return delete(city);
    }
}