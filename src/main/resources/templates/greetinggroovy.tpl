yieldUnescaped '<!DOCTYPE html>'
html(lang:'en') {
    head {
        meta('http-equiv':'"Content-Type" content="text/html; charset=utf-8"')
        title('Getting Started: Serving Web Content With Groovy Templates')
    }
    body {
        h2 ('A Groovy View with Spring MVC + Spring Boot')
        p ("Hello $name!")
    }
}