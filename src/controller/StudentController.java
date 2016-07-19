package controller;
//Similar to City controller of JSpringDemo.

import com.fasterxml.jackson.databind.*;
import model.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

@Controller
@RequestMapping(value = "/Student")
public class StudentController extends BaseController {

    @RequestMapping(value = "")
    public String index(ModelMap model) {
        try {
            createSession();
            HashMap<Integer, String> cities = City.retrieveAll(session);
            ObjectMapper mapper = new ObjectMapper();
            String OptionsCity = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cities);
            HashMap<Integer, String> courses = Course.retrieveAll(session);
            ObjectMapper mapper1 = new ObjectMapper();
            String OptionsCourse = mapper1.writerWithDefaultPrettyPrinter().writeValueAsString(courses);
            model.addAttribute("OptionsCity", OptionsCity);
            model.addAttribute("OptionsCourse", OptionsCourse);
            model.addAttribute("Error", "");
        } catch (Throwable ex) {
            model.addAttribute("OptionsCity", 0);
            model.addAttribute("OptionsCourse", 0);
            model.addAttribute("Error", exceptionToString(ex));
        }
        return "/jsp/Student.jsp";
    }

    @ResponseBody
    @RequestMapping(value = "List")
    public JTableResult List(JTableRequest jTableRequest) {
        JTableResult rslt = new JTableResult();
        try {
            createSession();
            return Student.retrievePage(session, jTableRequest);
        } catch (Throwable ex) {
            rslt.Result = "Error";
            rslt.Message = exceptionToString(ex);
            return rslt;
        }
    }

    @ResponseBody
    @RequestMapping(value = "Save")
    public JTableResult Save(@Valid Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return toError(bindingResult);

        int action = Integer.parseInt(request.getParameter("action"));
        if (student.active_flg == null) student.active_flg = "N";
        if (action == 1) {
            student.record_date = new Date();
            return insert(student);
        } else {
            createSession();
            student.record_date = Student.getRecordDateById(session, student.id);
            return update(student);
        }
    }

    @ResponseBody
    @RequestMapping(value = "Delete")
    public JTableResult Delete(int id) {
        Student student = new Student();
        student.id = id;
        return delete(student);
    }
}
