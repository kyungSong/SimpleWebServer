package website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;

/**
 * Created by Kyung Song on 6/28/2017.
 */

@Controller
public class WebController {

    @RequestMapping("/trending")
    public String trending(@RequestParam(value="name", required = false, defaultValue = "World") String name, Model model)
    {
        model.addAttribute("name", name);
        return "trending";

    }

    @RequestMapping("/")
    public String index()
    {
        return "index";
    }

    @RequestMapping("/google_trends")
    @ResponseBody
    public String googleTrends(@RequestParam(defaultValue = "커피", required = false, value="searchTerm") String searchTerm,
                               @RequestParam(defaultValue = "2000-01-01", required = false, value="startDate") String startDate,
                               @RequestParam(defaultValue = "2017-01-01", required = false, value="endDate") String endDate)
    {
        System.out.println(searchTerm);
        System.out.println(startDate);
        System.out.println(endDate);
        Runtime rt = Runtime.getRuntime();
        String command = "node e:\\development\\nodejs\\gt_related_queries.js " + searchTerm + " " + startDate + " " + endDate;
        System.out.println(command);
        Process proc = null;
        try
        {
            proc = rt.exec(command);
        }
        catch(IOException e)
        {
            System.err.println("Caught IOException: " + e.getMessage());
        }


        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        //read the output
        String s = "";
        String temp = null;

        try {
            while ((temp = stdInput.readLine()) != null) {
                s += temp;
            }
            System.out.println("Printing out Error Messages (if any): \n");
            while ((temp = stdError.readLine()) != null) {
                System.out.println(temp);
            }
        }catch(IOException e){
            System.err.println("Caught IOException: " + e.getMessage());
        }
        return s;
    }
}
