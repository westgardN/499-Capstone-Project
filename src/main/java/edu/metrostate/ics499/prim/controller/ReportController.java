package edu.metrostate.ics499.prim.controller;

import edu.metrostate.ics499.prim.model.SocialNetwork;
import edu.metrostate.ics499.prim.service.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/report")
public class ReportController {
    @Autowired
    InteractionService interactionService;

    /**
     * Generates the data for the interaction count by social network report.
     *
     * @return A list of Object arrays that contain the label and data for each
     * result.
     */
    @ResponseBody
    @GetMapping("/interactionCountBySocialNetwork")
    public List<Object[]> interactionCountBySocialNetwork() throws IOException {
        return interactionService.interactionCountBySocialNetwork();
    }

    @ResponseBody
    @GetMapping("/interactionCountByState")
    public List<Object[]> interactionCountByState() throws IOException {
        return interactionService.interactionCountByState();
    }

    /**
     * Returns a list of available reports to run.
     *
     * @param request the request object
     * @param response the response object
     * @return a list of available reports to run.
     */
    @GetMapping("/")
    public ModelAndView reportList(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("report/list", "reports", interactionService.getAvailableReports(request));
    }
}
