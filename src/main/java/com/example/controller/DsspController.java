package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
public class DsspController
{
    @RequestMapping(value = "/chemistry/dssp", method = RequestMethod.GET)
    public String getCount(ModelMap model)
    {
        return "dssp";
    }

    @RequestMapping(value = "/chemistry/dssp", method = RequestMethod.POST)
    public String getResult(HttpServletRequest request)
    {
        String dsspText = request.getParameter("text");
        return "dssp";
    }
}
