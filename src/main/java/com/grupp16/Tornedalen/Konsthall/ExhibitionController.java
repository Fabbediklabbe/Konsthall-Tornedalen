package com.grupp16.Tornedalen.Konsthall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/exhibitions")
//Tillåter frontend att hämta från backend
@CrossOrigin("*")

public class ExhibitionController
{
    @Autowired
    private SQL sql;

    @GetMapping("/all")
    public ResponseEntity<List<Map<String, Object>>> getAllExhibitions ()
    {
        List<Map<String, Object>> exhibitions = sql.fetchAllExhibitions();
        return ResponseEntity.ok(exhibitions);
    }
}
