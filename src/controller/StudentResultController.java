package controller;

import model.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

//Similar to City controller of JSpringDemo.
@Controller
@RequestMapping(value = "/StudentResult")
public class StudentResultController extends BaseController {
    @ResponseBody
    @RequestMapping(value = "List")
    public JTableResult List(JTableRequest jTableRequest, int student_id) {
        JTableResult rslt = new JTableResult();
        try {
            createSession();
            return StudentResult.retrievePage(session, student_id, jTableRequest);
        } catch (Throwable ex) {
            rslt.Result = "Error";
            rslt.Message = exceptionToString(ex);
            return rslt;
        }
    }

    @ResponseBody
    @RequestMapping(value = "Save")
    public JTableResult Save(@Valid StudentResult studentResult, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return toError(bindingResult);
        int action = Integer.parseInt(request.getParameter("action"));
        if (action == 1) return insert(studentResult);
        else return update(studentResult);
    }

    @ResponseBody
    @RequestMapping(value = "Delete")
    public JTableResult Delete(int id) {
        StudentResult studentResult = new StudentResult();
        studentResult.id = id;
        return delete(studentResult);
    }
}
