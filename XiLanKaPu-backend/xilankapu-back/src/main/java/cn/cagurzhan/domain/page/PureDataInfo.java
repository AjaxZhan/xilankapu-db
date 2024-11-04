package cn.cagurzhan.domain.page;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Cagur Zhan
 * 分页查询实体
 */
@Data
@NoArgsConstructor
public class PureDataInfo<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 列表数据
     */
    private List<T> rows;

    /**
     * 消息状态码
     */
    private int code;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 当前页码
     */
    private long currentPage;

    /**
     * 页面大小
     */
    private long pageSize;

    /**
     * 分页
     *
     * @param list  列表数据
     * @param total 总记录数
     */
    public PureDataInfo(List<T> list, long total) {
        this.rows = list;
        this.total = total;
    }

    public static <T> PureDataInfo<T> build(IPage<T> page) {
        PureDataInfo<T> rspData = new PureDataInfo<>();
        rspData.setCode(HttpStatus.HTTP_OK);
        rspData.setMsg("查询成功");
        rspData.setRows(page.getRecords());
        rspData.setTotal(page.getTotal());
        rspData.setCurrentPage(page.getCurrent());
        rspData.setPageSize(page.getSize());
        return rspData;
    }

    public static <T> PureDataInfo<T> build(List<T> list) {
        PureDataInfo<T> rspData = new PureDataInfo<>();
        rspData.setCode(HttpStatus.HTTP_OK);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(list.size());
        return rspData;
    }

    public static <T> PureDataInfo<T> build() {
        PureDataInfo<T> rspData = new PureDataInfo<>();
        rspData.setCode(HttpStatus.HTTP_OK);
        rspData.setMsg("查询成功");
        return rspData;
    }

}
