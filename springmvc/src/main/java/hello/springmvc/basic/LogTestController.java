package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogTestController {

//    private final Logger log = LoggerFactory.getLogger(LogTestController.class);

    @GetMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        System.out.println("name = " + name);

        // 자바에서는 사용하든 안하든 + 연산읋 해서 저장한다. 의미없는 연산이 발생한다.
        log.trace("trace my log = " + name);

        log.trace("trace Log = {}", name);
        log.debug("debug Log = {}", name);
        log.info("info Log = {}", name);
        log.warn("warn Log = {}", name);
        log.error("error Log = {}", name);

        return "ok";
    }
}
