package com.flynn.dto;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.flynn.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.util.Date;

/**
 * 外显号码表
 *
 * @author v_fuxincao
 * @date 2021-12-21 11:08:10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@HeadStyle(fillForegroundColor = 22, fillPatternType = FillPatternType.SOLID_FOREGROUND, locked = true, wrapped = true, verticalAlignment = VerticalAlignment.CENTER, horizontalAlignment = HorizontalAlignment.CENTER)
public class ExplicitNumberDTO extends BaseDTO {


    /**
     *
     */
    @ExcelIgnore
    private Long id;
    /**
     * 归属地
     */
    @ExcelProperty("归属地")
    private String attribution;
    /**
     * 外显号码
     */
    @ExcelProperty("外显号码")
    private String explicit;
    /**
     *
     */
    @ExcelIgnore
    private Long lineId;
    /**
     * 创建时间
     */
    @ExcelIgnore
    private Date createTime;
    /**
     * 更新时间
     */
    @ExcelIgnore
    private Date updateTime;

}
