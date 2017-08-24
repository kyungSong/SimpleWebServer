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
    public String trending()
    {
        return "trending";
    }

    @RequestMapping("/")
    public String index()
    {
        return "index";
    }

    @RequestMapping("/text_analysis")
    public String text_analysis()
    {
        return "text_analysis";
    }

    @RequestMapping("/about")
    public String about()
    {
        return "about";
    }

    @RequestMapping("/contact")
    public String contact()
    {
        return "contact";
    }

    @RequestMapping("/google_trends")
    @ResponseBody
    public String googleTrends(@RequestParam(defaultValue = "커피", required = false, value="searchTerm") String searchTerm,
                               @RequestParam(defaultValue = "2000-01-01", required = false, value="startDate") String startDate,
                               @RequestParam(defaultValue = "2017-01-01", required = false, value="endDate") String endDate)
    {
        Runtime rt = Runtime.getRuntime();
        String command = "node c:\\Users\\Administrator\\Desktop\\server\\tools\\nodejs\\gt_related_queries.js " + searchTerm + " " + startDate + " " + endDate;
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

    @RequestMapping("/get_text_analysis")
    @ResponseBody
    public String text_analysis(@RequestParam(defaultValue = "리니지m", required = false, value="gameName") String gameName,
                                @RequestParam(defaultValue = "2017-01-01", required = false, value="startDate") String startDate,
                                @RequestParam(defaultValue = "2017-01-01", required = false, value="endDate") String endDate)
    {
        Runtime rt = Runtime.getRuntime();
        String command = "python c:\\Users\\Administrator\\Desktop\\server\\tools\\connToDB\\getData.py " + gameName + " " + startDate + " " + endDate;
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
