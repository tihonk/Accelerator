package com.example.controller;

import com.example.Configuration.MysteryConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class MysteryController
{
    @RequestMapping(value = "/chemistry/mystery", method = RequestMethod.GET)
    public String getCount(ModelMap model)
    {
        return "mystery";
    }

    @RequestMapping(value = "/chemistry/mystery", method = RequestMethod.POST)
    public String analis(@RequestParam String text, Map<String, Object> model)
    {
        MysteryConfig mysteryConfig = new MysteryConfig();
        List<String> aminoAcids = mysteryConfig.getAminoAcids(text);

        model.put("aminoAcidList", aminoAcids );
        return "mystery";
    }
}
