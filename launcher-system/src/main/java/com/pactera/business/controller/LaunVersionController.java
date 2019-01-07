package com.pactera.business.controller;

import com.pactera.business.service.LaunVersionService;
import com.pactera.config.exception.status.SuccessStatus;
import com.pactera.result.ResultData;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName LaunVersionController
 * @Description 版本管理
 * @Author xukj
 * @Date 2018/12/19 14:11
 * @Version
 */

@RestController
@RequestMapping("/version")
public class LaunVersionController {

    @Autowired LaunVersionService launVersionService;

    /**
     * 查询租户（所有）版本信息
     * @return
     */
    @GetMapping("/list")
    ResultData versions() { return new ResultData(launVersionService.versions()); }

    /**
     * 添加描述信息
     * @param id
     * @param description
     * @return
     */
    @PostMapping("/describe")
    ResultData describe(@NonNull Long id, @RequestParam(defaultValue = "") String description) {
        int count = launVersionService.describe(id, description);
        return count == 1? new ResultData(): new ResultData((Object)count, SuccessStatus.UPDATE_DESCRIPTION_FAIL);
    }

    /**
     * 车机端上报
     * @param version
     * @return
     */
    @PostMapping("/add")
    ResultData add(@NonNull String version) {
        launVersionService.add(version);
        return new ResultData();
    }

}
