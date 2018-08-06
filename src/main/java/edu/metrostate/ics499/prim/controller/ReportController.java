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
     * Generates the Facebook authorization URL that the client is redirected to.
     *
     * @return the URL to redirect the client to.
     * @throws IOException
     */
    @ResponseBody
    @GetMapping("/interactionCountBySocialNetwork")
    public List<Object[]> interactionCountBySocialNetwork() throws IOException {
        return interactionService.interactionCountBySocialNetwork();
    }

    @GetMapping("/")
    public ModelAndView reportList(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("report/list", "reports", interactionService.getAvailableReports(request));
    }
}
