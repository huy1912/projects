package com.example.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// http://zetcode.com/java/freemarker/
public class FreeMarkerConsoleEx {

    public static void main(String[] args) throws IOException,
            TemplateException {

        Configuration cfg = new Configuration(new Version("2.3.23"));

        cfg.setClassForTemplateLoading(FreeMarkerConsoleEx.class, "/");
        cfg.setDefaultEncoding("UTF-8");

        Template template = cfg.getTemplate("test.ftl");

        Map<String, Object> templateData = new HashMap<>();
        templateData.put("msg", "Today is a beautiful day");
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("Audi", 10000));
        cars.add(new Car("Volvo", 20000));
        cars.add(new Car("Honda", 30000));
        templateData.put("cars", cars);

        try (StringWriter out = new StringWriter()) {
            
            template.process(templateData, out);
            System.out.println(out.getBuffer().toString());

            out.flush();
        }
    }
}