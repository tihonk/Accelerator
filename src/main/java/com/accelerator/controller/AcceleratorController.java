package com.accelerator.controller;

import com.accelerator.configuration.AcceleratorConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class AcceleratorController
{
    @RequestMapping(value = "/chemistry/accelerator", method = RequestMethod.GET)
    public String getCount(ModelMap model)
    {
        return "accelerator";
    }

    @RequestMapping(value = "/chemistry/accelerator", method = RequestMethod.POST)
    public String analis( @RequestParam String text, @RequestParam String element,
                          @RequestParam int maxCount, Map<String, Object> model) {

        AcceleratorConfig accelerator = new AcceleratorConfig();
        String processedPDB = accelerator.processPDB(text, element, maxCount);

        model.put("text", processedPDB);
        return "accelerator";
    }
}
