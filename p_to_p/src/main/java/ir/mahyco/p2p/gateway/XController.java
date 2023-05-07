package ir.mahyco.p2p.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class XController {

    @PostMapping("/Start")
    public void start(Integer offset_day)
    {

    }
}
