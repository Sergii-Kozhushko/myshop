/**
 * UtilsController.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 25-Jul-2023 17:19
 */

package de.edu.telran.myshop.controller.rest;

import de.edu.telran.myshop.dto.TestDataResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(UtilsController.MAIN_PATH)
@RequiredArgsConstructor
public class UtilsController {
    public static final String MAIN_PATH = "/utils";

    @PostMapping("/checkauth")
    public ResponseEntity<TestDataResultDto> checkAuth() throws URISyntaxException {
        return ResponseEntity
                .created(new URI(MAIN_PATH))
                .body(new TestDataResultDto("ok"));
    }

}
