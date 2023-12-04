package com.example.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.jpa.repository.FileUploadREpository;
import com.example.jpa.model.UploadFile;
import com.example.jpa.util.getCurrentTime;
import com.example.jpa.model.Picture;
import com.example.jpa.repository.PictureRepository;
import com.example.jpa.model.FileInfo;

@Controller
public class UploadController {
    @Autowired
    FileUploadREpository fileUploadRepository;
    @Autowired
    PictureRepository pictureRepository;

    @GetMapping("/upload1")
    public String upload1() {
        return "html/upload1";
    }

    @PostMapping("/upload1")
    @ResponseBody
    public String upload1Post(MultipartHttpServletRequest mRequest){
        String result = "";
        MultipartFile mFile = mRequest.getFile("file");
        String oName = mFile.getOriginalFilename();
        result += oName + "<br>" + mFile.getSize();


//파일 저장할 경로와 이름 " C:/폴더명/파일 이름"
        String saveFolder = "c:/data/";
        File saveFile = new File(saveFolder+oName);
        try {
             mFile.transferTo(saveFile);  
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }                   
        return result;
    }

    @GetMapping("/upload2")
    public String upload2() {
        return "/html/upload2";
    } 

    @PostMapping("/upload2")
    @ResponseBody
    public String upload2Post(@RequestParam("file") MultipartFile mFile, 
                              @RequestParam("memberId") String memberId,
                              HttpServletRequset requset) {
        UploadFile uploadFile = new UploadFile();
   
        String regDate = getCurrentTime.getTime();
        String uid = UUID.randomUUID().toString();
        String oName = mFile.getOriginalFilename();

        String secretFlag = "";
        if (requset.getParameter("secretFlag") == null){
            secretFlag = "1";
        } else {
            secretFlag = requset.getParameter("secretFlag");
        }

        System.out.println(secretFlag);

        String saveFolder = "C:/som/backend/jpa/jpa/src/main/resources/static/image/" ;
        // String saveFolder = "c:/data/";
        File saveFile = new File(saveFolder+uid);      //uid는 고유의 값이다
        try {
            int seq = fileUploadRepository.findAll().size()+1;
            uploadFile.setOriginalFileName(oName);
            uploadFile.setSeq(seq);
            uploadFile.setUuid(uid);
            uploadFile.setRegDate(regDate);
            uploadFile.setMemberId(memberId);
            fileUploadRepository.save(uploadFile);      //여기까지 DB에 저장 되는 부분
            mFile.transferTo(saveFile);                 //file에 저장되는 부분
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }     
        return oName + " 파일이 저장되었습니다";              
    }

    @GetMapping("/pictureupload")
    public String pictureUpload(){
        return "/html/pictureupload";
    }
    @PostMapping("/pictureupload")
    @ResponseBody
    public String pictureUploadPost(
        @RequestParam("file") MultipartFile mFile,
        @RequestParam("memberId") String memberId
    ){
        Picture picture = new Picture();
        String updateDate = getCurrentTime.getTime();
        String fileName = mFile.getName();
        String memberFileName = memberId + "_" +fileName;
        String saveFolder = "C:/som/backend/jpa/jpa/src/main/resources/static/image/" ;
        File saveFile = new File(saveFolder + memberFileName);
        int seq = pictureRepository.findAll().size()+1;
        try {
            mFile.transferTo(saveFile);
            picture.setSeq(seq);
            picture.setFileName(memberFileName);
            picture.setUpdateDate(updateDate);
            picture.setUserId(memberId);
            pictureRepository.save(picture);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        } 
        return "<div><img src='/image/"+memberFileName+"'></div>";
    }

    @GetMapping("/upload3")
    public String upload3(){
        return "/html/upload3";
    }
    @PostMapping("/upload3")
    @ResponseBody
    public String upload3Post(@ModelAttribute FileInfo info){
        String result = "";
        String oName = info.getFile().getOriginalFilename();
        Long size = info.getFile().getSize();
        String type = info.getFile().getContentType();
        return result += oName +" : "+ size +" : "+ type;
    }

    @GetMapping("/upload4")
    public String upload4(){
        return "/html/upload4";
    }
    @PostMapping("/upload4")
    @ResponseBody
    public String upload4Post(@RequestParam("file") MultipartFile[] mFiles,
                              @RequestParam("memberId") String memberId){
        UploadFile uploadFile = new UploadFile();
        String saveFolder = "C:/data/";
        String CurrentTime = getCurrentTime.getTime();
        String result = "";
        for (MultipartFile mFile : mFiles){
            String uid = UUID.randomUUID().toString();
            String oName = mFile.getOriginalFilename();
            int seq = fileUploadRepository.findAll().size()+1;
            File saveFile = new File(saveFolder+uid);
            uploadFile.setMemberId(memberId);
            uploadFile.setOriginalFileName(oName);
            uploadFile.setRegDate(CurrentTime);
            uploadFile.setUuid(uid);
            uploadFile.setSeq(seq);
            try {                                                
                mFile.transferTo(saveFile);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            } 
            result += "<p>" + oName + "</p>";
        }
        return result;
    }

}
