package cn.cagurzhan.excel.convert;

import cn.cagurzhan.excel.annotation.ExcelDictFormat;
import cn.cagurzhan.utils.StringUtils;
import cn.cagurzhan.utils.poi.ExcelUtil;
import cn.cagurzhan.utils.spring.SpringUtils;
import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.NullCacheKey;

import java.lang.reflect.Field;

/**
 * 字典格式化转换处理
 *
 * @author Lion Li
 */
@Slf4j
public class ExcelDictConvert implements Converter<Object> {

    @Override
    public Class<Object> supportJavaTypeKey() {
        return Object.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return null;
    }

    @Override
    public Object convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        ExcelDictFormat anno = getAnnotation(contentProperty.getField());
        String type = anno.dictType();
        String label = cellData.getStringValue();
        String value;
        if (StringUtils.isBlank(type)) {
            value = ExcelUtil.reverseByExp(label, anno.readConverterExp(), anno.separator());
        } else {
            // TODO 注意，这里因为我不会用到字段功能，所以注释掉，如果需要使用请务必改回来。
            // value = SpringUtils.getBean(DictService.class).getDictValue(type, label, anno.separator());
            throw new UnsupportedOperationException("暂时不支持转换器，请查看相关代码的注释");
        }
        return Convert.convert(contentProperty.getField().getType(), value);
    }

    @Override
    public WriteCellData<String> convertToExcelData(Object object, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (ObjectUtil.isNull(object)) {
            return new WriteCellData<>("");
        }
        ExcelDictFormat anno = getAnnotation(contentProperty.getField());
        String type = anno.dictType();
        String value = Convert.toStr(object);
        String label;
        if (StringUtils.isBlank(type)) {
            label = ExcelUtil.convertByExp(value, anno.readConverterExp(), anno.separator());
        } else {
            // TODO 注意，这里因为我不会用到字段功能，所以注释掉，如果需要使用请务必改回来。
            throw new UnsupportedOperationException("暂时不支持转换器，请查看相关代码的注释");
            // label = SpringUtils.getBean(DictService.class).getDictLabel(type, value, anno.separator());
        }
        return new WriteCellData<>(label);
    }

    private ExcelDictFormat getAnnotation(Field field) {
        return AnnotationUtil.getAnnotation(field, ExcelDictFormat.class);
    }
}
