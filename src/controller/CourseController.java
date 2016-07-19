package controller;
//Similar to City controller of JSpringDemo.

import model.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@Controller
@RequestMapping(value = "/Course")
public class CourseController extends BaseController {

    @RequestMapping(value = "")
    public String index() {
        return "/jsp/Course.jsp";
    }

    @ResponseBody
    @RequestMapping(value = "List")
    public JTableResult List(JTableRequest jTableRequest) {
        JTableResult rslt = new JTableResult();
        try {
            createSession();
            return Course.retrievePage(session, jTableRequest);
        } catch (Throwable ex) {
            rslt.Result = "Error";
            rslt.Message = exceptionToString(ex);
            return rslt;
        }
    }

    @ResponseBody
    @RequestMapping(value = "Save")
    public JTableResult Save(@Valid @ModelAttribute("Course") Course course, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return toError(bindingResult);
        int action = Integer.parseInt(request.getParameter("action"));
        if (action == 1) return insert(course);
        else return update(course);
    }

    @ResponseBody
    @RequestMapping(value = "Delete")
    public JTableResult Delete(int id) {
        Course course = new Course();
        course.id = id;
        return delete(course);
    }
}