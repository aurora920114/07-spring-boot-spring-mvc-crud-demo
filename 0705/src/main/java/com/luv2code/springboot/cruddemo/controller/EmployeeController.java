package com.luv2code.springboot.cruddemo.controller;

import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    //Employee Controller 將使用 Employee Service
    private EmployeeService employeeService;

    //add constructor to inject EmployeeService
    public EmployeeController(EmployeeService theEmployeeService){
        employeeService = theEmployeeService;
    }

    //添加映射 -> 用於列出員工
    @GetMapping("/list")
    public String listEmployees(Model theModel){
        //get the employees from db
        List<Employee> theEmployees = employeeService.findAll();

        //add to the spring model
        theModel.addAttribute("employees",theEmployees);
        return "employees\\list-employees.html";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel){
        //create model attribute to bind form data
        Employee theEmployee = new Employee();

        theModel.addAttribute("employee", theEmployee);

        return "employees/employee-form";
    }

    @GetMapping("/showFormPreUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int theId,Model theModel){
        // get the employee from the service
        Employee theEmployee = employeeService.findById(theId);

        // set employee as a model attribute to pre-populate the form
        theModel.addAttribute("employee", theEmployee);

        // send over to our form
        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee){
        // save the employee
        employeeService.save(theEmployee);

        //use a redirect to prevent duplicate submission( 避免重複提交 )
        return "redirect:/employees/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId") int theId) {
        // delete the employee
        employeeService.deleteById(theId);

        // redirect to /employees/list
        return "redirect:/employees/list";
    }
}

