package controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/Home")
public class HomeController extends BaseController {

    @RequestMapping(value = "")
    public String index() {
        return "/jsp/Home.jsp";
    }

}