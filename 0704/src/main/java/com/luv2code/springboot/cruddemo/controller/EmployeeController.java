package com.luv2code.springboot.cruddemo.controller;

import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/showFormAdd")
    public String showFormForAdd(Model theModel){
        //create model attribute to bind form data
        Employee theEmployee = new Employee();

        theModel.addAttribute("employee", theEmployee);

        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee){
        // save the employee
        employeeService.save(theEmployee);

        //use a redirect to prevent duplicate submission( 避免重複提交 )
        return "redirect:/employees/list";
    }
}

