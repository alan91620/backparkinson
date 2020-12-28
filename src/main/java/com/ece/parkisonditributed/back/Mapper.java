package com.ece.parkisonditributed.back;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@RestController
@Component
public class Mapper {

    @Value("${app.pathToSaveWav}")
    private String pathToSaveWav;

    @PostMapping("/process")
    public ResponseEntity<ResponseMessage> decrypt(@RequestParam("file") MultipartFile file){
        save(file);
        JSONObject data = Caller.postAudio(pathBuilder());

        //POST in DB
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
        return pathToSaveWav + "/" + "data.wav";

    }
}
