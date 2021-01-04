package com.allimu.mastercontroller.netty.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.allimu.mastercontroller.dao.EquipDao;
import com.allimu.mastercontroller.netty.model.Equip;
import com.allimu.mastercontroller.remote.service.InstructionCodeRemoteService;
import com.allimu.mastercontroller.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 项目启动时运行一次,从集控同步网关信息
 *
 * @author ymsn
 */
@Service
public class SaveEquipService implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private InstructionCodeRemoteService remoteService;
    @Autowired
    private EquipDao equipDao;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            System.out.println(" >> 项目启动时同步一次网关设备");

            String str = remoteService.getWgEquipBySchoolCode(CommonUtil.schoolCode);

            JSONObject jsonObject = JSONObject.parseObject(str);
            System.out.println(" >> 查看str：" + jsonObject);
            String res = jsonObject.getString("data");
            if (res != null && !res.equals("[]")) {
                List<Equip> snEquipList = JSON.parseObject(res, new TypeReference<ArrayList<Equip>>() {
                });
                for (Equip snEquip : snEquipList) {
                    //查询获取到的信息时候已存在
                    if (equipDao.getSnEquipBySn(snEquip.getSn()) == null) {
                        //不存在就新增
                        snEquip.setCreateTime(new Date());
                        equipDao.saveSnEquip(snEquip);
                    }
                }
            }
        }
    }


}
