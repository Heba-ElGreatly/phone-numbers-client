package customer.controller;

import customer.model.CustomerDTO;
import customer.model.FilteredCriteria;
import customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    List<CustomerDTO> customersList;
    List<String> countryList;
    List<String> stateList;

    @PostConstruct
    public void init() {
        customersList = customerService.getCustomers();
        countryList = customersList.stream().map(x -> x.getCountry()).distinct().collect(Collectors.toList());
        stateList = customersList.stream().map(x -> x.getState()).distinct().collect(Collectors.toList());
    }

    @GetMapping
    public String findAll(Model model,
                          @RequestParam("page") Integer page,
                          @RequestParam("size") Integer size){

        Page<CustomerDTO> customersList = customerService.findPaginated(PageRequest.of(page, size));

        model.addAttribute("countryList", countryList);
        model.addAttribute("stateList", stateList);

        model.addAttribute("customers", customersList);
        int totalPages = customersList.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());

            if(customersList.getContent().size() < size){
                pageNumbers.remove(pageNumbers.size()-1);
                System.out.println(pageNumbers.size());
            }

            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "customers";
    }

    //to submit country and state filter
    @PostMapping(path = "/search")
    public String submitFilter(@ModelAttribute("filteredCriteria") FilteredCriteria filteredCriteria
            , Model model){

        List<CustomerDTO> filteredList = customersList;

        if (!filteredCriteria.getCountry().isEmpty() && !filteredCriteria.getState().isEmpty()) {
            filteredList = customersList.stream().map(x -> x)
                    .filter(c -> c.getCountry().equals(filteredCriteria.getCountry()))
                    .filter(s -> s.getState().equals(filteredCriteria.getState()))
                    .collect(Collectors.toList());
        } else if (!filteredCriteria.getCountry().isEmpty()) {
            filteredList = customersList.stream().map(x -> x)
                    .filter(c -> c.getCountry().equals(filteredCriteria.getCountry()))
                    .collect(Collectors.toList());
        } else if (!filteredCriteria.getState().isEmpty()) {
            filteredList = customersList.stream().map(x -> x)
                    .filter(c -> c.getState().equals(filteredCriteria.getState()))
                    .collect(Collectors.toList());
        }
        model.addAttribute("countryList", countryList);
        model.addAttribute("stateList", stateList);
        model.addAttribute("customers", filteredList);

        return "customers";
    }
}
