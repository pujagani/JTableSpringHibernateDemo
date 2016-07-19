package controller;
//Similar to City controller of JSpringDemo.

import model.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@Controller
@RequestMapping(value = "/StudentPhone")
public class StudentPhoneController extends BaseController {
    @ResponseBody
    @RequestMapping(value = "List")
    public JTableResult List(JTableRequest jTableRequest, int student_id) {
        JTableResult rslt = new JTableResult();
        try {
            createSession();
            return StudentPhone.retrievePage(session, student_id, jTableRequest);
        } catch (Throwable ex) {
            rslt.Result = "Error";
            rslt.Message = exceptionToString(ex);
            return rslt;
        }
    }

    @ResponseBody
    @RequestMapping(value = "Save")
    public JTableResult Save(@Valid StudentPhone studentPhone, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return toError(bindingResult);
        int action = Integer.parseInt(request.getParameter("action"));
        if (action == 1) return insert(studentPhone);
        else return update(studentPhone);
    }

    @ResponseBody
    @RequestMapping(value = "Delete")
    public JTableResult Delete(int id) {
        StudentPhone studentPhone = new StudentPhone();
        studentPhone.id = id;
        return delete(studentPhone);
    }
}
