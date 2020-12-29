package com.ece.parkisonditributed.back;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@RestController
@Component
public class Mapper {


    /*@Value("${app.pathToSaveWav}")
    private String pathToSaveWav;*/

    @Autowired
    private Environment env;

    @PostMapping("/process")
    public ResponseEntity<ResponseMessage> decrypt(@RequestParam("file") MultipartFile file){
        save(file);
        JSONObject data = Caller.postAudio(pathBuilder(), env.getProperty("PAR_API_URL"));

        //POST in DB

        SpringJdbcConfig configurator = new SpringJdbcConfig();
        DataSource ds = configurator.mysqlDataSource(env.getProperty("MYSQL_DRIVER"),env.getProperty("MYSQL_URL"),env.getProperty("MYSQL_USR"),env.getProperty("MYSQL_PASS"));
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

        jdbcTemplate.update(
                "INSERT INTO measures (parkinson, Jitter_rel, Jitter_abs, Jitter_RAP, Jitter_PPQ, Shim_loc, Shim_dB, Shim_APQ3, Shim_APQ5, Shim_APQ11, hnr05, hnr15, hnr25) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                data.get("parkinson"), (BigDecimal) data.get("Jitter_rel"), (BigDecimal) data.get("Jitter_abs"), (BigDecimal) data.get("Jitter_RAP"), (BigDecimal) data.get("Jitter_PPQ"), (BigDecimal) data.get("Shim_loc"), (BigDecimal) data.get("Shim_dB"), (BigDecimal) data.get("Shim_APQ3"), (BigDecimal) data.get("Shim_APQ5"), (BigDecimal) data.get("Shi_APQ11"), (BigDecimal) data.get("hnr05"), (BigDecimal)data.get("hnr15"), (BigDecimal)data.get("hnr25")
        );

        //return (String) data.get("parkinson");
        ResponseMessage message = new ResponseMessage("");
        if (((String) data.get("parkinson")).equals("false")){
            message.setMessage("The prediction says that you don't have parkinson");
        }
        else{
            message.setMessage("The prediction says that you have parkinson");
        }
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), Paths.get(pathBuilder()), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }


    public String pathBuilder (){
        //return pathToSaveWav + "/" + "data.wav";
        return env.getProperty("WAV_TARGET") + "/" + "data.wav";

    }
}
