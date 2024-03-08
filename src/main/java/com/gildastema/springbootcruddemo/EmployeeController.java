package com.gildastema.springbootcruddemo;

import com.gildastema.springbootcruddemo.entities.Employee;
import com.gildastema.springbootcruddemo.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("employee", new Employee());
        model.addAttribute("employees", employeeRepository.findAll( ));
        return "index.html";
    }

    @PostMapping("/employee/create")
    public String create(@ModelAttribute Employee employee){
        employeeRepository.save(new Employee(employee.getFirstName(), employee.getLastName()));
        return "redirect:/";
    }
}
