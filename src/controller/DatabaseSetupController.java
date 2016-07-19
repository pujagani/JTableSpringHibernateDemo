package controller;

import com.fasterxml.jackson.databind.*;
import dbsetup.*;
import model.*;
import org.hibernate.*;
import org.hibernate.internal.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import java.math.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

@Controller
@RequestMapping(value = "/DatabaseSetup")
public class DatabaseSetupController extends BaseController {
    @RequestMapping(value = "")
    public String index(ModelMap model, String table) {
        model.addAttribute("Error", "");
        try {
            createSession();
            if (table != null) {
                session.getTransaction().begin();
                if ("city".equals(table)) processCity(session);
                else if ("course".equals(table)) processCourse(session);
                else if ("student".equals(table)) processStudent(session);
                else if ("student_phone".equals(table)) processStudentPhone(session);
                else if ("student_result".equals(table)) processStudentResult(session);
                else if ("user".equals(table)) processUser(session);
                session.getTransaction().commit();
            }

            List<String[]> rslt = getRowCount(session);
            ObjectMapper mapper = new ObjectMapper();
            String rowCount = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rslt);
            model.addAttribute("RowCount", rowCount);
        } catch (Throwable ex) {
            String msg = exceptionToString(ex);
            model.addAttribute("Error", exceptionToString(ex));
        }

        return "/jsp/DatabaseSetup.jsp";
    }

    public List<String[]> getRowCount(Session session) {
        List<String[]> rslt = new ArrayList<>();
        getRowCount(session, rslt, "city");
        getRowCount(session, rslt, "course");
        getRowCount(session, rslt, "student");
        getRowCount(session, rslt, "student_phone");
        getRowCount(session, rslt, "student_result");
        getRowCount(session, rslt, "user");
        return rslt;
    }

    public void getRowCount(Session session, List<String[]> rslt, String table) {
        try {// Hibernate syntac for obtaining row count.
            int count = ((BigInteger) session.createNativeQuery("Select count(*) From " + table).getSingleResult()).intValue();
            rslt.add(new String[]{table, count + ""});
        } catch (Throwable ex) {
            rslt.add(new String[]{table, exceptionToString(ex)});
        }
    }

    public void processCity(Session session) {
        executeSQL(session, City.dropSQL);
        executeSQL(session, City.createSQL);
        //Adding records in the database.
        for (String city_name : DataRepo.city_data) {
            City city = new City();
            city.name = city_name;
            session.save(city);
        }
    }

    public void processCourse(Session session) {
        executeSQL(session, Course.dropSQL);
        executeSQL(session, Course.createSQL);

        for (String course_name : DataRepo.course_data) {
            Course course = new Course();
            course.name = course_name;
            session.save(course);
        }
    }

    public void processStudent(Session session) {
        executeSQL(session, Student.dropSQL);
        executeSQL(session, Student.createSQL);
        executeSQL(session, Student.alterSQL);

        for (int i = 0; i < 200; i++) {
            Student student = new Student();
            int pos = (int) (Math.random() * DataRepo.person_data.length);
            int pos2 = (int) (Math.random() * DataRepo.person_data.length);
            student.name = DataRepo.person_data[pos][0] + " " + DataRepo.person_data[pos2][1];
            student.email = DataRepo.person_data[pos][0] + "." + DataRepo.person_data[pos2][1] + "@gmail.com";
            student.password = "Prescient#1";
            student.gender = DataRepo.person_data[pos][2];
            int cty = (int) (Math.random() * DataRepo.city_data.length);
            student.city_id = cty + 1;
            student.birth_date = new Date(100 - (int) (Math.random() * 20), (int) (Math.random() * 11), (int) (Math.random() * 30) + 1);
            student.education = (int) (Math.random() * 3) + 1;
            student.about = "Studies at UCLA";
            student.active_flg = "Y";
            student.record_date = new Date(115 - (int) (Math.random() * 2), (int) (Math.random() * 11), (int) (Math.random() * 30) + 1);
            session.save(student);
        }
    }

    public void processStudentPhone(Session session) {
        executeSQL(session, StudentPhone.dropSQL);
        executeSQL(session, StudentPhone.createSQL);
        executeSQL(session, StudentPhone.alterSQL);

        String sql = "Select id From student Order By id";
        List rows = session.createNativeQuery(sql).list();
        for (int i = 0; i < rows.size(); i++) {
            int student_id = (int) rows.get(i);
            StudentPhone studentPhone = new StudentPhone();
            studentPhone.student_id = student_id;
            studentPhone.phone_type = 1;
            studentPhone.phone_number = ((long) (Math.random() * 1000000000) + 7000000000L) + "";
            studentPhone.record_date = new Date();
            session.save(studentPhone);

            studentPhone = new StudentPhone();
            studentPhone.student_id = student_id;
            studentPhone.phone_type = 2;
            studentPhone.phone_number = ((long) (Math.random() * 1000000000) + 8000000000L) + "";
            studentPhone.record_date = new Date();
            session.save(studentPhone);

            studentPhone = new StudentPhone();
            studentPhone.student_id = student_id;
            studentPhone.phone_type = 3;
            studentPhone.phone_number = ((long) (Math.random() * 1000000000) + 9000000000L) + "";
            studentPhone.record_date = new Date();
            session.save(studentPhone);
        }
    }

    public void processStudentResult(Session session) {
        executeSQL(session, StudentResult.dropSQL);
        executeSQL(session, StudentResult.createSQL);
        executeSQL(session, StudentResult.alterSQL);
    }

    public void processUser(Session session) {
        executeSQL(session, User.dropSQL);
        executeSQL(session, User.createSQL);
    }

    public void executeSQL(Session session, String sql) {
        // Executing SQL statement as per Hibernate requirement.
        SessionImpl sessionImpl = (SessionImpl) session;
        Connection connection = sessionImpl.connection();
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        } catch (Throwable ex) {
            throw new Error(ex);
        }
    }
}