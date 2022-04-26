package com.flynn.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.flynn.dto.User;
import com.flynn.dto.ExplicitNumberDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 　　@Description:
 * 　　@author: v_fuxincao
 * 　　@version: 1.0
 * 　　@date: 2021-12-16 11:18
 */

@Data
@RestController
@RequestMapping("/test/01")
@Log4j2
@ResponseBody
public class TestController {

    @RequestMapping(value = "/testmethod", method = RequestMethod.POST)
    public boolean test( final HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

        String apikey = "webank_push_task_1";

        System.out.println("apikey = " + apikey);

        System.out.println("apikey = " + apikey);

        return true;

    }

    @RequestMapping(value = "/filetest", method = RequestMethod.POST)
    public void test01(@RequestBody String json, final HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

        try {
            // 文件名
            String path = FileUtil.getTmpDirPath() + File.separator + "result" + File.separator;

            System.out.println("path = " + path);
            String str = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT);
            String zipName = StrUtil.format("result-{}.zip", str);
            String zipPathName = StrUtil.format(path + "result-{}.zip", str);
            String corrExcelName = StrUtil.format(path + "result-纠错-{}.xlsx", str);
            String corpusExcelName = StrUtil.format(path + "result-语料-{}.xlsx", str);


            File corrFileName = FileUtil.touch(corrExcelName);
            File corpusFileName = FileUtil.touch(corpusExcelName);
            File zipFile = FileUtil.touch(zipPathName);

            OutputStream os1 = new FileOutputStream(corrFileName);
            OutputStream os2 = new FileOutputStream(corpusFileName);
            ExcelWriter writer = ExcelUtil.getWriter(true);
            writer.flush(os1);
            writer.flush(os2);
            writer.close();


            User user = new User();
            List<User> users = new ArrayList<>();
            users.add(user);
            EasyExcel.write(corrFileName, User.class).sheet("").doWrite(users);
            List<File> fileList = new ArrayList<File>();
            if (FileUtil.exist(corrFileName)) {
                fileList.add(corrFileName);
            }

            if (FileUtil.exist(corpusFileName)) {
                fileList.add(corpusFileName);
            }
            File[] filesArrays = new File[fileList.size()];
            fileList.toArray(filesArrays);

            File zip = ZipUtil.zip(zipFile, false, filesArrays);

            byte[] bytes = FileUtil.readBytes(zip);
//            httpResponse.reset();
            httpResponse.setHeader("content-disposition", "attachment;filename=" + zipName);
            httpResponse.addHeader("Content-Length", "" + bytes.length);
//            httpResponse.setContentType("application/octet-stream; charset=UTF-8");
            IoUtil.write(httpResponse.getOutputStream(), Boolean.TRUE, bytes);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/exceltest", method = RequestMethod.POST)
    public void exceltest(@RequestBody String json, final HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

        try {
            List<ExplicitNumberDTO> explicitNumberDTOS = new ArrayList<>();
            ExplicitNumberDTO explicitNumberDTO = new ExplicitNumberDTO();
            explicitNumberDTO.setAttribution("sohfuo");
            explicitNumberDTO.setExplicit("4156465416");

            ExplicitNumberDTO explicitNumberDTO1 = new ExplicitNumberDTO();
            explicitNumberDTO1.setAttribution("asg tawt");
            explicitNumberDTO1.setExplicit("d465465465");

            explicitNumberDTOS.add(explicitNumberDTO);
            explicitNumberDTOS.add(explicitNumberDTO1);
            String fileName = "explicitNumber"+ DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT)+".xlsx";
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            httpResponse.setContentType("application/vnd.ms-excel");
            httpResponse.setCharacterEncoding("utf-8");
            httpResponse.setHeader("content-disposition", "attachment;filename=" + fileName);
            EasyExcel.write(httpResponse.getOutputStream(), ExplicitNumberDTO.class).sheet("sheet1").doWrite(explicitNumberDTOS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}