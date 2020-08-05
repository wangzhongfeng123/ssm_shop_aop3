package com.fh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.model.po.Cons;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsDao extends BaseMapper<Cons> {
    void updateByCheck(Integer m);

    void updateByChecked(Integer id);
}
