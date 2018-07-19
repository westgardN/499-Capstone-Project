package edu.metrostate.ics499.prim.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This controller simply serves up an error page when the server an encounters an error.
 * That is, the HTTP response code is in 4xx or 5xx.
 */
@ControllerAdvice
public class ErrorController {

    private static Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(Throwable.class)
    public String exception(final Throwable throwable, final Model model) {
        logger.error("Exception during execution of PRIM application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        // Add the view name to the model as our interceoptor is not called when handling
        // exceptions.
        model.addAttribute("viewName", "error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }

}