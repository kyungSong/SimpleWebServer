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

    @RequestMapping("/kr/trending")
    public String trending_kr()
    {
        return "kr/trending";
    }

    @RequestMapping("/en/trending")
    public String trending_en()
    {
        return "en/trending";
    }

    @RequestMapping("/")
    public String index_kr()
    {
        return "kr/index";
    }

    @RequestMapping("/en")
    public String index_en()
    {
        return "en/index";
    }

    @RequestMapping("/kr/text_analysis")
    public String text_analysis_kr()
    {
        return "kr/text_analysis";
    }

    @RequestMapping("/en/text_analysis")
    public String text_analysis_en()
    {
        return "en/text_analysis";
    }

    @RequestMapping("/kr/about")
    public String about_kr()
    {
        return "kr/about";
    }

    @RequestMapping("/en/about")
    public String about_en()
    {
        return "en/about";
    }

    @RequestMapping("/kr/contact")
    public String contact_kr()
    {
        return "kr/contact";
    }

    @RequestMapping("/en/contact")
    public String contact_en()
    {
        return "en/contact";
    }

    @RequestMapping("/google_trends")
    @ResponseBody
    public String googleTrends(@RequestParam(defaultValue = "커피", required = false, value="searchTerm") String searchTerm,
                               @RequestParam(defaultValue = "2000-01-01", required = false, value="startDate") String startDate,
                               @RequestParam(defaultValue = "2017-01-01", required = false, value="endDate") String endDate)
    {
        Runtime rt = Runtime.getRuntime();
        String absolutePath = System.getProperty("user.dir");
        String command = "node \"" + absolutePath + "\\..\\tools\\nodejs\\gt_related_queries.js\" " + searchTerm + " " + startDate + " " + endDate;
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
        String absolutePath = System.getProperty("user.dir");
        String command = "python \"" + absolutePath + "\\..\\tools\\connToDB\\getData.py\" " + gameName + " " + startDate + " " + endDate;
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
