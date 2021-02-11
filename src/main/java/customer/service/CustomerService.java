package customer.service;

import customer.model.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${resource.customers.filter}")
    private String filteredCustomersAPI;

    @Value("${resource.customers}")
    private String customersAPI;

    public List<CustomerDTO> getCustomers(){
        return Arrays.asList(restTemplate.getForObject(customersAPI,CustomerDTO[].class));
    }

    public Page<CustomerDTO> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<CustomerDTO> list = Arrays.asList(restTemplate.getForObject(filteredCustomersAPI, CustomerDTO[].class,currentPage,pageSize));
        Page<CustomerDTO> bookPage
                = new PageImpl(list, PageRequest.of(currentPage, pageSize), list.size());

        return bookPage;
    }
}
