package com.example.demo;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("rest/customers")
public class CustomerController {
    List<CustomerDto> dtos = new ArrayList<>();

    @PostConstruct
    public void initDummyData() {
        dtos.add(new CustomerDto(1l, "John"));
        dtos.add(new CustomerDto(2l, "Mike"));
    }

    @GetMapping
    public List<CustomerDto> getAll(@RequestParam(required = false) String name) {
        if (StringUtils.isNotBlank(name)) {
            return dtos.stream().filter(dto -> dto.name.equals(name)).collect(toList());
        } else {
            return dtos;
        }
    }

    @GetMapping("{id}")
    public CustomerDto getById(@PathVariable long id) {
        return dtos.stream().filter(d -> d.id==id).findFirst().get();
    }
}

class CustomerDto {
    public long id;
    public String name;
    public CustomerDto() {}
    public CustomerDto(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
