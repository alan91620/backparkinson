package com.ece.parkisonditributed.back;
import com.ece.parkisonditributed.back.FeignModel.predictResource;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@RestController
@Component
public class Mapper {

    @Value("${app.pathToSaveWav}")
    private String pathToSaveWav;

    @PostMapping("/process")
    public String decrypt(@RequestParam("file") MultipartFile file){
        save(file);
        JSONObject data = Caller.postAudio(pathBuilder());

        //POST in DB
        return (String) data.get("parkinson");
    }

    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), Paths.get(pathBuilder()), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }


    public String pathBuilder (){
        return pathToSaveWav + "/" + "data.wav";

    }
}
