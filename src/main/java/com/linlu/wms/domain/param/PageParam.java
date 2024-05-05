package com.linlu.wms.domain.param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class PageParam<T> extends Page {

    public PageParam(long current, long size) {
        super.current = current;
        super.size = size;
    }
}
